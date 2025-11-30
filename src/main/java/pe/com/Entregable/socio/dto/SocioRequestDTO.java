package pe.com.Entregable.socio.dto;

import lombok.Data;

@Data
public class SocioRequestDTO {
    private String username;
    private String tipoDocumento;
    private String dni;
    private String nombre;
    private String apellido;
    private String numero;
    private String password;

    private String direccion;
    private String carnetSanidad;
    private String tarjetaSocio;

    private Integer idPuesto;

}
