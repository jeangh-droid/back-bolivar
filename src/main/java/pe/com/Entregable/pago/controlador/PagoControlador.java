package pe.com.Entregable.pago.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.Entregable.pago.dto.PagoRequestDTO;
import pe.com.Entregable.pago.dto.PagoResponseDTO;
import pe.com.Entregable.pago.servicio.IPagoServicio;

import java.util.List;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoControlador {

    private final IPagoServicio pagoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')") // Permitimos a ambos ver
    public List<PagoResponseDTO> listarPagos() {
        return pagoService.listarPagos();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagoResponseDTO> obtenerPagoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagoResponseDTO> crearPago(@RequestBody PagoRequestDTO dto) {
        PagoResponseDTO pagoCreado = pagoService.crearPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagoResponseDTO> actualizarPago(@PathVariable Integer id, @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO pagoActualizado = pagoService.actualizarPago(id, dto);
        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPago(@PathVariable Integer id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}