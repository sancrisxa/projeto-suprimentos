package br.com.tjro.supribackend.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    JwtUtil jwtUtil;

    @BeforeEach
    void before() {
        ReflectionTestUtils.setField(jwtUtil, "sub", "egespapi");
        ReflectionTestUtils.setField(jwtUtil, "mat", "00sonda");
        ReflectionTestUtils.setField(jwtUtil, "key", "MjRJLBbd5vpL5");
        ReflectionTestUtils.setField(jwtUtil, "exp", "1740000");
    }

    @Test
    void generateToken() throws Exception {

        String generateToken = this.jwtUtil.generateToken();

        assertNotNull(generateToken);
    }
}