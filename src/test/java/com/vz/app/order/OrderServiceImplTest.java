package com.vz.app.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.vz.app.user.Root;
import com.vz.app.user.UserDetails;
import com.vz.app.user.UserNotFoundException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Transactional
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
	@Mock
	RestTemplate restTemplate;
	@Mock
	private UserRepo userRepo;
	@Mock
	private OrderEntity orderEntity;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private UserDetails mockuserDetails;
	@InjectMocks
	private OrderServiceImpl orderImpl;
	@Mock
	private OrderDetailsDAO orderDetailsDao;
	@Mock
	private Root root;
	
	@Autowired
	private UserRepo userRepoUser;

	private static List<OrderEntity> orderentity;

	private ArrayList<UserDetails> mockArrayList = new ArrayList<UserDetails>();;
	ResponseEntity responseEntity = mock(ResponseEntity.class);

	@BeforeEach
	void setUp() {
		modelMapper = new ModelMapper();
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(orderImpl, "apiHost", "URL");

	}

	@SuppressWarnings("unchecked")
	@Test
	void createOrder() throws UserNotFoundException, OrderExistsException {
		OrderDAO orderDao = new OrderDAO();
		orderDao.setProductId(1L);
		orderDao.setEmail("someemail");
		UserDetails userDetails = new UserDetails(1L, "someemail", "first_name", "last_name", "avatar");
		doReturn(responseEntity).when(restTemplate).exchange(any(String.class), any(HttpMethod.class),
				any(HttpEntity.class), any(Class.class));
		when(responseEntity.getBody()).thenReturn(root);
		mockArrayList.add(userDetails);
		root.data = mockArrayList;
		orderImpl.createOrder(orderDao);
		assertThat(orderEntity.getOrderID()).isEqualTo(0L);

	}

	@SuppressWarnings("unchecked")

	@Test
	void createOrderwithUserException() {
		OrderDAO orderDao = new OrderDAO();
		orderDao.setProductId(1L);
		orderDao.setEmail("othermail");
		UserDetails userDetails = new UserDetails(1L, "someemail", "first_name", "last_name", "avatar");
		doReturn(responseEntity).when(restTemplate).exchange(any(String.class), any(HttpMethod.class),
				any(HttpEntity.class), any(Class.class));
		when(responseEntity.getBody()).thenReturn(root);
		mockArrayList.add(userDetails);
		root.data = mockArrayList;
		Throwable exception = assertThrows(UserNotFoundException.class, () -> {
			orderImpl.createOrder(orderDao);
		});
		assertEquals("user not found with the emailothermail", exception.getMessage());
	}

	
}
