package com.kmj.apiProject.order.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.common.dao.BaseDao;
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
	public int order(OrderDto orderDto) {
		int orderId = insert("order", orderDto);
		return orderId;
	}

	// 주문목록  등록
	public int item(ItemDto itemDto) {
		
		return insert("item", itemDto);
	}

}
