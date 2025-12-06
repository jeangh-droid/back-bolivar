package pe.com.Entregable.bien.dto;

import lombok.Data;
import pe.com.Entregable.bien.modelo.HistorialBien;
import java.time.format.DateTimeFormatter;

@Data
public class HistorialResponseDTO {
    private String fecha;
    private String motivo;
    private Integer cantidadEntrada;
    private Integer cantidadSalida;
    private Integer stockResultante;

    public HistorialResponseDTO(HistorialBien historial) {
        this.fecha = historial.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.motivo = historial.getMotivo();
        this.stockResultante = historial.getStockResultante();

        // Separamos en columnas según el tipo
        if (historial.getTipo().name().equals("ENTRADA")) {
            this.cantidadEntrada = historial.getCantidad();
            this.cantidadSalida = 0; // O null, para visualización
        } else {
            this.cantidadEntrada = 0;
            this.cantidadSalida = historial.getCantidad();
        }
    }
}