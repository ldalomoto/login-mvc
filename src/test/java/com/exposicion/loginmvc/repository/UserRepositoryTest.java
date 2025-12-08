package com.exposicion.loginmvc.repository;

import com.exposicion.loginmvc.model.User;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    // Instanciamos el repositorio real (porque NO es una interfaz, es una clase)
    private final UserRepository userRepository = new UserRepository();

    @Test
    void findByUsername_DeberiaEncontrarAdmin_CuandoElUsuarioEsCorrecto() {
        // Probamos el caso positivo ("admin")
        Optional<User> resultado = userRepository.findByUsername("admin");

        // Verificamos que encontró algo
        assertTrue(resultado.isPresent(), "Debería encontrar al usuario admin");
        assertEquals("admin", resultado.get().getUsername());
        assertEquals("password123", resultado.get().getPassword());
    }

    @Test
    void findByUsername_DeberiaRetornarVacio_CuandoElUsuarioNoExiste() {
        // Probamos el caso negativo (cualquier otro nombre)
        Optional<User> resultado = userRepository.findByUsername("usuarioDesconocido");

        // Verificamos que devolvió vacío (Optional.empty)
        assertFalse(resultado.isPresent(), "No debería encontrar usuarios que no sean admin");
    }
}