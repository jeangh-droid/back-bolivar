package pe.com.Entregable.bien.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.Entregable.enums.EstadoBien;

@Entity
@Table(name = "inventario_bienes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 144)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private Integer cantidad = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private EstadoBien estado = EstadoBien.BUENO;
}