package com.vz.app.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<OrderEntity, Long>{
	OrderEntity findByproductIDAndEmail(Long productId, String email);
	
	
}