package com.kmj.apiProject.user.dao;

import org.springframework.stereotype.Repository;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.common.dao.BaseDao;
import com.kmj.apiProject.user.dto.UserDeliveryDto;

@Repository
public class UserDao extends BaseDao {

	private static final String NAMESPACE = "com.kmj.apiProject.user.dao.mapper.UserMapper.";


    @Override
    protected String getNamespace() {
        return NAMESPACE;
    }
    
    // userId로 사용자 정보 조회
    public AuthDto userDetail(int id) {
        
        return selectOne("userDetail", id);
    }
    
    
    // 배송지 + 출입방법 등록
    public int delivery(UserDeliveryDto userDeliveryDto) {
        
        return insert("delivery", userDeliveryDto);
    }
}
