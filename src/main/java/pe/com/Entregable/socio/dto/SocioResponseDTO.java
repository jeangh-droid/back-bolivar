package pe.com.Entregable.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.socio.modelo.Socio;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocioResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String username;
    private String numero;
    private String direccion;
    private String carnetSanidad;
    private String tarjetaSocio;

    public SocioResponseDTO(Socio socio) {
        this.id = socio.getId();
        this.nombre = socio.getUsuario().getNombre();
        this.apellido = socio.getUsuario().getApellido();
        this.username = socio.getUsuario().getUsername();
        this.numero = socio.getUsuario().getNumero();
        this.dni = socio.getUsuario().getDni();
        this.direccion = socio.getDireccion();
        this.carnetSanidad = socio.getCarnetSanidad().name();
        this.tarjetaSocio = socio.getTarjetaSocio();
    }
}
