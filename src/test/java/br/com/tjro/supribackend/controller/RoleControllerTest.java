package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.Constantes.Constantes;
import br.com.tjro.supribackend.dto.RolesDto;
import br.com.tjro.supribackend.enums.Role;
import br.com.tjro.supribackend.service.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @InjectMocks
    RoleController roleController;

    @Mock
    RoleServiceImpl roleService;

    @Test
    public void getRoles() {

        Role role = Role.SERVIDOR_SOF;

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        RolesDto rolesDto = new RolesDto();
        rolesDto.setRoles(roleList);

        Mockito.when(this.roleService.getRoles(Constantes.TOKEN)).thenReturn(rolesDto);
        ResponseEntity<RolesDto> responseEntity = this.roleController.getRoles(Constantes.TOKEN);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
