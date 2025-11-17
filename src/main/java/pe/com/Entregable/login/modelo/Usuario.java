package pe.com.Entregable.login.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import pe.com.Entregable.enums.Estado;

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

    @Column(unique = true, length = 8, nullable = false)
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
