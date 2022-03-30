package com.vz.app.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vz.app.user.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/order", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderDAO orderDto)
			throws UserNotFoundException, OrderExistsException {
		log.debug("Place order for the product: {}", orderDto.getProductId());

		OrderResponse res = orderService.createOrder(orderDto);
		return ResponseEntity.ok(res);

	}

	@GetMapping(value="/orders")
	public ResponseEntity<Page<OrderAllDTO>> getOrders(Pageable pageable) {
		log.debug("Get all orders: {}");
		return ResponseEntity.ok(orderService.getAllOrders(pageable));

	}

}
