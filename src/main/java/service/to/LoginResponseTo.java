package service.to;

// esta clase me permite enviar el token, nombre y rol desde el backend al frontend
public class LoginResponseTo {

    private String token;
    private String nombre;
    private String rol;

    // GET y SET

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
