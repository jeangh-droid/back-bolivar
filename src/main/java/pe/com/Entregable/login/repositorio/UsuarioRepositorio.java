package pe.com.Entregable.login.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.login.modelo.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByDni(String dni);

    boolean existsByUsername(String username);
}
