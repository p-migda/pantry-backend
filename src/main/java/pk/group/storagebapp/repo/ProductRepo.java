package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
