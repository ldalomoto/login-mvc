package com.exposicion.loginmvc.repository;

import com.exposicion.loginmvc.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public class UserRepository {

    // Simulación del "usuario maestro" en la base de datos
    private final User adminUser = new User(1L, "admin", "password123");

    public Optional<User> findByUsername(String username) {

        // Simulación: Si el nombre de usuario coincide con el predefinido, devuelve el objeto User.
        if (adminUser.getUsername().equals(username)) {
            return Optional.of(adminUser);
        }

        // Si no se encuentra, devuelve un Optional vacío (como lo haría un repositorio real)
        return Optional.empty();
    }
}
