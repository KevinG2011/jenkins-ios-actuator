package com.pepper.component.service;

import java.util.ArrayList;
import java.util.List;

import com.pepper.component.dao.IOSReleaseInfoDao;
import com.pepper.entity.IOSReleaseInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IOSReleaseInfoService {
    @Autowired
    IOSReleaseInfoDao releaseInfoDao;

    public void setReleaseInfoDao(IOSReleaseInfoDao releaseInfoDao) {
        this.releaseInfoDao = releaseInfoDao;
    }

    public List<IOSReleaseInfo> getAll() {
        return new ArrayList<>();
    }

    public List<IOSReleaseInfo> add(IOSReleaseInfo release) {

        return new ArrayList<>();
    }

    public List<IOSReleaseInfo> delete(int id) {
        return new ArrayList<>();
    }

}
