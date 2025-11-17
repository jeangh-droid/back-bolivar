package pe.com.Entregable.login.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    @GetMapping
    private String paginaPrincipal() {
        return "Bienvenido a una pagina resguardada";
    }

}

