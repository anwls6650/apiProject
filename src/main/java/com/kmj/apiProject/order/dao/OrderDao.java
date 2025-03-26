package com.kmj.apiProject.order.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.common.dao.BaseDao;

@Repository
public class OrderDao extends BaseDao{
	
	private static final String NAMESPACE = "com.kmj.apiProject.order.dao.mapper.OrderMapper.";

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	

}
