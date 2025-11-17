package pe.com.Entregable.puesto.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.enums.EstadoPuesto;
import pe.com.Entregable.enums.LicenciaFuncionamiento;
import pe.com.Entregable.puesto.dto.PuestoRequestDTO;
import pe.com.Entregable.puesto.dto.PuestoResponseDTO;
import pe.com.Entregable.puesto.modelo.Puesto;
import pe.com.Entregable.puesto.repositorio.PuestoRepositorio;
import pe.com.Entregable.socio.modelo.Socio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PuestoServicio implements IPuestoServicio {

    private final PuestoRepositorio puestoRepositorio;
    private final SocioRepositorio socioRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<PuestoResponseDTO> listarPuestos() {
        return puestoRepositorio.findAll().stream()
                .map(PuestoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PuestoResponseDTO actualizarPuesto(Integer idPuesto, PuestoRequestDTO dto) {

        Puesto puesto = puestoRepositorio.findById(idPuesto)
                .orElseThrow(() -> new ResourceNotFoundException("Puesto", "id", idPuesto));

        Socio socio = socioRepositorio.findById(dto.getIdSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", dto.getIdSocio()));

        puesto.setSocio(socio);
        puesto.setLicenciaFuncionamiento(LicenciaFuncionamiento.valueOf(dto.getLicenciaFuncionamiento().toUpperCase()));
        puesto.setEstado(EstadoPuesto.valueOf(dto.getEstado().toUpperCase()));

        Puesto puestoActualizado = puestoRepositorio.save(puesto);
        return new PuestoResponseDTO(puestoActualizado);
    }
}