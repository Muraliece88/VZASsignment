package com.vz.app.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vz.app.user.UserNotFoundException;

public interface OrderService {

	OrderResponse createOrder(OrderDAO orderDao) throws UserNotFoundException, OrderExistsException;

	Page<OrderAllDTO> getAllOrders(Pageable pageable);

}
