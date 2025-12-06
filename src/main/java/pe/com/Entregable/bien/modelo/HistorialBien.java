package pe.com.Entregable.bien.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.enums.TipoMovimiento;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_bienes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialBien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_bien", nullable = false)
    private Bien bien;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(columnDefinition = "TEXT")
    private String motivo;

    private Integer stockResultante;
}