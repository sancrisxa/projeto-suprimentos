package br.com.tjro.supribackend.controller;

import br.com.tjro.supribackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/util")
public class UtilController {

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/generate-token")
    public ResponseEntity<String> generateToken() throws Exception {

        return ResponseEntity.ok(jwtUtil.generateToken());
    }
}
