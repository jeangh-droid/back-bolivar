package pe.com.Entregable.multa.dto;

import lombok.Data;
import pe.com.Entregable.multa.modelo.Multa;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Data
public class MultaResponseDTO {
    private Integer id;
    private String motivo;
    private BigDecimal monto;
    private String estado;
    private String fechaEmision;

    // Datos del Socio
    private Integer socioId;
    private String socioNombreCompleto;
    private String socioDni;

    // Datos del Pago
    private Integer pagoId;

    public MultaResponseDTO(Multa multa) {
        this.id = multa.getId();
        this.motivo = multa.getMotivo().name();
        this.monto = multa.getMonto();
        this.estado = multa.getEstado().name();
        this.fechaEmision = multa.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        if (multa.getSocio() != null) {
            this.socioId = multa.getSocio().getId();
            this.socioDni = multa.getSocio().getUsuario().getDni();
            this.socioNombreCompleto = multa.getSocio().getUsuario().getNombre() + " " + multa.getSocio().getUsuario().getApellido();
        }

        if (multa.getPago() != null) {
            this.pagoId = multa.getPago().getId();
        }
    }
}