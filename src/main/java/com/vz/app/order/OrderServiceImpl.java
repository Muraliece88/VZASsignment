package com.vz.app.order;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.vz.app.user.Root;
import com.vz.app.user.UserDetails;
import com.vz.app.user.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${api.host.baseurl}")
	private String apiHost;

	private final UserRepo userRepo;

	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public OrderResponse createOrder(OrderDAO orderDao) throws UserNotFoundException, OrderExistsException {

		log.debug("Save order info{}" + orderDao.getClass());
		UserDetails user = checkUserExists(orderDao.getEmail());

		if (user == null) {
			log.debug("Exception when fetching user details for users {}" + orderDao.getEmail());
			throw new UserNotFoundException(Constants.USER_NOT_FOUND + orderDao.getEmail());
		}

		if (checkOrderExists(orderDao)) {
			log.debug("Product exists for different Order {}" + orderDao.getClass());
			throw new OrderExistsException(Constants.ORDER_EXISTS + orderDao.getProductId());
		}
		OrderDetailsDAO orderDetails = new OrderDetailsDAO();
		orderDetails.setProductId(orderDao.getProductId());
		orderDetails.setEmail(orderDao.getEmail());
		orderDetails.setFirst_name(user.getFirst_name());
		orderDetails.setLast_name(user.getLast_name());

		OrderEntity order = userRepo.save(modelMapper.map(orderDetails, OrderEntity.class));

		return modelMapper.map(order, OrderResponse.class);
	}

	public UserDetails checkUserExists(String email) throws UserNotFoundException {
		UserDetails user = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<Root> responseEntity = restTemplate.exchange(apiHost, HttpMethod.GET, entity, Root.class);
		Root rootdata = responseEntity.getBody();
		for (UserDetails userList : rootdata.data) {
			if ((userList.getEmail()).equalsIgnoreCase(email)) {
				user = userList;
			}
		}
		return user;
	}

	public boolean checkOrderExists(OrderDAO orderDao) {
		boolean found = false;
		if (userRepo.findByproductIDAndEmail(orderDao.getProductId(), orderDao.getEmail()) != null) {
			found = true;
		}
		return found;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrderAllDTO> getAllOrders(Pageable pageable) {
		log.debug("Retrieve the Orders");
		return userRepo.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
				.map(order -> modelMapper.map(order, OrderAllDTO.class));

	}

}
