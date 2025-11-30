package pe.com.Entregable.pago.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoRequestDTO {
    private Integer idSocio;

    private Integer idMulta;

    private String tipoPago;
    private BigDecimal monto;
    private String fechaPago;
    private String observacion;
}