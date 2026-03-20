package security;

/**
 * Clase estática para mantener los nombres de los roles de forma centralizada.
 * Si en el futuro cambian o se añaden nuevos, solo se modifica aquí.
 */
public final class Roles {

    // Roles Reales (los que vienen de la base de datos)
    public static final String SUPER_ADMIN = "SuperAdmin";
    public static final String ADMIN = "Admin";

    // Permisos Agrupados / Roles Lógicos
    // Si mañana agregas "Usuario", solo lo incluyes en la capa Service para este grupo
    // y no necesitas tocar NINGÚN Controller.
    public static final String ACCESO_ADMINISTRADORES = "AccesoAdministradores";

    private Roles() {
        // Constructor privado para evitar que se instancie la clase
    }
}

