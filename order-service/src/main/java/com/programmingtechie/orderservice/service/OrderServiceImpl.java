package com.programmingtechie.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtechie.orderservice.dto.OrderLineItemDto;
import com.programmingtechie.orderservice.dto.OrderRequestDto;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void placeOrder(OrderRequestDto orderRequestDto) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItemsList = orderRequestDto.getOrderLineItemDtoList().stream()
		.map(this::mapDtoToModel)
		.toList();
		
		order.setOrderLineItemsList(orderLineItemsList);
		orderRepository.save(order);
	}
	
	private OrderLineItems mapDtoToModel(OrderLineItemDto orderLineItemDto) {
		OrderLineItems orderLineItems = OrderLineItems.builder()
				.price(orderLineItemDto.getPrice())
				.quantity(orderLineItemDto.getQuantity())
				.skuCode(orderLineItemDto.getSkuCode())
				.build();
		return orderLineItems;
	}
}
