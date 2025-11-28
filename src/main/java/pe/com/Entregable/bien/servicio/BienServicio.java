package pe.com.Entregable.bien.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.bien.dto.BienRequestDTO;
import pe.com.Entregable.bien.dto.BienResponseDTO;
import pe.com.Entregable.bien.modelo.Bien;
import pe.com.Entregable.bien.repositorio.BienRepositorio;
import pe.com.Entregable.enums.EstadoBien;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BienServicio implements IBienServicio {

    private final BienRepositorio bienRepositorio;

    public List<BienResponseDTO> listarBienes() {
        return bienRepositorio.findAll().stream()
                .map(BienResponseDTO::new)
                .collect(Collectors.toList());
    }

    public BienResponseDTO obtenerBienPorId(Integer id) {
        Bien bien = bienRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", id));
        return new BienResponseDTO(bien);
    }

    @Transactional
    public BienResponseDTO crearBien(BienRequestDTO dto) {
        Bien bien = new Bien();
        bien.setNombre(dto.getNombre());
        bien.setDescripcion(dto.getDescripcion());
        bien.setCantidad(dto.getCantidad());
        bien.setEstado(EstadoBien.valueOf(dto.getEstado().toUpperCase()));

        return new BienResponseDTO(bienRepositorio.save(bien));
    }

    @Transactional
    public BienResponseDTO actualizarBien(Integer id, BienRequestDTO dto) {
        Bien bien = bienRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", id));

        bien.setNombre(dto.getNombre());
        bien.setDescripcion(dto.getDescripcion());
        bien.setCantidad(dto.getCantidad());
        bien.setEstado(EstadoBien.valueOf(dto.getEstado().toUpperCase()));

        return new BienResponseDTO(bienRepositorio.save(bien));
    }

    @Transactional
    public void eliminarBien(Integer id) {
        Bien bien = bienRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bien", "id", id));
        bienRepositorio.delete(bien);
    }
}