package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.ProductOrder;
import pk.group.storagebapp.keys.ProductOrderKey;

@Repository
public interface ProductOrderRepo extends JpaRepository<ProductOrder, ProductOrderKey> {
}
