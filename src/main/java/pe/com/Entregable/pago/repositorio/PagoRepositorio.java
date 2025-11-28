package pe.com.Entregable.pago.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.Entregable.pago.modelo.Pago;

import java.util.List;

public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
    List<Pago> findBySocioUsuarioUsername(String username);

    @Query("SELECT p FROM Pago p JOIN p.socio s JOIN s.usuario u WHERE " +
            "CAST(p.id AS string) LIKE %:termino% OR " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "u.dni LIKE %:termino%")
    List<Pago> buscarPorTermino(@Param("termino") String termino);

}
