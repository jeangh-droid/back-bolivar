package pe.com.Entregable.puesto.dto;

import lombok.Data;
import pe.com.Entregable.puesto.modelo.Puesto;

@Data
public class PuestoResponseDTO {

    private Integer id;
    private String numeroPuesto;
    private String ubicacion;
    private String licenciaFuncionamiento;
    private String estado;

    private Integer socioId;
    private String socioNombreCompleto;
    private String socioDni;

    public PuestoResponseDTO(Puesto puesto) {
        this.id = puesto.getId();
        this.numeroPuesto = puesto.getNumeroPuesto();
        this.ubicacion = puesto.getUbicacion();
        this.licenciaFuncionamiento = puesto.getLicenciaFuncionamiento().name();
        this.estado = puesto.getEstado().name();

        // Incluye la información del socio si no es nulo
        if (puesto.getSocio() != null) {
            this.socioId = puesto.getSocio().getId();
            this.socioDni = puesto.getSocio().getUsuario().getDni();
            this.socioNombreCompleto = puesto.getSocio().getUsuario().getNombre() + " " + puesto.getSocio().getUsuario().getApellido();
        }
    }
}