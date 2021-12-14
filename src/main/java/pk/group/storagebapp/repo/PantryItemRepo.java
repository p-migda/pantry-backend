package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.PantryItem;

@Repository
public interface PantryItemRepo extends JpaRepository<PantryItem, Long> {
}
