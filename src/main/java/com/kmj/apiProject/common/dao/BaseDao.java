package com.kmj.apiProject.common.dao;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public abstract class BaseDao {

 
    @Autowired
    protected SqlSession sqlSession;

    // 네임스페이스 추상 메서드
    protected abstract String getNamespace();

    // 기본적인 selectOne 메서드
    protected <T> T selectOne(String queryId, Object parameter) {
        return sqlSession.selectOne(getNamespace() + queryId, parameter);
    }

    // 기본적인 selectList 메서드
    protected <T> List<T> selectList(String queryId, Object parameter) {
        return sqlSession.selectList(getNamespace() + queryId, parameter);
    }

    // 기본적인 insert 메서드
    protected int insert(String queryId, Object parameter) {
        return sqlSession.insert(getNamespace() + queryId, parameter);
    }

    // 기본적인 update 메서드
    protected int update(String queryId, Object parameter) {
        return sqlSession.update(getNamespace() + queryId, parameter);
    }

    // 기본적인 delete 메서드
    protected int delete(String queryId, Object parameter) {
        return sqlSession.delete(getNamespace() + queryId, parameter);
    }
}