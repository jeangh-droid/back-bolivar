package pe.com.Entregable.puesto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.Entregable.enums.EstadoPuesto;
import pe.com.Entregable.puesto.modelo.Puesto;
import pe.com.Entregable.socio.modelo.Socio;

import java.util.List;
// import java.util.Optional;

public interface PuestoRepositorio extends JpaRepository<Puesto, Integer> {

    List<Puesto> findByEstado(EstadoPuesto estado);

    List<Puesto> findBySocio(Socio socio);

    @Query("SELECT p FROM Puesto p LEFT JOIN p.socio s LEFT JOIN s.usuario u WHERE " +
            "p.numeroPuesto LIKE %:termino% OR " +
            "p.ubicacion LIKE %:termino% OR " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Puesto> buscarPorTermino(@Param("termino") String termino);
}