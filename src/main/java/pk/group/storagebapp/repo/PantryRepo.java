package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.Pantry;

import java.util.Optional;

@Repository
public interface PantryRepo extends JpaRepository<Pantry, Long> {

    Pantry findByUserId(Long id);
}
