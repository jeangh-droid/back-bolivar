package pe.com.Entregable.bien.dto;

import lombok.Data;

@Data
public class MovimientoStockDTO {
    private String tipo;
    private Integer cantidad;
    private String motivo;
    private String fecha;
}