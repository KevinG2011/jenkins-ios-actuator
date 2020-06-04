package com.pepper.component.dao;

import com.pepper.entity.IOSReleaseInfo;
import com.pepper.entity.IOSReleaseInfoExample;

import org.apache.ibatis.annotations.Mapper;

/**
 * ReleaseDAO继承基类
 */
@Mapper
public interface IOSReleaseInfoDAO extends MyBatisBaseDao<IOSReleaseInfo, Integer, IOSReleaseInfoExample> {

}