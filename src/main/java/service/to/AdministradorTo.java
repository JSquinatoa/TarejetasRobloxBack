package service.to;

public class AdministradorTo {

    private Integer id;
    private String nombre;
    private String usuario;
    // El password entra en CREATE/UPDATE pero nunca sale en respuestas GET.
    // El mapper lo asigna solo en la dirección To → Entidad.
    private String password;
    private String rol;

    // GET y SET

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
