package pe.com.Entregable.multa.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.multa.dto.MultaRequestDTO;
import pe.com.Entregable.multa.dto.MultaResponseDTO;
import pe.com.Entregable.multa.modelo.Multa;
import pe.com.Entregable.multa.repositorio.MultaRepositorio;
import pe.com.Entregable.multa.servicio.MultaServicio;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/multas")
@RequiredArgsConstructor
public class MultaControlador {

    private final MultaServicio multaService;
    private final MultaRepositorio multaRepositorio;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MultaResponseDTO> listarMultas() {
        return multaService.listarMultas();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MultaResponseDTO> crearMulta(@RequestBody MultaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(multaService.crearMulta(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MultaResponseDTO> actualizarMulta(@PathVariable Integer id, @RequestBody MultaRequestDTO dto) {
        return ResponseEntity.ok(multaService.actualizarMulta(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarMulta(@PathVariable Integer id) {
        multaService.eliminarMulta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pendientes/{idSocio}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MultaResponseDTO>> listarMultasPendientesPorSocio(@PathVariable Integer idSocio) {
        List<Multa> multas = multaRepositorio.findBySocioIdAndEstado(idSocio, EstadoMulta.PENDIENTE);
        List<MultaResponseDTO> dtos = multas
                .stream()
                .map(MultaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/mis-multas")
    @PreAuthorize("hasRole('SOCIO')")
    public List<MultaResponseDTO> listarMisMultas() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return multaService.listarMultasPorUsuario(username);
    }

}