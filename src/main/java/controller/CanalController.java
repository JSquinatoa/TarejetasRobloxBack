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
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import security.Roles;
import service.ICanalService;
import service.mapper.CanalMapper;
import service.to.CanalTo;
import service.exception.CanalNotFoundException;

@Path("canales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CanalController {

    @Inject
    private ICanalService canalService;

    @POST
    @Path("")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Guardar un Canal", description = "Esta capacidad me permite guardar un Canal")
    public Response guardar(@RequestBody CanalTo canalTo) {
        this.canalService.guardarCanal(CanalMapper.toEntity(canalTo));
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Listar canales", description = "Obtener lista de todos los canales")
    public Response listarTodos() {
        List<CanalTo> canales = this.canalService.buscarTodos().stream()
                .map(CanalMapper::toTo)
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(canales).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Buscar canal por ID", description = "Obtiene un canal por su identificador")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            CanalTo canalTo = CanalMapper.toTo(this.canalService.buscarPorId(id));
            return Response.status(Response.Status.OK).entity(canalTo).build();
        } catch (CanalNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Actualizar canal completo", description = "Reemplaza todos los datos del canal")
    public Response actualizarCompleto(@PathParam("id") Integer id, @RequestBody CanalTo canalTo) {
        try {
            canalTo.setId(id);
            this.canalService.actualizar(CanalMapper.toEntity(canalTo));
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CanalNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Actualizar canal parcialmente", description = "Actualiza solo los campos proporcionados")
    public Response actualizarParcial(@PathParam("id") Integer id, @RequestBody CanalTo canalTo) {
        try {
            CanalTo existenteTo = CanalMapper.toTo(this.canalService.buscarPorId(id));

            if (canalTo.getNombre() != null) {
                existenteTo.setNombre(canalTo.getNombre());
            }
            if (canalTo.getColor() != null) {
                existenteTo.setColor(canalTo.getColor());
            }
            if (canalTo.getEstado() != null) {
                existenteTo.setEstado(canalTo.getEstado());
            }

            this.canalService.actualizar(CanalMapper.toEntity(existenteTo));
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CanalNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Eliminar canal", description = "Borra un canal por su identificador")
    public Response borrarPorId(@PathParam("id") Integer id) {
        try {
            this.canalService.borrarPorId(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CanalNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
