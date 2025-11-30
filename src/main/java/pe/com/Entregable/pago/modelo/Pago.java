package pe.com.Entregable.pago.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.enums.TipoPago;
import pe.com.Entregable.socio.modelo.Socio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_socio", nullable = false)
    private Socio socio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPago tipoPago;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String observacion;
}