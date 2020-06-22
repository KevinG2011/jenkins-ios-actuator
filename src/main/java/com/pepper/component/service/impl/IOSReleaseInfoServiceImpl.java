package com.pepper.component.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pepper.component.dao.IOSReleaseInfoDao;
import com.pepper.component.service.IOSReleaseInfoService;
import com.pepper.entity.IOSReleaseInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("releaseInfoService")
// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IOSReleaseInfoServiceImpl implements IOSReleaseInfoService {
    @Autowired
    IOSReleaseInfoDao releaseInfoDao;

    public void setReleaseInfoDao(IOSReleaseInfoDao releaseInfoDao) {
        this.releaseInfoDao = releaseInfoDao;
    }

    public List<IOSReleaseInfo> getAll() {
        System.out.println(releaseInfoDao);
        return new ArrayList<>();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<IOSReleaseInfo> add(IOSReleaseInfo release) {

        return new ArrayList<>();
    }

    public List<IOSReleaseInfo> delete(int id) {
        return new ArrayList<>();
    }

}
