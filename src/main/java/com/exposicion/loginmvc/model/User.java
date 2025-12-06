package com.exposicion.loginmvc.model;

/**
 * Clase POJO (Plain Old Java Object) que representa al usuario.
 * Contiene datos simples y es usada para transportar información entre capas.
 */
public class User {

    private String username;
    private String password;
    
    // Asumimos que todo usuario tiene un ID (simulando una clave primaria de BD)
    private Long id; 
    
    public User(){
        //TODO
    }

    // Constructor, Getters y Setters...
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}