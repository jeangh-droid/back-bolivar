package pe.com.Entregable.pago.dto;

import lombok.Data;
import pe.com.Entregable.pago.modelo.Pago;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Data
public class PagoResponseDTO {

    private Integer id;
    private String tipoPago;
    private BigDecimal monto;
    private String fechaPago;
    private String observacion;

    private Integer socioId;
    private String socioNombreCompleto;
    private String socioDni;

    public PagoResponseDTO(Pago pago) {
        this.id = pago.getId();
        this.tipoPago = pago.getTipoPago().name();
        this.monto = pago.getMonto();
        // Formatea la fecha para que sea compatible con <input type="datetime-local">
        this.fechaPago = pago.getFechaPago().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.observacion = pago.getObservacion();

        if (pago.getSocio() != null) {
            this.socioId = pago.getSocio().getId();
            this.socioDni = pago.getSocio().getUsuario().getDni();
            this.socioNombreCompleto = pago.getSocio().getUsuario().getNombre() + " " + pago.getSocio().getUsuario().getApellido();
        }
    }
}