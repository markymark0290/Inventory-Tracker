package inventory.tracker.controller.model;

import java.util.HashSet;
import java.util.Set;

import inventory.tracker.entity.Customer;
import inventory.tracker.entity.Orders;
import inventory.tracker.entity.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
public class StoreData {
	
	private Long storeId;
	private String storeName;
	private String storeLocation;
	private String storePhone;
	
	private Set<StoreCustomer> customers = new HashSet<>();
	private Set<StoreOrders> orders = new HashSet<>();
	
	public StoreData (Store store) {
		storeId = store.getStoreId();
		storeName = store.getStoreName();
		storeLocation = store.getStoreLocation();
		storePhone = store.getStorePhone();
		
		
		 for(Customer customer : store.getCustomers()) {
		 customers.add(new StoreCustomer(customer)); }
		 
		for(Orders order : store.getOrders()) {
			orders.add(new StoreOrders(order));
		}
	}

}
