package pe.com.Entregable.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.socio.modelo.Socio;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocioRequestDTO {
    private String username;
    private String dni;
    private String nombre;
    private String apellido;
    private String numero;
    private String password;
    private String direccion;
    private String carnetSanidad;
    private String tarjetaSocio;
}
