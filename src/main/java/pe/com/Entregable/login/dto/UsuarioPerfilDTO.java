package pe.com.Entregable.login.dto;

import lombok.Data;
import pe.com.Entregable.login.modelo.Usuario;

@Data
public class UsuarioPerfilDTO {
    private String username;
    private String dni;
    private String nombre;
    private String apellido;
    private String numero;
    private String rol;

    public UsuarioPerfilDTO(Usuario usuario) {
        this.username = usuario.getUsername();
        this.dni = usuario.getDni();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.numero = usuario.getNumero();
        this.rol = usuario.getPrivilegio().getNombre();
    }
}