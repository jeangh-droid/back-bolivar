package pe.com.Entregable.multa.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.enums.MotivoMulta;
import pe.com.Entregable.pago.modelo.Pago;
import pe.com.Entregable.socio.modelo.Socio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "multas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_socio", nullable = false)
    private Socio socio;

    @OneToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", nullable = false)
    private MotivoMulta motivo = MotivoMulta.FALTA_ASAMBLEA;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMulta estado = EstadoMulta.PENDIENTE;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();
}