package pe.com.Entregable.login.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.login.dto.LoginDTO;
import pe.com.Entregable.login.dto.LoginResponseDTO;
import pe.com.Entregable.login.servicio.AuthServicio;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthControlador {

    @Autowired
    private AuthServicio authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
