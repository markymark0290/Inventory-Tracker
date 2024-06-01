package inventory.tracker.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inventory.tracker.Dao.CustomerDao;
import inventory.tracker.Dao.OrderDao;
import inventory.tracker.Dao.ProductDao;
import inventory.tracker.Dao.StoreDao;
import inventory.tracker.controller.model.OrderData;
import inventory.tracker.controller.model.StoreCustomer;
import inventory.tracker.controller.model.StoreData;
import inventory.tracker.controller.model.StoreOrders;
import inventory.tracker.controller.model.StoreProduct;
import inventory.tracker.entity.Customer;
import inventory.tracker.entity.Orders;
import inventory.tracker.entity.Product;
import inventory.tracker.entity.Store;

@Service //-->makes this a managed bean (spring managed)
public class InvTrackService {
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CustomerDao customerDao;
	
	
//****Save a new Store to the DB****	
	@Transactional(readOnly = false)
	public StoreData saveStore(StoreData storeData) { 
			
		Long storeId = storeData.getStoreId();
		Store store = findOrCreateStore(storeId);
		setFieldsInStore(store, storeData);
		
		return new StoreData(storeDao.save(store));
	}

	private void setFieldsInStore(Store store, StoreData storeData) {
		//order.setProduct(storeOrders.getProduct());
		store.setStoreLocation(storeData.getStoreLocation());
		store.setStoreName(storeData.getStoreName());
		store.setStorePhone(storeData.getStorePhone());
	}

	private Store findOrCreateStore(Long storeId) {
		Store store;
		
		if(Objects.isNull(storeId)) {
			store = new Store();
		}
		else {
			store = findStoreById(storeId);
		}
		
		return store;
	}

	private Store findStoreById(Long storeId) {
				
		return storeDao.findById(storeId).orElseThrow(() -> new NoSuchElementException(
				"Store with ID=" + storeId + " was not found."));
	}

//****Save a new Order to the DB****
	@Transactional(readOnly = false)
	public StoreOrders saveOrders(Long storeId, Long customerId, Long productId, StoreOrders storeOrders) {
		
		Long orderId = storeOrders.getOrderId();
		Orders order = findOrCreateOrder(orderId);
		
		
		Store store = findStoreById(storeId);
		Customer customer = findCustomerById(customerId, storeId);
		Product product = findProductById(productId);		
		order.setStore(store);
		order.setCustomer(customer);
		order.setProduct(product);
		
		
		//Checks to see if there are enough units to sell 
		if (storeOrders.getUnits() < product.getTotalUnits()) {
			product.setTotalUnits(product.getTotalUnits() - storeOrders.getUnits());
		}else {
			throw new IllegalArgumentException(
					"You don't have enough units of " + product.getProductName()
					+ " for your order");
		}
		
		//sets the fields in te
		setFieldsInOrders(order, storeOrders);
		Set<Orders> orders = new HashSet<>(); 
		orders.add(order);
		product.setOrders(orders);
		store.setOrders(orders);
		customer.setOrders(orders);
		
		//checks for whether customer has shopped at the store before then adds
		//the customer to the store and the store to the customer if not.
		boolean boo = false;
		for (Customer cust : store.getCustomers()) {
			if(cust.getCustomerId() == customer.getCustomerId()) {
				boo = true;
			}
		}
		
		if (!boo) {
			customer.getStores().add(store);
			store.getCustomers().add(customer);
		}
						
		return new StoreOrders(orderDao.save(order)); //returns a orders object so 
	}

	private void setFieldsInOrders(Orders order, StoreOrders storeOrders) {
		
		order.setDollarAmount(storeOrders.getDollarAmount());
		order.setUnits(storeOrders.getUnits());
		order.setOrderDate(storeOrders.getOrderDate());
	}

	private Orders findOrCreateOrder(Long orderId) {
		Orders order;
		
		if(Objects.isNull(orderId)) {
			order = new Orders();
		}
		else {
			order = findOrderById(orderId);
		}
		
		return order;
	}

	private Orders findOrderById(Long orderId) {
		return orderDao.findById(orderId).orElseThrow(() -> new NoSuchElementException(
				"Order with ID=" + orderId + " was not found."));
	}

//****Save a new Product to the DB****
	@Transactional(readOnly = false)
	public StoreProduct saveProduct(StoreProduct storeProduct) {
		Long productId = storeProduct.getProductId();
		Product product = findOrCreateProduct(productId);
		
		setFieldsInProduct(product, storeProduct);
		return new StoreProduct(productDao.save(product));
	}

	private void setFieldsInProduct(Product product, StoreProduct storeProduct) {
		
		product.setCost(storeProduct.getCost());
		product.setTotalUnits(storeProduct.getTotalUnits());
		product.setProductName(storeProduct.getProductName());
	}

	private Product findOrCreateProduct(Long productId) {
		Product product;
		
		if(Objects.isNull(productId)) {
			product = new Product();
		}
		else {
			product = findProductById(productId);
		}
		
		return product;
	}

	private Product findProductById(Long productId) {
				
		return productDao.findById(productId).orElseThrow(() -> new NoSuchElementException(
				"Store with ID=" + productId + " was not found."));
	}
//****Save a new Customer to the DB****
	@Transactional(readOnly = false)
	public StoreCustomer saveCustomer(Long storeId, StoreCustomer storeCustomer) {
	
		//Set<Store> stores = new HashSet<Store>();
		Store store = findStoreById(storeId);
		//stores.add(store);
		
		Long customerId = storeCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, storeId);
		
		setFieldsInCustomer(customer, storeCustomer);
		//customer.setStores(stores);
		/*
		 * Set<Customer> customers = new HashSet<Customer>(); customers.add(customer);
		 * store.setCustomers(customers);
		 */
		
		customer.getStores().add(store);
		store.getCustomers().add(customer);
		
		return new StoreCustomer(customerDao.save(customer));
	}

	private void setFieldsInCustomer(Customer customer, StoreCustomer storeCustomer) {
		
		customer.setCustomerFirstName(storeCustomer.getCustomerFirstName());
		customer.setCustomerLastName(storeCustomer.getCustomerLastName());
		customer.setCustomerPhone(storeCustomer.getCustomerPhone());
		customer.setCustomerEmail(storeCustomer.getCustomerEmail());
	}

	private Customer findCustomerById(Long customerId, Long storeId) {
		
		Store store = findStoreById(storeId);
		
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" 
						+ customerId + " was not found"));
		
		
		boolean boo = false;
		for (Store st : customer.getStores()) {
			if (st.getStoreId() == storeId) {
				boo = true;
			}
		}
		if(boo) {
			return customer;
		}else {
			
					customer.getStores().add(store);
					store.getCustomers().add(customer);
			return customer;
		}
	}
		
	private Customer findOrCreateCustomer(Long customerId, Long storeId) {
		Customer customer;
			
		if(Objects.isNull(customerId)){
			customer = new Customer();
		}else {
		customer = findCustomerById(customerId, storeId);
		}
		return customer;

	}
	@Transactional(readOnly = true)
	public StoreOrders retrieveOrders(Long orderId) {
		Orders order = findOrderById(orderId);
		/*
		 * Customer customer = findCustomerById(order.getCustomer().getCustomerId(),
		 * order.getStore().getStoreId()); Store store =
		 * findStoreById(order.getStore().getStoreId()); Product product =
		 * findProductById(order.getProduct().getProductId());
		 * 
		 * String customerName = customer.getCustomerFirstName().toString() + " " +
		 * customer.getCustomerLastName().toString(); String storeName =
		 * store.getStoreName().toString(); String productName =
		 * product.getProductName().toString(); order.setCustomer(null);
		 * order.setProduct(null); order.setStore(null); customer = null; store = null;
		 * product = null;
		 */
		
		return new StoreOrders(order);
	}
	@Transactional(readOnly = false)
	public void deleteOrdersById(Long orderId) {
		Orders order = findOrderById(orderId);
		orderDao.delete(order);
	}

	public OrderData updateOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
