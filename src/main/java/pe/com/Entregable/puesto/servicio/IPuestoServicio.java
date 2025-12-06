package pe.com.Entregable.puesto.servicio;

import pe.com.Entregable.puesto.dto.PuestoRequestDTO;
import pe.com.Entregable.puesto.dto.PuestoResponseDTO;
import java.util.List;

public interface IPuestoServicio {
    List<PuestoResponseDTO> listarPuestos();
    PuestoResponseDTO actualizarPuesto(Integer idPuesto, PuestoRequestDTO dto);
    List<PuestoResponseDTO> listarPuestosLibres();
    List<PuestoResponseDTO> buscarPuestos(String termino);
}