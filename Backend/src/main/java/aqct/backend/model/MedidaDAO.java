package aqct.backend.model;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MedidaDAO extends CrudRepository<Medida, Long> {
    
    @Override
    public List<Medida> findAll();
    
    public List<Medida> findByCreatedAtBetween(Timestamp from, Timestamp to);
    
}
