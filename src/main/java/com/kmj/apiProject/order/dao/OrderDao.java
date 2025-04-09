package com.kmj.apiProject.order.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.dao.BaseDao;
import com.kmj.apiProject.order.dto.DeliveryDto;
import com.kmj.apiProject.order.dto.ItemDto;
import com.kmj.apiProject.order.dto.OrderDto;

@Repository
public class OrderDao extends BaseDao {

	private static final String NAMESPACE = "com.kmj.apiProject.order.dao.mapper.OrderMapper.";

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	// 주문 등록
	public int receipt(OrderDto orderDto) {
		insert("receipt", orderDto);
		return orderDto.getOrderId();
	}

	// 주문목록 등록
	public int item(ItemDto itemDto) {

		return insert("item", itemDto);
	}

	// 주문 정보 검색
	public DeliveryDto orderDetail(OrderDto orderDto) {

		return selectOne("orderDetail", orderDto);
	}

	// 주문 정보 검색
	public DriverDto driverLocation(DeliveryDto deliveryDto) {

		return selectOne("driverLocation", deliveryDto);
	}

	// 주문 회원 조회
	public AuthDto orderUserDetail(AuthDto authDto) {

		return selectOne("orderUserDetail", authDto);
	}

}
