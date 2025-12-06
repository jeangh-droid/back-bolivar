package pe.com.Entregable.bien.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.bien.dto.BienRequestDTO;
import pe.com.Entregable.bien.dto.BienResponseDTO;
import pe.com.Entregable.bien.dto.HistorialResponseDTO;
import pe.com.Entregable.bien.dto.MovimientoStockDTO;
import pe.com.Entregable.bien.modelo.Bien;
import pe.com.Entregable.bien.servicio.IBienServicio;

import java.util.List;

@RequestMapping("/bienes")
@RestController
@RequiredArgsConstructor
public class BienControlador {

    private final IBienServicio bienServicio;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BienResponseDTO> listarBienes() {
        return bienServicio.listarBienes();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BienResponseDTO> crearBien(@RequestBody BienRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bienServicio.crearBien(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BienResponseDTO> actualizarBien(@PathVariable Integer id, @RequestBody BienRequestDTO dto) {
        return ResponseEntity.ok(bienServicio.actualizarDatosBien(id, dto));
    }

    @PostMapping("/{id}/movimiento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> registrarMovimiento(@PathVariable Integer id, @RequestBody MovimientoStockDTO dto) {
        bienServicio.registrarMovimiento(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/historial")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HistorialResponseDTO>> listarHistorial(@PathVariable Integer id) {
        return ResponseEntity.ok(bienServicio.listarHistorial(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarBien(@PathVariable Integer id) {
        bienServicio.eliminarBien(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    public ResponseEntity<List<BienResponseDTO>> buscarBienes(@RequestParam String termino) {
        return ResponseEntity.ok(bienServicio.buscarBienes(termino));
    }

}
