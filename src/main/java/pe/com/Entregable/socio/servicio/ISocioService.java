package pe.com.Entregable.socio.servicio;

import pe.com.Entregable.socio.dto.SocioRequestDTO;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import java.util.List;

public interface ISocioService {

    List<SocioResponseDTO> listarSocios();

    SocioResponseDTO obtenerSocioPorId(Integer idSocio);

    SocioResponseDTO crearSocio(SocioRequestDTO socioRequestDTO);

    SocioResponseDTO actualizarSocio(Integer idSocio, SocioRequestDTO socioRequestDTO);

    void eliminarSocio(Integer idSocio);

    SocioResponseDTO obtenerPerfilSocio(String username);

    List<SocioResponseDTO> buscarSocios(String termino);

    boolean existeUsername(String username);

    List<SocioResponseDTO> listarSociosActivos();
}