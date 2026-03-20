package controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam; 
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.Roles;
import service.IClienteService;
import service.mapper.ClienteMapper;
import service.to.ClienteTo;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    @Inject
    private IClienteService clienteService;

    // guardar cliente
    @POST
    @Path("")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Guardar un Cliente", description = "Esta capacidad me permite guardar un Cliente")
    public Response guardar(@RequestBody ClienteTo clienteTo){
        this.clienteService.guardarCLiente(ClienteMapper.toEntity(clienteTo));
        return Response.status(Response.Status.CREATED).build();
    }

    // buscar todos
    @GET
    @Path("")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Buscar todos los Clientes", description = "Esta capacidad me permite buscar todos los Clientes")
    public Response listarTodos(){

        List<ClienteTo> cto = this.clienteService.buscarTodos().stream()
                .map(ClienteMapper::toTo)
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(cto).build();
    }

    // actualizar
    @PUT
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Actualizar un Cliente", description = "Esta capacidad me permite actualizar un Cliente")
    public Response actualizar(@PathParam("id") Integer id, @RequestBody ClienteTo clienteTo){
        this.clienteService.actualizarCliente(ClienteMapper.toEntity(clienteTo));
        return Response.status(Response.Status.OK).build();
    }

    // borrar
    @DELETE
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Eliminar un Cliente", description = "Esta capacidad me permite eliminar un Cliente")
    public Response borrar(@PathParam("id") Integer id){
        this.clienteService.borrarPorId(id);
        return Response.status(Response.Status.OK).build();
    }

}
