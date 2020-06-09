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
    IOSReleaseInfoDao releaseDao;

    public List<IOSReleaseInfo> list() {
        return new ArrayList<>();
    }

    public List<IOSReleaseInfo> add(IOSReleaseInfo release) {
        return new ArrayList<>();
    }

    public List<IOSReleaseInfo> delete(int id) {
        return new ArrayList<>();
    }

}
