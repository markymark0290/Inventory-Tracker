package inventory.tracker.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import inventory.tracker.entity.Orders;

public interface OrderDao extends JpaRepository<Orders, Long> {

}
