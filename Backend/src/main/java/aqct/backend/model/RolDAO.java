package aqct.backend.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author matia
 */
public interface RolDAO extends CrudRepository<Rol, Long> {
    
    public Rol findByNombre(String nombre);
    public List<Rol> findAll();
    
}
