package service.to;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import jakarta.ws.rs.core.UriInfo;

public class FacturaTo {

    private Integer id;
    private String numero;
    private Date fecha;
    private float descuento;
    private float total;
    private ClienteTo clienteTo;
    
    // ATENCIÓN: Solo será no nulo cuando la factura RECIÉN se crea. 
    private String tokenRevelacion; 

    private Map<String, String> _links = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ClienteTo getClienteTo() {
        return clienteTo;
    }

    public void setClienteTo(ClienteTo clienteTo) {
        this.clienteTo = clienteTo;
    }

    public String getTokenRevelacion() {
        return tokenRevelacion;
    }

    public void setTokenRevelacion(String tokenRevelacion) {
        this.tokenRevelacion = tokenRevelacion;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

    public void buildURI(UriInfo uriInfo) {
    }
}
