package pe.com.Entregable.login.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.Entregable.login.dto.UsuarioPerfilDTO;
import pe.com.Entregable.login.modelo.Usuario;
import pe.com.Entregable.login.repositorio.UsuarioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/perfil")
    @PreAuthorize("isAuthenticated()") // Cualquiera con token puede ver su propio perfil
    public ResponseEntity<UsuarioPerfilDTO> obtenerPerfil() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameActual = auth.getName();

        Usuario usuario = usuarioRepositorio.findByUsername(usernameActual)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", usernameActual));

        return ResponseEntity.ok(new UsuarioPerfilDTO(usuario));
    }
}