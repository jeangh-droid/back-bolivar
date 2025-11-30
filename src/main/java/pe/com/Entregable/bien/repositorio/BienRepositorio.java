package pe.com.Entregable.bien.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.bien.modelo.Bien;

public interface BienRepositorio extends JpaRepository<Bien, Integer> {
}