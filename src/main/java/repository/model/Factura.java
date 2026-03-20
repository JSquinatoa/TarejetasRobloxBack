package repository.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fact_id")
    private Integer id;
    
    @Column(name = "fact_numero")
    private String numero;
    
    @Column(name = "fact_fecha")
    private Date fecha;
    
    @Column(name = "fact_descuento")
    private float descuento;
    
    @Column(name = "fact_total")
    private float total;
    
    @Column(name = "fact_token_revelacion")
    private String tokenRevelacion;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "fact_cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fact_admin_id")
    private Administrador administrador;

    @OneToMany(mappedBy = "factura")
    private List<Tarjeta> tarjetas = new ArrayList<>();

    // GET y SET
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

    public String getTokenRevelacion() {
        return tokenRevelacion;
    }

    public void setTokenRevelacion(String tokenRevelacion) {
        this.tokenRevelacion = tokenRevelacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
