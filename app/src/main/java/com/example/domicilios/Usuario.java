package com.example.domicilios;

public class Usuario {
    String nombre;
    String correo;
    String perfil;
    String estado;
    String numeroDocumento;
    String telefono;
    String direccion;
    String tipoVehiculo;
    String placa;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String perfil, String estado) {
        this.nombre = nombre;
        this.correo = correo;
        this.perfil = perfil;
        this.estado = estado;
    }

    public Usuario(String nombre, String correo, String perfil, String estado,
                   String numeroDocumento, String telefono, String direccion,
                   String tipoVehiculo, String placa) {
        this.nombre = nombre;
        this.correo = correo;
        this.perfil = perfil;
        this.estado = estado;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipoVehiculo = tipoVehiculo;
        this.placa = placa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getEstado() {
        return estado;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}