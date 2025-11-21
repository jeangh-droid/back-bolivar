package pe.com.Entregable.pago.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.Entregable.pago.modelo.Pago;

public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
}
