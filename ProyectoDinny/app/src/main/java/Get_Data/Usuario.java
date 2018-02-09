package Get_Data;

/**
 * Created by javier on 25/01/18.
 */

public class Usuario {
    private String Nombre;
    private String Correo;
    private String Contrasena;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasena) {
        Nombre = nombre;
        Correo = correo;
        Contrasena = contrasena;
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
}
