package pe.com.Entregable.dashboard.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.multa.repositorio.MultaRepositorio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardControlador {

    private final SocioRepositorio socioRepositorio;
    private final MultaRepositorio multaRepositorio;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("totalSocios", socioRepositorio.count());

        stats.put("multasPendientes", multaRepositorio.countByEstado(EstadoMulta.PENDIENTE));

        return ResponseEntity.ok(stats);
    }
}