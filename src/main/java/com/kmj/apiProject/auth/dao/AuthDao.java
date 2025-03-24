package com.kmj.apiProject.auth.dao;


import org.springframework.stereotype.Repository;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.common.dao.BaseDao;

@Repository
public class AuthDao extends BaseDao {

  
    private static final String NAMESPACE = "com.kmj.apiProject.auth.dao.mapper.AuthMapper.";


    @Override
    protected String getNamespace() {
        return NAMESPACE;
    }

    // id로 사용자 정보 조회
    public AuthDto userDetail(AuthDto authDto) {
        
        return selectOne("userDetail", authDto);
    }
    
 	// 회원가입
    public int signUp(AuthDto authDto) {
        
        return insert("signUp", authDto);
    }
    
}