package com.example.inmobiliarialucero.modelo;

import java.io.Serializable;

public class Propietario implements Serializable {
    private int IdPropietario;
    private String Nombre;
    private String Apellido;
    private String DNI;
    private String Telefono;
    private String Email;
    private String Clave;

    public Propietario() {

    }

    public Propietario(int idPropietario, String nombre, String apellido, String DNI, String telefono, String email, String clave) {
        IdPropietario = idPropietario;
        Nombre = nombre;
        Apellido = apellido;
        this.DNI = DNI;
        Telefono = telefono;
        Email = email;
        Clave = clave;
    }

    public int getIdPropietario() {
        return IdPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        IdPropietario = idPropietario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
