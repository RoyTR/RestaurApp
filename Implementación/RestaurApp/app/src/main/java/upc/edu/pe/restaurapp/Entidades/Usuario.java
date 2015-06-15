package upc.edu.pe.restaurapp.Entidades;

import java.math.BigDecimal;

/**
 * Created by Roy on 14/06/2015.
 */
public class Usuario {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String username;
    private String email;
    private BigDecimal facebook_id;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(BigDecimal facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
