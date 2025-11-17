package pe.com.Entregable.socio.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // ¡Importa el Encoder!
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.Entregable.enums.Estado;
import pe.com.Entregable.enums.Sanidad;
import pe.com.Entregable.login.modelo.Privilegio;
import pe.com.Entregable.login.modelo.Usuario;
import pe.com.Entregable.login.repositorio.PrivilegioRepositorio;
import pe.com.Entregable.login.repositorio.UsuarioRepositorio;
import pe.com.Entregable.socio.dto.SocioRequestDTO;
import pe.com.Entregable.socio.dto.SocioResponseDTO;
import pe.com.Entregable.socio.modelo.Socio;
import pe.com.Entregable.socio.repositorio.SocioRepositorio;
import pe.com.Entregable.util.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocioServicio implements ISocioService {

    private final SocioRepositorio socioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    // --- ¡DEPENDENCIAS INYECTADAS! ---
    private final PasswordEncoder passwordEncoder;
    private final PrivilegioRepositorio privilegioRepositorio;

    @Override
    @Transactional
    public List<SocioResponseDTO> listarSocios() {
        return socioRepositorio
                .findAll()
                .stream()
                .map(SocioResponseDTO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SocioResponseDTO obtenerSocioPorId(Integer id) {
        // (Nota: Tu interfaz usa 'idSocio', así que el DTO y el front deben usar 'id')
        // Voy a asumir que tu entidad Socio SÍ tiene 'idSocio'
        Socio socio = socioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
        return new SocioResponseDTO(socio);
    }

    @Override
    @Transactional
    public SocioResponseDTO crearSocio(SocioRequestDTO dto) {
        // 1. Validar que el DNI y Username no existan
        if (usuarioRepositorio.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("El username " + dto.getUsername() + " ya está registrado.");
        }
        if (usuarioRepositorio.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("El DNI " + dto.getDni() + " ya está registrado.");
        }

        Privilegio socioPrivilegio = privilegioRepositorio.findByNombre("SOCIO")
                .orElseThrow(() -> new ResourceNotFoundException("Privilegio", "nombre", "SOCIO"));

        // 2. Crear el nuevo Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(dto.getUsername());
        nuevoUsuario.setDni(dto.getDni());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setApellido(dto.getApellido());
        nuevoUsuario.setNumero(dto.getNumero());

        // --- ¡AQUÍ SE ENCRIPTA! ---
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        nuevoUsuario.setEstado(Estado.ACTIVO);
        nuevoUsuario.setPrivilegio(socioPrivilegio);

        Usuario usuarioGuardado = usuarioRepositorio.save(nuevoUsuario);

        // 3. Crear el nuevo Socio y enlazarlo
        Socio nuevoSocio = new Socio();
        nuevoSocio.setUsuario(usuarioGuardado);
        nuevoSocio.setDireccion(dto.getDireccion());
        nuevoSocio.setTarjetaSocio(dto.getTarjetaSocio());
        nuevoSocio.setCarnetSanidad(Sanidad.valueOf(dto.getCarnetSanidad().toUpperCase()));

        Socio socioGuardado = socioRepositorio.save(nuevoSocio);
        return new SocioResponseDTO(socioGuardado);
    }

    @Override
    @Transactional
    public SocioResponseDTO actualizarSocio(Integer idSocio, SocioRequestDTO dto) {
        Socio socio = socioRepositorio.findById(idSocio)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", idSocio));

        Usuario usuario = socio.getUsuario();

        // Validar DNI y Username
        if (!usuario.getDni().equals(dto.getDni())) {
            if (usuarioRepositorio.findByDni(dto.getDni()).filter(u -> !u.getId().equals(usuario.getId())).isPresent()) {
                throw new RuntimeException("El DNI " + dto.getDni() + " ya pertenece a otro usuario.");
            }
            usuario.setDni(dto.getDni());
        }
        if (!usuario.getUsername().equals(dto.getUsername())) {
            if (usuarioRepositorio.findByUsername(dto.getUsername()).filter(u -> !u.getId().equals(usuario.getId())).isPresent()) {
                throw new RuntimeException("El Username " + dto.getUsername() + " ya pertenece a otro usuario.");
            }
            usuario.setUsername(dto.getUsername());
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setNumero(dto.getNumero());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        usuarioRepositorio.save(usuario);

        socio.setDireccion(dto.getDireccion());
        socio.setTarjetaSocio(dto.getTarjetaSocio());
        socio.setCarnetSanidad(Sanidad.valueOf(dto.getCarnetSanidad().toUpperCase()));

        Socio socioActualizado = socioRepositorio.save(socio);
        return new SocioResponseDTO(socioActualizado);
    }

    @Override
    @Transactional
    public void eliminarSocio(Integer id) {
        Socio socio = socioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));

        Usuario usuarioAsociado = socio.getUsuario();
        socioRepositorio.delete(socio);
        usuarioRepositorio.delete(usuarioAsociado);
    }
}