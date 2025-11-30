package pe.com.Entregable.login.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.enums.Estado;
import pe.com.Entregable.enums.TipoDocumento;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false)
    private String nombre;
    private String apellido;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(unique = true, length = 20, nullable = false)
    private String dni;
    private String numero;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;

    @ManyToOne
    @JoinColumn(name = "id_privilegio", nullable = false)
    private Privilegio privilegio;
}
