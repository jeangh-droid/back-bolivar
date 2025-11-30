package pe.com.Entregable.multa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.enums.EstadoMulta;
import pe.com.Entregable.multa.modelo.Multa;

import java.util.List;

public interface MultaRepositorio extends JpaRepository<Multa,Integer> {
    long countByEstado(EstadoMulta estado);
    List<Multa> findBySocioIdAndEstado(Integer idsocio, EstadoMulta estado);

    List<Multa> findBySocioUsuarioUsername(String username);

}
