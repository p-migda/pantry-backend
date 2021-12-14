package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.ShoppingList;

@Repository
public interface ShoppingListRepo extends JpaRepository<ShoppingList,Long> {
}
