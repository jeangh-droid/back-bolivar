package pe.com.Entregable.bien.dto;

import lombok.Data;

@Data
public class BienRequestDTO {
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private String estado;
}