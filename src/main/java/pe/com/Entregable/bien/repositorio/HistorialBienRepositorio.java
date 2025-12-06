package pe.com.Entregable.bien.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.bien.modelo.Bien;
import pe.com.Entregable.bien.modelo.HistorialBien;
import java.util.List;

public interface HistorialBienRepositorio extends JpaRepository<HistorialBien, Integer> {
    List<HistorialBien> findByBienOrderByFechaDesc(Bien bien);
}