package com.kmj.apiProject.driver.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.common.dao.BaseDao;

@Repository
public class DriverDao extends BaseDao{
	
	private static final String NAMESPACE = "com.kmj.apiProject.driver.dao.mapper.DriverMapper.";

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

}
