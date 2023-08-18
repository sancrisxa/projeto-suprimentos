package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.RolesDto;
import br.com.tjro.supribackend.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    @Autowired
    ModelMapper mapper;

    @Override
    public RolesDto getRoles(String token) {

        try {

            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            String payload = new String(decoder.decode(chunks[1]));

            List<Role> rolesList = new ArrayList<>();

            if (payload.contains("SUPRI_SERVIDOR_SOF")) {
                rolesList.add(Role.SERVIDOR_SOF);
            }

            if (payload.contains("SUPRI_SERVIDOR_SGP")) {
                rolesList.add(Role.SERVIDOR_SGP);
            }

            if (payload.contains("SUPRI_GESTOR_UNIDADE")) {
                rolesList.add(Role.GESTOR_UNIDADE);
            }

            if (payload.contains("SUPRI_SUPRIDO")) {
                rolesList.add(Role.SUPRIDO);
            }

            RolesDto roleDto = new RolesDto();

            if (rolesList.isEmpty()) {
                throw new RuntimeException("Usuário sem perfil válido");
            }

            roleDto.setRoles(rolesList);

            return roleDto;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar os perfil");
        }
    }
}
