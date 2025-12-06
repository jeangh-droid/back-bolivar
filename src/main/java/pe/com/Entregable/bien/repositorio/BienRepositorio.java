package pe.com.Entregable.bien.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.Entregable.bien.modelo.Bien;

import java.util.List;
import java.util.Optional;

public interface BienRepositorio extends JpaRepository<Bien, Integer> {

    Optional<Bien> findByNombre(String nombre);

    @Query("SELECT b FROM Bien b WHERE " +
            "LOWER(b.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "LOWER(b.descripcion) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Bien> buscarPorTermino(@Param("termino") String termino);
}