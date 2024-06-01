package inventory.tracker.Dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import inventory.tracker.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
	
	//Set<Customer> findAllByCustomerIn(Set<Long> customerIds);

}
