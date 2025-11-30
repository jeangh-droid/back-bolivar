package pe.com.Entregable.puesto.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.enums.EstadoPuesto;
import pe.com.Entregable.enums.LicenciaFuncionamiento;
import pe.com.Entregable.socio.modelo.Socio;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Puesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_socio", nullable = true)
    private Socio socio;

    @Column(nullable = false, length = 100)
    private String ubicacion;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroPuesto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenciaFuncionamiento licenciaFuncionamiento = LicenciaFuncionamiento.VIGENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPuesto estado = EstadoPuesto.INACTIVO;
}
