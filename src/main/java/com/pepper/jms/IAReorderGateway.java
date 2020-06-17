package com.pepper.jms;

import com.pepper.entity.IOSReleaseInfo;

public interface IAReorderGateway {
    void sendReorderInfo(IOSReleaseInfo releaseInfo);
}