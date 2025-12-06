package pe.com.Entregable.bien.servicio;

import pe.com.Entregable.bien.dto.BienRequestDTO;
import pe.com.Entregable.bien.dto.BienResponseDTO;
import pe.com.Entregable.bien.dto.HistorialResponseDTO;
import pe.com.Entregable.bien.dto.MovimientoStockDTO;

import java.util.List;

public interface IBienServicio {
    List<BienResponseDTO> listarBienes();
    BienResponseDTO crearBien(BienRequestDTO dto);
    void eliminarBien(Integer id);
    List<HistorialResponseDTO> listarHistorial(Integer idBien);
    void registrarMovimiento(Integer idBien, MovimientoStockDTO dto);
    BienResponseDTO actualizarDatosBien(Integer id, BienRequestDTO dto);
    List<BienResponseDTO> buscarBienes(String termino);
}
