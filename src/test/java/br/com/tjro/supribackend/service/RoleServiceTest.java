package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.Constantes.Constantes;
import br.com.tjro.supribackend.dto.RolesDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    public void getRoles() {
        RolesDto roles = this.roleService.getRoles(Constantes.TOKEN);

        Assertions.assertNotNull(roles);
    }
}
