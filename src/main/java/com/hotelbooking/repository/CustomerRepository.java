package com.hotelbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbooking.entity.CustomerEntity;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

	public CustomerEntity findByCustomerId(Integer customerId);
	
	public List<CustomerEntity> findByCurrentStatus(String currentStatus);
	
	public Optional<CustomerEntity> findByCustomerNameAndAgeAndCityAndState(String customerName,Integer age,String city,String state);
	
	@Modifying
	@Query(value = "update customer set current_status=:currentStatus, room_no=:roomNo where customer_id=:customerId", nativeQuery = true)
	public void updateCustomerStatusAndRoomId(@Param("currentStatus") String currentStatus ,@Param("roomNo") String roomNo, @Param("customerId") Integer customerId);
}
