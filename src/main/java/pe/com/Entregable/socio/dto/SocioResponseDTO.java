package pe.com.Entregable.socio.dto;

import lombok.Data;
import pe.com.Entregable.socio.modelo.Socio;

@Data
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

    private String estadoUsuario;
    private String numeroPuesto;
    private Integer idPuesto;

    public SocioResponseDTO(Socio socio) {
        this.id = socio.getId();

        if (socio.getUsuario() != null) {
            this.nombre = socio.getUsuario().getNombre();
            this.apellido = socio.getUsuario().getApellido();
            this.dni = socio.getUsuario().getDni();
            this.username = socio.getUsuario().getUsername();
            this.numero = socio.getUsuario().getNumero();
            if (socio.getUsuario().getEstado() != null) {
                this.estadoUsuario = socio.getUsuario().getEstado().name();
            } else {
                this.estadoUsuario = "INACTIVO";
            }
        } else {
            this.nombre = "N/A";
            this.estadoUsuario = "INACTIVO";
        }

        this.direccion = socio.getDireccion();

        if (socio.getCarnetSanidad() != null) {
            this.carnetSanidad = socio.getCarnetSanidad().name();
        } else {
            this.carnetSanidad = "DESCONOCIDO";
        }

        this.tarjetaSocio = socio.getTarjetaSocio();

        this.numeroPuesto = "";
        this.idPuesto = null;
    }

    public void setPuestoInfo(Integer id, String numero) {
        this.idPuesto = id;
        this.numeroPuesto = numero;
    }

}