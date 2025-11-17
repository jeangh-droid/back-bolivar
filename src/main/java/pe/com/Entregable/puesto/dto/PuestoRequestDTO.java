package pe.com.Entregable.puesto.dto;

import lombok.Data;

@Data
public class PuestoRequestDTO {
    private Integer idSocio;
    private String licenciaFuncionamiento;
    private String estado;
}