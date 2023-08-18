package br.com.tjro.supribackend.service;

import br.com.tjro.supribackend.dto.RolesDto;

public interface RoleService {
    public RolesDto getRoles(String token);
}