package com.pepper.component.dao;

import java.util.List;

import com.pepper.entity.IOSReleaseInfo;

/**
 * ReleaseDAO继承基类
 */
public interface IOSReleaseInfoDao {
    List<IOSReleaseInfo> findAll();

    IOSReleaseInfo findById(String id);
}