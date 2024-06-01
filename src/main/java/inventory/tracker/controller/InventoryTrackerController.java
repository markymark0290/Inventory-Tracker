package inventory.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inventory.tracker.controller.model.OrderData;
import inventory.tracker.controller.model.StoreCustomer;
import inventory.tracker.controller.model.StoreData;
import inventory.tracker.controller.model.StoreOrders;
import inventory.tracker.controller.model.StoreProduct;
import inventory.tracker.service.InvTrackService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/inventory_tracker")
@Slf4j
public class InventoryTrackerController {
	
	@Autowired //-->spring injects bean from registry
	private InvTrackService invTrackService;
	
	@PostMapping("/store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreData insertStore(@RequestBody StoreData storeData) {
		
		log.info("Creating store {}", storeData);
		return invTrackService.saveStore(storeData);
	}

	@PostMapping("store/{storeId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreCustomer insertCustomer(@PathVariable Long storeId,
			@RequestBody StoreCustomer storeCustomer) {
		
		log.info("Creating customer {} for storeId {}", storeCustomer.getCustomerFirstName(), storeId);
		return invTrackService.saveCustomer(storeId,storeCustomer);
	}
	
	@PostMapping("/product")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreProduct insertStore(@RequestBody StoreProduct storeProduct) {
		
		log.info("Creating product {}", storeProduct);
		return invTrackService.saveProduct(storeProduct);
	}
	
	@PostMapping("store/{storeId}/customer/{customerId}/product/{productId}/order")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreOrders insertOrders(@PathVariable Long storeId,
			@PathVariable Long customerId,
			@PathVariable Long productId,
			@RequestBody StoreOrders storeOrders) {
		
		log.info("Creating order {}", storeOrders);
		return invTrackService.saveOrders(storeId, customerId, productId, storeOrders);
	}
	
	@PutMapping("store/{storeId}/customer/{customerId}/product/{productId}/order/{orderId}")
	public StoreOrders updateOrderById(@PathVariable Long storeId,
			@PathVariable Long customerId,
			@PathVariable Long productId,
			@PathVariable Long orderId,
			@RequestBody StoreOrders storeOrders) {
		
		storeOrders.setOrderId(orderId);
		
		log.info("Updating order with ID={}", orderId);
		
		return invTrackService.saveOrders(storeId, customerId, productId, storeOrders);
	}
	
	@GetMapping("/order/{orderId}")
	public StoreOrders retrieveOrders(@PathVariable Long orderId) {
		
		log.info("Retrieving order ID={}", orderId);
		return invTrackService.retrieveOrders(orderId);
	}
	
	@DeleteMapping("/order/{orderId}")
	public String deleteOrderById(@PathVariable Long orderId) {
		
		log.info("Deleting order ID={}", orderId);
		invTrackService.deleteOrdersById(orderId);
		return new String("Deletion of order ID=" + orderId + " was successful!");
	}

	
	
}
