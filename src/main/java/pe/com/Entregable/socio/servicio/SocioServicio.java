package pe.com.Entregable.socio.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.enums.Sanidad;
import pe.com.Entregable.login.modelo.Usuario;
import pe.com.Entregable.login.repositorio.UsuarioRepositorio;
import pe.com.Entregable.socio.dto.SocioRequestDTO;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import pe.com.Entregable.socio.modelo.Socio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocioServicio implements ISocioService {

    private final SocioRepositorio socioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<SocioResponseDTO> listarSocios() {
        return socioRepositorio
                .findAll()
                .stream()
                .map(SocioResponseDTO::new)
                .toList();
    }

    @Override
    public SocioResponseDTO obtenerSocioPorId(Integer idSocio) {
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));
        return new SocioResponseDTO(socio);
    }

    @Override
    @Transactional
    public SocioResponseDTO crearSocio(SocioRequestDTO socioRequestDTO) {
        // 1. Buscar el Usuario por DNI
        Usuario usuario = usuarioRepositorio.findByUsername(socioRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", socioRequestDTO.getUsername()));

        // 2. Mapear DTO a Entidad
        Socio socio = new Socio();
        mapDtoToEntity(socioRequestDTO, socio, usuario);

        // 3. Guardar y devolver DTO de respuesta
        Socio nuevoSocio = socioRepositorio.save(socio);
        return new SocioResponseDTO(nuevoSocio);
    }

    @Override
    @Transactional
    public SocioResponseDTO actualizarSocio(Integer idSocio, SocioRequestDTO socioRequestDTO) {
        // 1. Buscar el Socio existente
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));

        // 2. Buscar el Usuario (incluso si cambia)
        Usuario usuario = usuarioRepositorio.findByUsername(socioRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", socioRequestDTO.getUsername()));

        // 3. Mapear DTO a Entidad
        mapDtoToEntity(socioRequestDTO, socio, usuario);

        // 4. Guardar y devolver DTO de respuesta
        Socio socioActualizado = socioRepositorio.save(socio);
        return new SocioResponseDTO(socioActualizado);
    }

    @Override
    @Transactional
    public void eliminarSocio(Integer idSocio) {
        // 1. Buscar el Socio existente
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));

        // 2. Eliminar
        socioRepositorio.delete(socio);
    }

    private void mapDtoToEntity(SocioRequestDTO dto, Socio socio, Usuario usuario) {
        socio.setUsuario(usuario);
        socio.setDireccion(dto.getDireccion());
        socio.setTarjetaSocio(dto.getTarjetaSocio());

        try {
            socio.setCarnetSanidad(Sanidad.valueOf(dto.getCarnetSanidad().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valor de Carnet de Sanidad inválido: " + dto.getCarnetSanidad());
        }
    }
}