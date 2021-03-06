package aqct.backend.controller;

import aqct.backend.model.Rol;
import aqct.backend.repository.RolDAO;
import aqct.backend.model.Usuario;
import aqct.backend.repository.UsuarioDAO;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Batman
 */
@RestController
@RequestMapping("usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    private RolDAO rolDAO;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Retorna la lista de todos los usuarios registrados en el sistema
     *
     * @return Lista de Usuario
     */
    @GetMapping
    @Secured("IS_AUTHENTICATED_FULLY")
    public List<Usuario> index() {

        return this.usuarioDAO.findAll();

    }

    /**
     * Pemite guardar un usuario dentro de la base de datos
     *
     * @param usuario Un Usuario que se desea guardar (debe ser enviado en
     * formato JSON)
     * @return Numero id asignado al usuario registrado
     */
    @PostMapping
    @PreAuthorize("true")
    public Long store(@RequestBody Usuario usuario) {
        // Rol para usuarios comunes
        Rol rol = this.rolDAO.findById((long) 1).get();

        // Encriptar contraseña de usuario
        usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));

        usuario.setRol(rol);
        //Instante en que se guarda el usuario en la base de datos
        usuario.setCreatedAt(Timestamp.from(Instant.now()));

        this.usuarioDAO.save(usuario);

        return usuario.getId();
        
    }
    
    @PutMapping
    @Secured("IS_AUTHENTICATED_FULLY")
    public void update(@RequestBody Usuario usuario) {
        
        this.usuarioDAO.save(usuario);
        
    }

    /**
     * Busca y obtiene el Usuario asosiado a un id
     *
     * @param id Id del usuario
     * @return El objeto usuario como un JSON
     */
    @GetMapping("{id}")
    @Secured("IS_AUTHENTICATED_FULLY")
    public Usuario show(@PathVariable(value = "id") long id) {
        return this.usuarioDAO.findById(id).get();
    }

    /**
     * Eliminar a un Usuario por su Id
     *
     * @param id Id del usuario que se desea eliminar
     */
    @DeleteMapping("{id}")
    @Secured("IS_AUTHENTICATED_FULLY")
    public void destroy(@PathVariable("id") long id) {

        this.usuarioDAO.deleteById(id);

    }

}
