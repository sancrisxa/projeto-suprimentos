package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.repository.UsuarioRepository;
import br.com.tjro.supribackend.dto.LoginDto;
import br.com.tjro.supribackend.dto.LoginInfoDto;
import br.com.tjro.supribackend.dto.RefreshTokenDto;
import br.com.tjro.supribackend.dto.SsoTokenDto;
import br.com.tjro.supribackend.model.Usuario;
import br.com.tjro.supribackend.util.JwtUtil;
import br.com.tjro.supribackend.util.MessageService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AutenticacaoSSOService {

    @Value("${keycloak.token-uri}")
    private String keycloakTokenUri;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.logout-uri}")
    private String keycloakLogoutUri;
    private final RestTemplate restTemplate;
    private final UsuarioRepository repository;
    private final MessageService messageService;
    private final JwtUtil jwtUtil;

    private SsoTokenDto login(LoginDto loginDto) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", loginDto.getLogin());
        map.add("password", loginDto.getSenha());
        map.add("client_id", clientId);
        map.add("grant_type", "password");
        map.add("scope", "openid profile");

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
            String response = restTemplate.postForObject(keycloakTokenUri, request, String.class);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            SsoTokenDto ssoTokenDto = gson.fromJson(response, SsoTokenDto.class);

            return ssoTokenDto;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, messageService.message("user.and.password.error"));
            }

            if (e.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT || e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                throw new HttpClientErrorException(e.getStatusCode(), messageService.messageWithParams("sso.login.error", "Serviço SSO indisponível"));
            }
            throw new RuntimeException(messageService.messageWithParams("sso.login.error", ExceptionUtils.getRootCauseMessage(e)));
        }
    }

    //TODO implementar futuramente quando houver a definição das permissões no SSO
    private boolean possuiPermissaoAdmin(String token) {
        boolean possuiPermissao = false;
        try {
            JwtClaims claims = extractClaimsFromToken(token);
            HashMap resourceAccess = (HashMap) claims.getClaimValue("resource_access");
            HashMap clientIdHash = (HashMap) resourceAccess.get(clientId);

            if (clientIdHash != null && !clientIdHash.isEmpty()) {
                String role = clientIdHash.get("roles").toString();
                possuiPermissao = role.contains("SIJ_ADMINISTRADOR");
            }

            return possuiPermissao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> extractRolesJwt(String token) {

        List<String> roles = new ArrayList<>();

        try {
            JwtClaims claims = extractClaimsFromToken(token);
            HashMap resourceAccess = (HashMap) claims.getClaimValue("resource_access");

            if (resourceAccess.containsKey(clientId)) {
                Map<String, List<String>> resource = (Map<String, List<String>>) resourceAccess.get(clientId);
                roles.addAll(resource.get("roles"));
            }

            return roles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JwtClaims extractClaimsFromToken(String token) {
        try {
            JwtConsumer consumer = new JwtConsumerBuilder()
                    .setSkipAllValidators()
                    .setDisableRequireSignature()
                    .setSkipSignatureVerification()
                    .build();

            JwtClaims claims = consumer.processToClaims(token);
            return claims;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public LoginInfoDto autenticarSSO(LoginDto login) throws Exception {
        SsoTokenDto response = login(login);

        if (StringUtils.isNotBlank(response.getAccessToken())) {

            Optional<Usuario> byMatricula = repository.findByMatricula(login.getLogin());
            Usuario usuario = byMatricula.orElse(null);

            if (usuario == null) {
                JwtClaims jwtClaims = extractClaimsFromToken(response.getAccessToken());
                usuario = new Usuario(null,
                        jwtClaims.getClaimValue("given_name").toString(),
                        login.getLogin(),
                        null);

                usuario = repository.save(usuario);
            }

            return new LoginInfoDto(usuario.getIdUsuario(),
                    usuario.getNome(), usuario.getMatricula(), jwtUtil.generateToken(),
                    response);

        } else {
            throw new RuntimeException("Não foi possivel autenticar o usuário");
        }
    }

    public void logoutSSO(RefreshTokenDto logout) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("client_id", clientId);
        map.add("refresh_token", logout.getRefreshToken());

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
            restTemplate.postForObject(keycloakLogoutUri, request, String.class);

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT || e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                throw new HttpClientErrorException(e.getStatusCode(), "Serviço SSO indisponível");
            }
            throw new RuntimeException(ExceptionUtils.getRootCauseMessage(e));
        }
    }

    public SsoTokenDto refreshTokenSSO(RefreshTokenDto logout) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("client_id", clientId);
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", logout.getRefreshToken());

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
            String response = restTemplate.postForObject(keycloakTokenUri, request, String.class);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            SsoTokenDto ssoTokenDto = gson.fromJson(response, SsoTokenDto.class);

            return ssoTokenDto;

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT || e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                throw new HttpClientErrorException(e.getStatusCode(), "Serviço SSO indisponível");
            }

            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

}
