package pe.com.Entregable.pago.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.enums.TipoPago;
import pe.com.Entregable.multa.modelo.Multa;
import pe.com.Entregable.multa.repositorio.MultaRepositorio;
import pe.com.Entregable.pago.dto.PagoRequestDTO;
import pe.com.Entregable.pago.dto.PagoResponseDTO;
import pe.com.Entregable.pago.modelo.Pago;
import pe.com.Entregable.pago.repositorio.PagoRepositorio;
import pe.com.Entregable.socio.modelo.Socio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServicio implements IPagoServicio {

    private final PagoRepositorio pagoRepositorio;
    private final SocioRepositorio socioRepositorio;
    private final MultaRepositorio multaRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPagos() {
        return pagoRepositorio.findAll().stream()
                .map(PagoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPagoPorId(Integer id) {
        Pago pago = pagoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago", "id", id));
        return new PagoResponseDTO(pago);
    }

    @Override
    @Transactional
    public PagoResponseDTO crearPago(PagoRequestDTO dto) {
        Socio socio = socioRepositorio.findById(dto.getIdSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", dto.getIdSocio()));

        // 1. Crear y guardar el Pago
        Pago pago = new Pago();
        pago.setSocio(socio);
        pago.setTipoPago(TipoPago.valueOf(dto.getTipoPago().toUpperCase()));
        pago.setMonto(dto.getMonto());
        pago.setObservacion(dto.getObservacion());

        if(dto.getFechaPago() != null && !dto.getFechaPago().isEmpty()) {
            pago.setFechaPago(LocalDateTime.parse(dto.getFechaPago()));
        } else {
            pago.setFechaPago(LocalDateTime.now());
        }

        Pago pagoGuardado = pagoRepositorio.save(pago);

        if (pago.getTipoPago() == TipoPago.MULTA) {
            if (dto.getIdMulta() == null) {
                throw new RuntimeException("El ID de la multa es obligatorio para pagos de tipo MULTA");
            }

            Multa multa = multaRepositorio.findById(dto.getIdMulta())
                    .orElseThrow(() -> new ResourceNotFoundException("Multa", "id", dto.getIdMulta()));

            if (!multa.getSocio().getId().equals(socio.getId())) {
                throw new RuntimeException("La multa no corresponde al socio seleccionado");
            }

            multa.setEstado(EstadoMulta.PAGADO);
            multa.setPago(pagoGuardado);

            multaRepositorio.save(multa);         }

        return new PagoResponseDTO(pagoGuardado);
    }

    @Override
    @Transactional
    public PagoResponseDTO actualizarPago(Integer id, PagoRequestDTO dto) {
        Pago pago = pagoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago", "id", id));

        Socio socio = socioRepositorio.findById(dto.getIdSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", dto.getIdSocio()));

        pago.setSocio(socio);
        pago.setTipoPago(TipoPago.valueOf(dto.getTipoPago().toUpperCase()));
        pago.setMonto(dto.getMonto());
        pago.setObservacion(dto.getObservacion());
        pago.setFechaPago(LocalDateTime.parse(dto.getFechaPago()));

        Pago pagoActualizado = pagoRepositorio.save(pago);
        return new PagoResponseDTO(pagoActualizado);
    }

    @Override
    @Transactional
    public void eliminarPago(Integer id) {

        Pago pago = pagoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago", "id", id));
        pagoRepositorio.delete(pago);
    }

    public List<PagoResponseDTO> listarPagosPorUsuario(String username) {
        return pagoRepositorio.findBySocioUsuarioUsername(username)
                .stream()
                .map(PagoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoResponseDTO> buscarPagos(String termino) {
        return pagoRepositorio.buscarPorTermino(termino)
                .stream()
                .map(PagoResponseDTO::new)
                .collect(Collectors.toList());
    }
}