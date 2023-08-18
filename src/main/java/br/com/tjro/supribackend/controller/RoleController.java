package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.dto.RolesDto;
import br.com.tjro.supribackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<RolesDto> getRoles(@RequestHeader("Authorization") String token) {

        RolesDto roles = this.roleService.getRoles(token);

        return ResponseEntity.ok(roles);
    }
}
