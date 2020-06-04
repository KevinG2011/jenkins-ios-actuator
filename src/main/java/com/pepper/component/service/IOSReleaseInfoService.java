package com.pepper.component.service;

import java.util.List;

import com.pepper.component.dao.IOSReleaseInfoDAO;
import com.pepper.entity.IOSReleaseInfo;
import com.pepper.entity.IOSReleaseInfoExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IOSReleaseInfoService {
    @Autowired
    IOSReleaseInfoDAO releaseDao;

    public List<IOSReleaseInfo> list() {
        IOSReleaseInfoExample example = new IOSReleaseInfoExample();
        return releaseDao.selectByExample(example);
    }

    public List<IOSReleaseInfo> add(IOSReleaseInfo release) {
        releaseDao.insert(release);
        return list();
    }

    public List<IOSReleaseInfo> delete(int id) {
        releaseDao.deleteByPrimaryKey(id);
        return list();
    }

}
