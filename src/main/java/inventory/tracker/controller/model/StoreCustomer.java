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
public class StoreCustomer {

	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerPhone;
	private String customerEmail;
	private Set<StoreOrders> orders = new HashSet<>();
	//private Set<Long> stores = new HashSet<>();

	public StoreCustomer(Customer customer) {
		
		 customerId = customer.getCustomerId(); 
		 customerFirstName = customer.getCustomerFirstName(); 
		 customerLastName = customer.getCustomerLastName();
		 customerPhone = customer.getCustomerPhone();
		 customerEmail = customer.getCustomerEmail();
		 
		 for(Orders order : customer.getOrders()) { 
			 orders.add(new StoreOrders(order)); 
		}
		 
			
		/*
		 for(Store store : customer.getStores()) { 
			 stores.add(store.getStoreId()); }*/
	
	}
}
