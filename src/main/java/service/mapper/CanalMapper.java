package service.mapper;

import repository.model.Canal;
import service.to.CanalTo;

public class CanalMapper {

    // Método para mostrar al público
    public static CanalTo toTo(Canal canal) {
        if (canal == null) {
            return null;
        }

        CanalTo cto = new CanalTo();
        cto.setId(canal.getId());
        cto.setNombre(canal.getNombre());
        cto.setColor(canal.getColor());
        cto.setEstado(canal.getEstado());

        return cto;
    }

    // Método para insersión en base de datos
    public static Canal toEntity(CanalTo canalTo) {
        Canal c = new Canal();

        c.setId(canalTo.getId());
        c.setNombre(canalTo.getNombre());
        c.setColor(canalTo.getColor());
        c.setEstado(canalTo.getEstado());

        return c;
    }

}
