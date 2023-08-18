package br.com.tjro.supribackend.dto;

import br.com.tjro.supribackend.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class RolesDto {

    @NotNull
    private List<Role> roles;
}
