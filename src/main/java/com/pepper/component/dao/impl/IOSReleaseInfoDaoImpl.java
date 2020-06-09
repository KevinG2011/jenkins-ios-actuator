package com.pepper.component.dao.impl;

import java.util.List;

import com.pepper.component.dao.IOSReleaseInfoDao;
import com.pepper.entity.IOSReleaseInfo;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IOSReleaseInfoDaoImpl extends SqlMapClientDaoSupport implements IOSReleaseInfoDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<IOSReleaseInfo> findAll() {
        List<IOSReleaseInfo> list = getSqlMapClientTemplate().queryForList("getAll");
        return list;
    }

    @Override
    public IOSReleaseInfo findById(String id) {
        IOSReleaseInfo releaseInfo = (IOSReleaseInfo) getSqlMapClientTemplate().queryForObject("selectById", id);
        return releaseInfo;
    }

}