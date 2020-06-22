package com.pepper.component.dao;

import java.util.List;
import java.util.Map;

import com.pepper.entity.IOSReleaseInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface IOSReleaseInfoDao {
    List<IOSReleaseInfo> findAll();

    IOSReleaseInfo findById(int id);

    IOSReleaseInfo findByParams(Map<String, Object> parameters);
}