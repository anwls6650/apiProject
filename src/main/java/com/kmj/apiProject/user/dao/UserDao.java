package com.kmj.apiProject.user.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.common.dao.BaseDao;

@Repository
public class UserDao extends BaseDao {

	private static final String NAMESPACE = "com.kmj.apiProject.user.dao.mapper.UserMapper.";


    @Override
    protected String getNamespace() {
        return NAMESPACE;
    }
    
 // id로 사용자 정보 조회
    public AuthDto userDetail(String id) {
        
        return selectOne("userDetail", id);
    }
}
