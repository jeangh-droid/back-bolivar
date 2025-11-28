package pe.com.Entregable.bien.servicio;

import pe.com.Entregable.bien.dto.BienRequestDTO;
import pe.com.Entregable.bien.dto.BienResponseDTO;

import java.util.List;

public interface IBienServicio {
    List<BienResponseDTO> listarBienes();
    BienResponseDTO obtenerBienPorId(Integer id);
    BienResponseDTO crearBien(BienRequestDTO dto);
    BienResponseDTO actualizarBien(Integer id, BienRequestDTO dto);
    void eliminarBien(Integer id);
}
