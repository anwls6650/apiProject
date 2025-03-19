package com.kmj.apiProject.login.dao;


import org.springframework.stereotype.Repository;

import com.kmj.apiProject.common.dao.BaseDao;
import com.kmj.apiProject.login.dto.LoginDto;

@Repository
public class LoginDao extends BaseDao {

  
    private static final String NAMESPACE = "com.kmj.apiProject.login.dao.mapper.LoginMapper.";


    @Override
    protected String getNamespace() {
        return NAMESPACE;
    }

    // 사용자명으로 사용자 정보 조회
    public LoginDto userDetail(LoginDto loginDto) {
        
        return selectOne("userDetail", loginDto);
    }
}