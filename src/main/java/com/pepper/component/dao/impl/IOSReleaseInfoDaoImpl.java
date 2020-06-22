package com.pepper.component.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pepper.component.dao.IOSBaseDao;
import com.pepper.component.dao.IOSReleaseInfoDao;
import com.pepper.entity.IOSReleaseInfo;

import org.springframework.stereotype.Repository;

@Repository()
public class IOSReleaseInfoDaoImpl extends IOSBaseDao implements IOSReleaseInfoDao {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IOSReleaseInfo> findAll() {
        List<IOSReleaseInfo> list = getSqlMapClientTemplate().queryForList("getAll");
        return list;
    }

    @Override
    public IOSReleaseInfo findById(int id) {
        IOSReleaseInfo releaseInfo = (IOSReleaseInfo) getSqlMapClientTemplate().queryForObject("selectById", id);
        return releaseInfo;
    }

    @Override
    public IOSReleaseInfo findByParams(Map<String, Object> parameters) {
        // TODO Auto-generated method stub
        return null;
    }

}