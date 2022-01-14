package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.ProductShoppingList;
import pk.group.storagebapp.keys.ProductShoppingListKey;

@Repository
public interface ProductShoppingListRepo extends JpaRepository<ProductShoppingList, ProductShoppingListKey> {
}
