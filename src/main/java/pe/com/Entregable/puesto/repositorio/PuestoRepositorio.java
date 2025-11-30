package pe.com.Entregable.puesto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.enums.EstadoPuesto;
import pe.com.Entregable.puesto.modelo.Puesto;
import pe.com.Entregable.socio.modelo.Socio;

import java.util.List;
import java.util.Optional;

public interface PuestoRepositorio extends JpaRepository<Puesto, Integer> {

    List<Puesto> findByEstado(EstadoPuesto estado);

    Optional<Puesto> findBySocio(Socio socio);
}

