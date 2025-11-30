package pe.com.Entregable.administrador.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.administrador.modelo.Administrador;

public interface AdministradorRepositorio extends JpaRepository<Administrador, Integer> {

}
