package pe.com.Entregable.multa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.multa.modelo.Multa;

import java.util.List;

public interface MultaRepositorio extends JpaRepository<Multa,Integer> {
    long countByEstado(EstadoMulta estado);
    List<Multa> findBySocioIdAndEstado(Integer idsocio, EstadoMulta estado);

    List<Multa> findBySocioUsuarioUsername(String username);

    @Query("SELECT m FROM Multa m JOIN m.socio s JOIN s.usuario u WHERE " +
            "LOWER(m.motivo) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Multa> buscarPorTermino(@Param("termino") String termino);
}
