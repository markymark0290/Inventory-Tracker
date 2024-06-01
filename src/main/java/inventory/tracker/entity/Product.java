package inventory.tracker.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Product {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long productId;
		private String productName;
		private Long totalUnits;
		private BigDecimal cost; 
		
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		@OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
		private Set<Orders> orders = new HashSet<>();
}
