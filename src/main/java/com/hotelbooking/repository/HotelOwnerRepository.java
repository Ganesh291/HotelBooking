package com.hotelbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbooking.entity.HotelOwnerEntity;

@Repository
@Transactional
public interface HotelOwnerRepository extends JpaRepository<HotelOwnerEntity, Integer>{
	 
	public List<HotelOwnerEntity> findByStatus(String status);
	
	public HotelOwnerEntity findByRoomNo(String roomNo);
	
	public List<HotelOwnerEntity> findByAirConditionerAndWifiAndDoubleCot(String airConditioner,String wifi,String DoubleCot);
	
	@Modifying
	@Query(value = "update hotelowner set status=:status, customer_id=:customerId where id=:id", nativeQuery=true)
	public void updateRoomStatusAndCustId(@Param("status") String status, @Param("customerId") Integer customerId, @Param("id") Integer id);
	
	
	

}
