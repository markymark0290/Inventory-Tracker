package inventory.tracker.controller.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import inventory.tracker.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreProduct {
	private Long productId;
	private String productName;
	private Long totalUnits;
	private BigDecimal cost; 
	private Set<StoreOrders> orders = new HashSet<>();
	
	public StoreProduct(Product product) {
		productId = product.getProductId();
		productName = product.getProductName();
		totalUnits = product.getTotalUnits();
		cost = product.getCost();
	}
}
