package pe.com.Entregable.multa.servicio;

import pe.com.Entregable.multa.dto.MultaRequestDTO;
import pe.com.Entregable.multa.dto.MultaResponseDTO;

import java.util.List;

public interface IMultaServicio {
    List<MultaResponseDTO> listarMultas();
    MultaResponseDTO crearMulta(MultaRequestDTO dto);
    MultaResponseDTO actualizarMulta(Integer id, MultaRequestDTO dto);
    void eliminarMulta(Integer id);
    List<MultaResponseDTO> buscarPorTermino(String termino);
}
