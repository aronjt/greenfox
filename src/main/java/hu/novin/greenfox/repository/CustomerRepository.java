package hu.novin.greenfox.repository;

import hu.novin.greenfox.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c JOIN FETCH c.items WHERE c.id=:id")
    Customer findCustomerById(Long id);
}
