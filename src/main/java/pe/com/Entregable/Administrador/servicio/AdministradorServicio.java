package pe.com.Entregable.Administrador.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.Entregable.Administrador.modelo.Administrador;
import pe.com.Entregable.Administrador.repositorio.AdministradorRepositorio;

import java.util.List;

@Service
public class AdministradorServicio {
    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    public List<Administrador> listarAdministrador(){
        return administradorRepositorio.findAll();
    }

    public Administrador buscarAdministrador(Integer idAdmi) {
        return administradorRepositorio.findById(idAdmi).orElse(null);
    }
}
