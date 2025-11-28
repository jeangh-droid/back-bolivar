package pe.com.Entregable.multa.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.enums.MotivoMulta;
import pe.com.Entregable.multa.dto.MultaRequestDTO;
import pe.com.Entregable.multa.dto.MultaResponseDTO;
import pe.com.Entregable.multa.modelo.Multa;
import pe.com.Entregable.multa.repositorio.MultaRepositorio;
import pe.com.Entregable.socio.modelo.Socio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultaServicio implements IMultaServicio{

    private final MultaRepositorio multaRepositorio;
    private final SocioRepositorio socioRepositorio;

    public List<MultaResponseDTO> listarMultas() {
        return multaRepositorio.findAll().stream()
                .map(MultaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MultaResponseDTO crearMulta(MultaRequestDTO dto) {
        Socio socio = socioRepositorio.findById(dto.getIdSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", dto.getIdSocio()));

        Multa multa = new Multa();
        multa.setSocio(socio);
        multa.setMotivo(MotivoMulta.valueOf(dto.getMotivo().toUpperCase()));
        multa.setMonto(dto.getMonto());
        multa.setEstado(EstadoMulta.PENDIENTE); // Por defecto PENDIENTE al crear
        multa.setFechaEmision(LocalDateTime.now());

        return new MultaResponseDTO(multaRepositorio.save(multa));
    }

    @Transactional
    public MultaResponseDTO actualizarMulta(Integer id, MultaRequestDTO dto) {
        Multa multa = multaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Multa", "id", id));

        Socio socio = socioRepositorio.findById(dto.getIdSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", dto.getIdSocio()));

        multa.setSocio(socio);
        multa.setMotivo(MotivoMulta.valueOf(dto.getMotivo().toUpperCase()));
        multa.setMonto(dto.getMonto());

        if (dto.getEstado() != null) {
            multa.setEstado(EstadoMulta.valueOf(dto.getEstado().toUpperCase()));
        }

        return new MultaResponseDTO(multaRepositorio.save(multa));
    }

    @Transactional
    public void eliminarMulta(Integer id) {
        Multa multa = multaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Multa", "id", id));
        multaRepositorio.delete(multa);
    }

    public List<MultaResponseDTO> listarMultasPorUsuario(String username) {
        return multaRepositorio.findBySocioUsuarioUsername(username)
                .stream()
                .map(MultaResponseDTO::new)
                .collect(Collectors.toList());
    }

}