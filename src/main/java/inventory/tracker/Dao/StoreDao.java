package inventory.tracker.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import inventory.tracker.entity.Store;

public interface StoreDao extends JpaRepository<Store, Long> {

}
