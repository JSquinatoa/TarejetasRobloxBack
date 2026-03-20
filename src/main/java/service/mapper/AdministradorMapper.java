package service.mapper;

import repository.model.Administrador;
import service.to.AdministradorTo;

public class AdministradorMapper {

    // Método para mostrar al público (sin exponer el password)
    public static AdministradorTo toTo(Administrador administrador) {
        if (administrador == null) {
            return null;
        }

        AdministradorTo ato = new AdministradorTo();
        ato.setId(administrador.getId());
        ato.setNombre(administrador.getNombre());
        ato.setUsuario(administrador.getUsuario());
        ato.setRol(administrador.getRol());

        return ato;
    }

    // Método para inserción/actualización en base de datos
    public static Administrador toEntity(AdministradorTo administradorTo) {
        Administrador a = new Administrador();

        a.setId(administradorTo.getId());
        a.setNombre(administradorTo.getNombre());
        a.setUsuario(administradorTo.getUsuario());
        // El password sí se copia en esta dirección (To → Entidad) para poder guardarlo en la BD.
        a.setPassword(administradorTo.getPassword());
        a.setRol(administradorTo.getRol());

        return a;
    }

}
