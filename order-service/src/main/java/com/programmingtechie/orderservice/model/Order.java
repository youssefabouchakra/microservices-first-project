package com.programmingtechie.orderservice.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String orderNumber;
	/**The meaning of CascadeType.ALL is that the persistence will propagate (cascade) all EntityManager 
	 * operations (PERSIST, REMOVE, REFRESH, MERGE, DETACH) to the relating entities.
	 * CascadeType.ALL specifies that when a Order is created, 
	 * if there is any orderLineItemsList association, then related OrderLineItems will be created as well.
	 * (CascadeType. PERSIST).
	 * If the Order is deleted from persistence storage, the related OrderLineItems will be deleted*/
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineItems> orderLineItemsList;
	
}
