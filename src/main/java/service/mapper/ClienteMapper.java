package service.mapper;

import repository.model.Cliente;
import service.to.ClienteTo;

public class ClienteMapper {

    // método para mostrar la público
    public static ClienteTo toTo (Cliente cliente){
        if (cliente == null) {
            return null;            
        }

        ClienteTo cto = new ClienteTo();
        cto.setId(cliente.getId());
        cto.setNombre(cliente.getNombre());
        cto.setCorreo(cliente.getCorreo());
        cto.setTelefono(cliente.getTelefono());
        cto.setDescuento(cliente.getDescuento());
        cto.setCanalContacto(CanalMapper.toTo(cliente.getCanalContacto()));
        
        return cto;

    }

    // Método para insersión en base de datos
    
    public static Cliente toEntity (ClienteTo clienteTo){

        Cliente c = new Cliente();
        c.setId(clienteTo.getId());
        c.setNombre(clienteTo.getNombre());
        c.setCorreo(clienteTo.getCorreo());
        c.setTelefono(clienteTo.getTelefono());
        c.setDescuento(clienteTo.getDescuento());
        c.setCanalContacto(CanalMapper.toEntity(clienteTo.getCanalContacto()));
        
        return c;
    }

}
