package service.to;

// esta clase me permite enviar el usuario y password desde el frontend al backend
public class LoginRequestTo {

    private String usuario;
    private String password;

    // GET y SET

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
