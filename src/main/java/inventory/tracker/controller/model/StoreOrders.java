package inventory.tracker.controller.model;

import java.math.BigDecimal;
import java.util.Date;

import inventory.tracker.entity.Customer;
import inventory.tracker.entity.Orders;
import inventory.tracker.entity.Product;
import inventory.tracker.entity.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreOrders {

	private Long orderId;
	private Long units;
	private BigDecimal dollarAmount;
	private Date orderDate;
	//private Customer customer;
	

	public StoreOrders (Orders orders) {
		orderId = orders.getOrderId();
		units = orders.getUnits();
		dollarAmount = orders.getDollarAmount();
		orderDate = orders.getOrderDate();
		//customer = orders.getCustomer();
	}
}
