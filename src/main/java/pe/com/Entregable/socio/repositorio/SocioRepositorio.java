package pe.com.Entregable.socio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import pe.com.Entregable.socio.modelo.Socio;

import java.util.List;

public interface SocioRepositorio extends JpaRepository<Socio,Integer> {
}
