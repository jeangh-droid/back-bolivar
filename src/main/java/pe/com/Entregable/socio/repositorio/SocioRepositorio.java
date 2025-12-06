package pe.com.Entregable.socio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.Entregable.enums.Estado;
import pe.com.Entregable.socio.modelo.Socio;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

public interface SocioRepositorio extends JpaRepository<Socio,Integer> {

    Optional<Socio> findByUsuarioUsername(String username);

    @Query("SELECT s FROM Socio s JOIN s.usuario u WHERE " +
            "u.dni LIKE %:termino% OR " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Socio> buscarPorTermino(@Param("termino") String termino);

    List<Socio> findByUsuarioEstado(Estado estado);

    @Query("SELECT s FROM Socio s " +
            "JOIN s.usuario u " +
            "WHERE u.estado = 'ACTIVO' " +
            "AND s.id NOT IN (SELECT p.socio.id FROM Puesto p WHERE p.socio IS NOT NULL)")
    List<Socio> findSociosActivosSinPuesto();
}
