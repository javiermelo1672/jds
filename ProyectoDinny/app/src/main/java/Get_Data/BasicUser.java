package Get_Data;

/**
 * Created by javier on 14/02/18.
 */

public class BasicUser {


    public String Nombre;
    public String Telefono;
    public String SmsId;
    public String emailid;
    public String Foto;

    public BasicUser(String nombre, String telefono, String smsId, String emailid, String foto) {
        Nombre = nombre;
        Telefono = telefono;
        SmsId = smsId;
        this.emailid = emailid;
        Foto = foto;
    }

    public BasicUser() {
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

    public String getSmsId() {
        return SmsId;
    }

    public void setSmsId(String smsId) {
        SmsId = smsId;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
