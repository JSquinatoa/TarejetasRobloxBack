package controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import security.Roles;
import service.IAdministradorService;
import service.exception.AdministradorAuthException;
import service.exception.AdministradorNotFoundException;
import service.mapper.AdministradorMapper;
import service.to.AdministradorTo;
import service.to.LoginRequestTo;
import service.to.LoginResponseTo;

@Path("administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorController {

    @Inject
    private IAdministradorService administradorService;

    // ────────────────────────────────────────────────
    // AUTENTICACIÓN — Endpoint público (sin @RolesAllowed)
    // ────────────────────────────────────────────────

    @POST
    @Path("login")
    @PermitAll
    // @PermitAll → Este endpoint es público. No necesita token.
    // Cualquiera puede llamarlo; es el que genera el token al hacer login.
    @Operation(summary = "Login de administrador", description = "Verifica credenciales y devuelve un JWT si son correctas")
    public Response login(@RequestBody LoginRequestTo loginRequest) {
        try {
            LoginResponseTo lrto = this.administradorService.autenticar(loginRequest);
            return Response.status(Response.Status.OK).entity(lrto).build();
        } catch (AdministradorAuthException e) {
            // 401 Unauthorized → credenciales incorrectas
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("setup")
    @RolesAllowed(Roles.SUPER_ADMIN)
    @Operation(summary = "Setup inicial", description = "Crea el primer administrador del sistema sin requerir autenticación")
    public Response setup(@RequestBody AdministradorTo administradorTo) {
        try {
            administradorTo.setRol(Roles.SUPER_ADMIN); // siempre SuperAdmin en el setup inicial
            this.administradorService.guardarAdministrador(AdministradorMapper.toEntity(administradorTo));
            return Response.status(Response.Status.CREATED)
                    .entity("Administrador inicial creado correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // ────────────────────────────────────────────────
    // CRUD — Endpoints protegidos con JWT
    // ────────────────────────────────────────────────

    @POST
    @Path("")
    @RolesAllowed(Roles.SUPER_ADMIN)
    @Operation(summary = "Registrar administrador", description = "Crea un nuevo administrador en el sistema")
    public Response registrar(@RequestBody AdministradorTo administradorTo) {
        this.administradorService.guardarAdministrador(AdministradorMapper.toEntity(administradorTo));
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    // Cualquier administrador autenticado puede ver la lista.
    @Operation(summary = "Listar administradores", description = "Obtiene la lista de todos los administradores")
    public Response listarTodos() {
        List<AdministradorTo> administradores = this.administradorService.buscarTodos().stream()
                .map(AdministradorMapper::toTo)
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(administradores).build();
    }

/* 
    Se queda ahi por el momento porque no estoy implementado nada
    @GET
    @Path("{id}")
    @RolesAllowed(Roles.ACCESO_ADMINISTRADORES)
    @Operation(summary = "Buscar administrador por ID", description = "Obtiene un administrador por su identificador")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            AdministradorTo administradorTo = AdministradorMapper.toTo(this.administradorService.buscarPorId(id));
            return Response.status(Response.Status.OK).entity(administradorTo).build();
        } catch (AdministradorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
     */

/*  
    se queda atras por el momento este end point
    @PUT
    @Path("{id}")
    @RolesAllowed(Roles.SUPER_ADMIN)
    // Solo SuperAdmin puede modificar datos de otros administradores.
    @Operation(summary = "Actualizar administrador completo", description = "Reemplaza todos los datos del administrador")
    public Response actualizarCompleto(@PathParam("id") Integer id, @RequestBody AdministradorTo administradorTo) {
        try {
            administradorTo.setId(id);
            this.administradorService.actualizar(AdministradorMapper.toEntity(administradorTo));
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (AdministradorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    } */

    // actulizar parcialmente
    @PATCH
    @Path("{id}")
    @RolesAllowed(Roles.SUPER_ADMIN)
    @Operation(summary = "Actualizar administrador parcialmente", description = "Actualiza solo los campos proporcionados")
    public Response actualizarParcial(@PathParam("id") Integer id, @RequestBody AdministradorTo administradorTo) {
        try {
            AdministradorTo adminstradorExistente = AdministradorMapper.toTo(this.administradorService.buscarPorId(id));

            if (administradorTo.getNombre() != null) {
                adminstradorExistente.setNombre(administradorTo.getNombre());                
            }
            if (administradorTo.getUsuario() != null) {
                adminstradorExistente.setUsuario(administradorTo.getUsuario());                
            }
            if (administradorTo.getPassword() != null) {
                adminstradorExistente.setPassword(administradorTo.getPassword());               
            }
            if (administradorTo.getRol() != null) {
                adminstradorExistente.setRol(administradorTo.getRol());                
            }

            this.administradorService.actualizar(AdministradorMapper.toEntity(adminstradorExistente));
            
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (AdministradorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }   

    @DELETE
    @Path("{id}")
    @RolesAllowed(Roles.SUPER_ADMIN)
    // Solo SuperAdmin puede eliminar administradores.
    @Operation(summary = "Eliminar administrador", description = "Borra un administrador por su identificador")
    public Response borrarPorId(@PathParam("id") Integer id) {
        try {
            this.administradorService.borrarPorId(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (AdministradorNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
