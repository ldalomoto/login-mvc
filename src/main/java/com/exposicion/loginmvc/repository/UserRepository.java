package com.exposicion.loginmvc.repository;

import com.exposicion.loginmvc.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio encargado del acceso a datos. Por ahora, simula la conexión a una
 * BD.
 */
@Repository
public class UserRepository {

    // Simulación del "usuario maestro" en la base de datos
    private final User ADMIN_USER = new User(1L, "admin", "password123");

    /**
     * Simula la búsqueda de un usuario por su nombre en la BD.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un objeto Optional<User> con el usuario si es encontrado.
     */
    public Optional<User> findByUsername(String username) {

        // Simulación: Si el nombre de usuario coincide con el predefinido, devuelve el objeto User.
        if (ADMIN_USER.getUsername().equals(username)) {
            return Optional.of(ADMIN_USER);
        }

        // Si no se encuentra, devuelve un Optional vacío (como lo haría un repositorio real)
        return Optional.empty();
    }
}
