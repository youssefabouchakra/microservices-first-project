package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.OrderRequestDto;

public interface OrderService {
	public void placeOrder(OrderRequestDto orderRequestDto);
}
