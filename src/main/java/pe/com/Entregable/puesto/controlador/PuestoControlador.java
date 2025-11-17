package pe.com.Entregable.puesto.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.puesto.dto.PuestoRequestDTO;
import pe.com.Entregable.puesto.dto.PuestoResponseDTO;
import pe.com.Entregable.puesto.servicio.IPuestoServicio;

import java.util.List;

@RestController
@RequestMapping("/puestos")
@RequiredArgsConstructor
public class PuestoControlador {

    private final IPuestoServicio puestoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<PuestoResponseDTO> listarPuestos() {
        return puestoService.listarPuestos();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PuestoResponseDTO> actualizarPuesto(
            @PathVariable Integer id,
            @RequestBody PuestoRequestDTO dto) {

        PuestoResponseDTO puestoActualizado = puestoService.actualizarPuesto(id, dto);
        return ResponseEntity.ok(puestoActualizado);
    }
}