package com.example.javier.proyectodinny;

/**
 * Created by javier on 31/01/18.
 */

class User {

    private String Foto;
    private String Nombre;
    private String Telefono;
    private String EmailVeri;
    private String SmsVeri;

    public User() {
    }

    public User(String foto, String nombre, String telefono, String emailVeri, String smsVeri) {
        Foto = foto;
        Nombre = nombre;
        Telefono = telefono;
        EmailVeri = emailVeri;
        SmsVeri = smsVeri;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmailVeri() {
        return EmailVeri;
    }

    public void setEmailVeri(String emailVeri) {
        EmailVeri = emailVeri;
    }

    public String getSmsVeri() {
        return SmsVeri;
    }

    public void setSmsVeri(String smsVeri) {
        SmsVeri = smsVeri;
    }
}