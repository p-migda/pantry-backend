package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.ShoppingListItem;

@Repository
public interface ShoppingListItemRepo extends JpaRepository<ShoppingListItem,Long> {
}
