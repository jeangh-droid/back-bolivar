package pe.com.Entregable.socio.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.enums.Estado;
import pe.com.Entregable.enums.EstadoPuesto;
import pe.com.Entregable.enums.Sanidad;
import pe.com.Entregable.enums.TipoDocumento;
import pe.com.Entregable.login.modelo.Privilegio;
import pe.com.Entregable.login.modelo.Usuario;
import pe.com.Entregable.login.repositorio.PrivilegioRepositorio;
import pe.com.Entregable.login.repositorio.UsuarioRepositorio;
import pe.com.Entregable.puesto.modelo.Puesto;
import pe.com.Entregable.puesto.repositorio.PuestoRepositorio;
import pe.com.Entregable.socio.dto.SocioRequestDTO;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import pe.com.Entregable.socio.modelo.Socio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocioServicio implements ISocioService {

    private final SocioRepositorio socioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final PrivilegioRepositorio privilegioRepositorio;

    private final PuestoRepositorio puestoRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<SocioResponseDTO> listarSocios() {
        return socioRepositorio.findAll().stream().map(socio -> {
            SocioResponseDTO dto = new SocioResponseDTO(socio);
            Optional<Puesto> puesto = puestoRepositorio.findBySocio(socio);
            puesto.ifPresent(p -> dto.setNumeroPuesto(p.getNumeroPuesto()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SocioResponseDTO obtenerSocioPorId(Integer idSocio) {
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));
        return new SocioResponseDTO(socio);
    }

    @Override
    @Transactional
    public SocioResponseDTO crearSocio(SocioRequestDTO dto) {
        if (usuarioRepositorio.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya existe.");
        }
        if (usuarioRepositorio.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("El  ya existe.");
        }

        Privilegio socioPrivilegio = privilegioRepositorio.findByNombre("SOCIO")
                .orElseThrow(() -> new ResourceNotFoundException("Privilegio", "nombre", "SOCIO"));

        // 2. Crear Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(dto.getUsername());
        nuevoUsuario.setTipoDocumento(TipoDocumento.valueOf(dto.getTipoDocumento()));
        nuevoUsuario.setDni(dto.getDni());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setApellido(dto.getApellido());
        nuevoUsuario.setNumero(dto.getNumero());
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        nuevoUsuario.setEstado(Estado.ACTIVO);
        nuevoUsuario.setPrivilegio(socioPrivilegio);
        Usuario usuarioGuardado = usuarioRepositorio.save(nuevoUsuario);

        // 3. Crear Socio
        Socio nuevoSocio = new Socio();
        nuevoSocio.setUsuario(usuarioGuardado);
        nuevoSocio.setDireccion(dto.getDireccion());
        nuevoSocio.setTarjetaSocio(dto.getTarjetaSocio());
        nuevoSocio.setCarnetSanidad(Sanidad.valueOf(dto.getCarnetSanidad().toUpperCase()));
        Socio socioGuardado = socioRepositorio.save(nuevoSocio);

        if (dto.getIdPuesto() != null && dto.getIdPuesto() > 0) {
            Puesto puesto = puestoRepositorio.findById(dto.getIdPuesto())
                    .orElseThrow(() -> new ResourceNotFoundException("Puesto", "id", dto.getIdPuesto()));

            if (puesto.getEstado() == EstadoPuesto.OPERATIVO || puesto.getSocio() != null) {
                throw new RuntimeException("El puesto seleccionado ya está ocupado.");
            }

            puesto.setSocio(socioGuardado);
            puesto.setEstado(EstadoPuesto.OPERATIVO);
            puestoRepositorio.save(puesto);
        }

        return new SocioResponseDTO(socioGuardado);
    }

    @Override
    @Transactional
    public SocioResponseDTO actualizarSocio(Integer idSocio, SocioRequestDTO dto) {
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));

        Usuario usuario = socio.getUsuario();
        usuario.setNombre(dto.getNombre());
        usuario.setTipoDocumento(TipoDocumento.valueOf(dto.getTipoDocumento()));
        usuarioRepositorio.save(usuario);

        socio.setDireccion(dto.getDireccion());
        Socio socioActualizado = socioRepositorio.save(socio);


        return new SocioResponseDTO(socioActualizado);
    }

    @Override
    @Transactional
    public void eliminarSocio(Integer idSocio) {
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));

        Usuario usuario = socio.getUsuario();

        if (usuario.getEstado() == Estado.ACTIVO) {
            usuario.setEstado(Estado.INACTIVO);

            Optional<Puesto> puestoAsignado = puestoRepositorio.findBySocio(socio);
            if (puestoAsignado.isPresent()) {
                Puesto puesto = puestoAsignado.get();
                puesto.setSocio(null);
                puesto.setEstado(EstadoPuesto.INACTIVO);
                puestoRepositorio.save(puesto);
            }

        } else {
            usuario.setEstado(Estado.ACTIVO);
        }

        usuarioRepositorio.save(usuario);
    }

    @Override
    public SocioResponseDTO obtenerPerfilSocio(String username) {
        return new SocioResponseDTO(socioRepositorio.findByUsuarioUsername(username).orElseThrow());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SocioResponseDTO> buscarSocios(String termino) {
        return socioRepositorio.buscarPorTermino(termino)
                .stream()
                .map(SocioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarioRepositorio.existsByUsername(username);
    }

    @Override
    public List<SocioResponseDTO> listarSociosActivos() {
        return socioRepositorio.findByUsuarioEstado(Estado.ACTIVO)
                .stream()
                .map(SocioResponseDTO::new)
                .collect(Collectors.toList());
    }

}