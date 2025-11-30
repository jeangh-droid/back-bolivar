package pe.com.Entregable.login.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pe.com.Entregable.login.dto.LoginDTO;
import pe.com.Entregable.login.dto.LoginResponseDTO;
import pe.com.Entregable.login.modelo.Usuario;
import pe.com.Entregable.login.repositorio.UsuarioRepositorio;
import pe.com.Entregable.login.seguridad.UsuarioDetails;

@Service
@RequiredArgsConstructor
public class AuthServicio {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepositorio usuarioRepository;

    public LoginResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioDetails usuarioDetails = null;
        if (usuario != null) {
            usuarioDetails = new UsuarioDetails(usuario);
        }
        String token = jwtService.generateToken(usuarioDetails);

        return new LoginResponseDTO(token);
    }
}

