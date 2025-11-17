package pe.com.Entregable.puesto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.puesto.modelo.Puesto;

public interface PuestoRepositorio extends JpaRepository<Puesto, Integer> {
}
