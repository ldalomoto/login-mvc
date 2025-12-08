package com.exposicion.loginmvc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    // Prueba 1: Verifica que el constructor vacío y los Setters funcionen
    @Test
    void testNoArgsConstructorAndSetters() {
        User user = new User();
        
        // Probamos los Setters
        user.setId(1L);
        user.setUsername("lenin");
        user.setPassword("1234");
        
        // Verificamos con los Getters (esto valida que el dato entró y salió bien)
        assertEquals(1L, user.getId());
        assertEquals("lenin", user.getUsername());
        assertEquals("1234", user.getPassword());
    }

    // Prueba 2: Verifica que el constructor con argumentos funcione
    @Test
    void testAllArgsConstructor() {
        // Usamos el constructor User(Long id, String username, String password)
        User user = new User(2L, "admin", "adminPass");
        
        assertNotNull(user); // Verifica que el objeto se creó
        assertEquals(2L, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("adminPass", user.getPassword());
    }
}