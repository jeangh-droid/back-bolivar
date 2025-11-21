package pe.com.Entregable.pago.servicio;

import pe.com.Entregable.pago.dto.PagoRequestDTO;
import pe.com.Entregable.pago.dto.PagoResponseDTO;
import java.util.List;

public interface IPagoServicio {
    List<PagoResponseDTO> listarPagos();
    PagoResponseDTO obtenerPagoPorId(Integer id);
    PagoResponseDTO crearPago(PagoRequestDTO dto);
    PagoResponseDTO actualizarPago(Integer id, PagoRequestDTO dto);
    void eliminarPago(Integer id);
}