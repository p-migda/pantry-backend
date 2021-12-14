package pk.group.storagebapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.group.storagebapp.entities.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
