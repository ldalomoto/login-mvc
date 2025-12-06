package com.exposicion.loginmvc.service;

import com.exposicion.loginmvc.model.User;
import com.exposicion.loginmvc.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// 1. Le decimos a JUnit 5 que use la extensión de Mockito
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    // Pruebas
    // 2. @Mock: Crea un "doble" del repositorio.
    // No conecta a la BD real, es un objeto tonto que nosotros controlamos.
    @Mock
    private UserRepository userRepository;

    // 3. @InjectMocks: Crea una instancia real de AuthService e
    // inyecta dentro de ella el mock de userRepository que creamos arriba.
    @InjectMocks
    private AuthService authService;

    // CASO 1: El usuario existe y la contraseña es correcta
    @Test
    void authenticate_DeberiaRetornarTrue_CuandoCredencialesSonCorrectas() {
        // PREPARAR (Given)
        User usuarioSimulado = new User();
        usuarioSimulado.setUsername("lenin");
        usuarioSimulado.setPassword("secreto123");

        // Le enseñamos al Mock qué decir:
        // "Cuando te busquen a 'lenin', devuelve este usuario simulado"
        when(userRepository.findByUsername("lenin")).thenReturn(Optional.of(usuarioSimulado));

        // EJECUTAR (When)
        boolean resultado = authService.authenticate("lenin", "secreto123");

        // VERIFICAR (Then)
        assertTrue(resultado, "El login debería ser exitoso");
    }

    // CASO 2: El usuario existe pero la contraseña está mal
    @Test
    void authenticate_DeberiaRetornarFalse_CuandoPasswordEsIncorrecto() {
        // PREPARAR
        User usuarioSimulado = new User();
        usuarioSimulado.setUsername("lenin");
        usuarioSimulado.setPassword("secreto123");

        // El mock devuelve el usuario correcto...
        when(userRepository.findByUsername("lenin")).thenReturn(Optional.of(usuarioSimulado));

        // ...pero nosotros intentamos entrar con una clave errónea
        boolean resultado = authService.authenticate("lenin", "clave_erronea");

        // VERIFICAR
        assertFalse(resultado, "El login debería fallar por contraseña incorrecta");
    }

    // CASO 3: El usuario no existe en la base de datos
    @Test
    void authenticate_DeberiaRetornarFalse_CuandoUsuarioNoExiste() {
        // PREPARAR
        // Le decimos al mock: "Si buscan a 'fantasma', devuelve vacío (empty)"
        when(userRepository.findByUsername("fantasma")).thenReturn(Optional.empty());

        // EJECUTAR
        boolean resultado = authService.authenticate("fantasma", "cualquierClave");

        // VERIFICAR
        assertFalse(resultado, "El login debería fallar porque el usuario no existe");
    }
}