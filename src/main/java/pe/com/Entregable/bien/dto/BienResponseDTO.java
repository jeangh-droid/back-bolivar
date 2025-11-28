package pe.com.Entregable.bien.dto;

import lombok.Data;
import pe.com.Entregable.bien.modelo.Bien;

@Data
public class BienResponseDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private String estado;

    public BienResponseDTO(Bien bien) {
        this.id = bien.getId();
        this.nombre = bien.getNombre();
        this.descripcion = bien.getDescripcion();
        this.cantidad = bien.getCantidad();
        this.estado = bien.getEstado().name();
    }
}