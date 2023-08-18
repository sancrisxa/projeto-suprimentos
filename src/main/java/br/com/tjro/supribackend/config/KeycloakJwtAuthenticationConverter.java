package br.com.tjro.supribackend.config;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private List<String> clientIds;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return new JwtAuthenticationToken(source, Stream.concat(new JwtGrantedAuthoritiesConverter().convert(source)
                        .stream(), extractResourceRoles(source).stream())
                .collect(toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        HashMap<Object, Object> resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        List<Object> resourceRoles = new ArrayList<>();

        clientIds.stream().forEach(id -> {
            if (resourceAccess.containsKey(id)) {
                Map<String, List<String>> resource = (Map<String, List<String>>) resourceAccess.get(id);
                resourceRoles.addAll(resource.get("roles"));
            }
        });
        return resourceRoles.isEmpty() ? emptySet() : resourceRoles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(toSet());
    }
}

