package com.pepper.component.service;

import java.util.List;

import com.pepper.entity.IOSReleaseInfo;

import org.springframework.stereotype.Service;

@Service()
// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface IOSReleaseInfoService {

    public List<IOSReleaseInfo> getAll();

    public List<IOSReleaseInfo> add(IOSReleaseInfo release);

    public List<IOSReleaseInfo> delete(int id);

}
