package pe.com.Entregable.bien.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.bien.dto.BienRequestDTO;
import pe.com.Entregable.bien.dto.BienResponseDTO;
import pe.com.Entregable.bien.dto.HistorialResponseDTO;
import pe.com.Entregable.bien.dto.MovimientoStockDTO;
import pe.com.Entregable.bien.modelo.Bien;
import pe.com.Entregable.bien.modelo.HistorialBien;
import pe.com.Entregable.bien.repositorio.BienRepositorio;
import pe.com.Entregable.bien.repositorio.HistorialBienRepositorio;
import pe.com.Entregable.enums.EstadoBien;
import pe.com.Entregable.enums.TipoMovimiento;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BienServicio implements IBienServicio{

    private final BienRepositorio bienRepositorio;
    private final HistorialBienRepositorio historialRepositorio;

    @Transactional(readOnly = true)
    public List<BienResponseDTO> listarBienes() {
        return bienRepositorio.findAll().stream().map(BienResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public BienResponseDTO crearBien(BienRequestDTO dto) {
        if (bienRepositorio.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un bien registrado con el nombre: " + dto.getNombre());
        }

        Bien bien = new Bien();
        bien.setNombre(dto.getNombre());
        bien.setDescripcion(dto.getDescripcion());
        bien.setCantidad(dto.getCantidad());
        bien.setEstado(EstadoBien.valueOf(dto.getEstado()));

        Bien guardado = bienRepositorio.save(bien);

        registrarHistorial(guardado, TipoMovimiento.ENTRADA, dto.getCantidad(), "Stock Inicial");

        return new BienResponseDTO(guardado);
    }

    @Transactional
    public BienResponseDTO actualizarDatosBien(Integer id, BienRequestDTO dto) {

        if (bienRepositorio.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un bien registrado con el nombre: " + dto.getNombre());
        }

        Bien bien = bienRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", id));

        bien.setNombre(dto.getNombre());
        bien.setDescripcion(dto.getDescripcion());
        bien.setEstado(EstadoBien.valueOf(dto.getEstado()));
        return new BienResponseDTO(bienRepositorio.save(bien));
    }

    @Transactional
    public void registrarMovimiento(Integer idBien, MovimientoStockDTO dto) {
        Bien bien = bienRepositorio.findById(idBien)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", idBien));

        TipoMovimiento tipo = TipoMovimiento.valueOf(dto.getTipo());
        int cantidad = dto.getCantidad();

        // Actualizar Stock del Bien
        if (tipo == TipoMovimiento.ENTRADA) {
            bien.setCantidad(bien.getCantidad() + cantidad);
        } else {
            if (bien.getCantidad() < cantidad) {
                throw new RuntimeException("Stock insuficiente para realizar la salida.");
            }
            bien.setCantidad(bien.getCantidad() - cantidad);
        }

        Bien bienActualizado = bienRepositorio.save(bien);

        LocalDateTime fecha = (dto.getFecha() != null && !dto.getFecha().isEmpty())
                ? LocalDateTime.parse(dto.getFecha())
                : LocalDateTime.now();

        registrarHistorial(bienActualizado, tipo, cantidad, dto.getMotivo(), fecha);
    }

    private void registrarHistorial(Bien bien, TipoMovimiento tipo, int cantidad, String motivo) {
        registrarHistorial(bien, tipo, cantidad, motivo, LocalDateTime.now());
    }

    private void registrarHistorial(Bien bien, TipoMovimiento tipo, int cantidad, String motivo, LocalDateTime fecha) {
        HistorialBien historial = new HistorialBien();
        historial.setBien(bien);
        historial.setTipo(tipo);
        historial.setCantidad(cantidad);
        historial.setMotivo(motivo);
        historial.setFecha(fecha);
        historial.setStockResultante(bien.getCantidad());
        historialRepositorio.save(historial);
    }

    @Transactional(readOnly = true)
    public List<HistorialResponseDTO> listarHistorial(Integer idBien) {
        Bien bien = bienRepositorio.findById(idBien)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", idBien));

        return historialRepositorio.findByBienOrderByFechaDesc(bien)
                .stream()
                .map(HistorialResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarBien(Integer id) {
        Bien bien = bienRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", id));
        bienRepositorio.delete(bien);
    }

    @Transactional(readOnly = true)
    public List<BienResponseDTO> buscarBienes(String termino) {
        return bienRepositorio.buscarPorTermino(termino)
                .stream()
                .map(BienResponseDTO::new)
                .collect(Collectors.toList());
    }

}