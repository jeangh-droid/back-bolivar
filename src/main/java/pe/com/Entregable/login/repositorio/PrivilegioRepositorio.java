package pe.com.Entregable.login.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.login.modelo.Privilegio;

import java.util.Optional;

public interface PrivilegioRepositorio extends JpaRepository<Privilegio,Integer> {

    Optional<Privilegio> findByNombre(String nombre);
}
