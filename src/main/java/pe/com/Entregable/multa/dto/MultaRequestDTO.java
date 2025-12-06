package pe.com.Entregable.multa.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MultaRequestDTO {
    private Integer idSocio;
    private String motivo;
    private BigDecimal monto;
    private String estado;
}