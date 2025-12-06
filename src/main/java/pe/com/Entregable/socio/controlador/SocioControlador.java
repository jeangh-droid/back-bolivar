package pe.com.Entregable.socio.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.socio.dto.SocioRequestDTO;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.socio.servicio.ISocioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/socios")
@RequiredArgsConstructor
public class SocioControlador {

    private final ISocioService socioService;
    private final SocioRepositorio socioRepositorio;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SocioResponseDTO> listarSocios() {
        return socioService.listarSocios();
    }

    @GetMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocioResponseDTO> obtenerSocioPorId(@PathVariable Integer id) {
        SocioResponseDTO socio = socioService.obtenerSocioPorId(id);
        return ResponseEntity.ok(socio);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocioResponseDTO> agregarSocio(@RequestBody SocioRequestDTO socioRequestDTO) {
        SocioResponseDTO nuevoSocio = socioService.crearSocio(socioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }

    @PutMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SocioResponseDTO> modificarSocio(@PathVariable Integer id, @RequestBody SocioRequestDTO socioRequestDTO) {
        SocioResponseDTO socioActualizado = socioService.actualizarSocio(id, socioRequestDTO);
        return ResponseEntity.ok(socioActualizado);
    }

    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Integer id) {
        socioService.eliminarSocio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mi-perfil")
    @PreAuthorize("hasRole('SOCIO')")
    public ResponseEntity<SocioResponseDTO> obtenerMiPerfil() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        SocioResponseDTO perfil = socioService.obtenerPerfilSocio(username);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SocioResponseDTO>> buscarSocios(@RequestParam String termino) {
        return ResponseEntity.ok(socioService.buscarSocios(termino));
    }

    @GetMapping("/check-username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> verificarUsername(@PathVariable String username) {
        return ResponseEntity.ok(socioService.existeUsername(username));
    }

    @GetMapping("/activos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SocioResponseDTO>> listarSociosActivos() {
        return ResponseEntity.ok(socioService.listarSociosActivos());
    }

    @GetMapping("/disponibles-para-puesto")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SocioResponseDTO> listarSociosDisponibles() {
        return socioRepositorio.findSociosActivosSinPuesto()
                .stream()
                .map(SocioResponseDTO::new)
                .collect(Collectors.toList());
    }

}