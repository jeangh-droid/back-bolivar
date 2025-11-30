package pe.com.Entregable.socio.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import pe.com.Entregable.enums.Estado;
import pe.com.Entregable.enums.Sanidad;
import pe.com.Entregable.login.modelo.Usuario;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "socios")
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Usuario usuario;

    @Column(length = 200)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "carnet_sanidad")
    private Sanidad carnetSanidad = Sanidad.VIGENTE;

    @Column(name = "tarjeta_socio", length = 50)
    private String tarjetaSocio;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
