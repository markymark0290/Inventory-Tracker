package inventory.tracker.controller.model;

import java.math.BigDecimal;
import java.util.Date;

import inventory.tracker.entity.Orders;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderData {


	private Long orderId;
	private Long units;
	private BigDecimal dollarAmount;
	private Date orderDate;
	private Long customerId;
	private Long productId;
	private Long storeId;
	

	public OrderData (Orders orders) {
		orderId = orders.getOrderId();
		units = orders.getUnits();
		dollarAmount = orders.getDollarAmount();
		orderDate = orders.getOrderDate();
		customerId = orders.getCustomer().getCustomerId();
		productId = orders.getProduct().getProductId();
		storeId = orders.getStore().getStoreId();
	}
}
