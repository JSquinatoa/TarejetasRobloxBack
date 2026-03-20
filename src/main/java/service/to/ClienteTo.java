package service.to;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.UriInfo;


public class ClienteTo {

    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    private float descuento;
    private CanalTo canalContacto;
    private Map<String, String> _links = new HashMap<>();

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public CanalTo getCanalContacto() {
        return canalContacto;
    }

    public void setCanalContacto(CanalTo canalContacto) {
        this.canalContacto = canalContacto;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    // Método para obtener todas las facturas del cliente
    public void buildURI(UriInfo uriInfo) {

    }

}
