package com.kmj.apiProject.driver.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.dao.BaseDao;
import com.kmj.apiProject.order.dto.DeliveryDto;

@Repository
public class DriverDao extends BaseDao {

	private static final String NAMESPACE = "com.kmj.apiProject.driver.dao.mapper.DriverMapper.";

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	// 기사 상태 변경
	public int status(DriverDto driverDto) {

		return insert("status", driverDto);
	}

	// 보류
	public int updateDriverLocation(DriverDto driverDto) {

		return update("updateDriverLocation", driverDto);
	}

	// 기사 배정 조회
	public DeliveryDto acceptance(DeliveryDto deliveryDto) {

		return selectOne("acceptance", deliveryDto);
	}
	
	// 배송기사 배정
	public int orderStatus(DeliveryDto deliveryDto) {

		return update("orderStatus", deliveryDto);
	}


}
