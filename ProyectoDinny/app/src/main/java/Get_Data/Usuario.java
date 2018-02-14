package Get_Data;

/**
 * Created by javier on 25/01/18.
 */

public class Usuario {
    private String Nombre;
    private String Correo;
    private String Contrasena;
    private String Foto;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasena, String foto) {
        Nombre = nombre;
        Correo = correo;
        Contrasena = contrasena;
        Foto=foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
