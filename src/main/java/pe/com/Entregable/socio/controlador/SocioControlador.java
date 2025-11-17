package pe.com.Entregable.socio.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.socio.dto.SocioRequestDTO;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import pe.com.Entregable.socio.servicio.ISocioService;

import java.util.List;

@RestController
@RequestMapping("/socios")
@RequiredArgsConstructor
public class SocioControlador {

    private final ISocioService socioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SocioResponseDTO> listarSocios() {
        return socioService.listarSocios();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocioResponseDTO> obtenerSocioPorId(@PathVariable Integer id) {
        SocioResponseDTO socio = socioService.obtenerSocioPorId(id);
        return ResponseEntity.ok(socio);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocioResponseDTO> agregarSocio(@RequestBody SocioRequestDTO socioRequestDTO) {
        SocioResponseDTO nuevoSocio = socioService.crearSocio(socioRequestDTO);
        // Devuelve 201 Created + el recurso creado
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocioResponseDTO> modificarSocio(@PathVariable Integer id, @RequestBody SocioRequestDTO socioRequestDTO) {
        SocioResponseDTO socioActualizado = socioService.actualizarSocio(id, socioRequestDTO);
        return ResponseEntity.ok(socioActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Integer id) {
        socioService.eliminarSocio(id);
        return ResponseEntity.noContent().build();
    }
}