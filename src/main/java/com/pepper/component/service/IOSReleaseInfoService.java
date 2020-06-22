package com.pepper.component.service;

import java.util.List;

import com.pepper.entity.IOSReleaseInfo;

public interface IOSReleaseInfoService {

    public List<IOSReleaseInfo> getAll();

    public List<IOSReleaseInfo> add(IOSReleaseInfo release);

    public List<IOSReleaseInfo> delete(int id);

}
