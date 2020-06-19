package com.pepper.jms.sync;

import com.pepper.entity.IOSReleaseInfo;
import com.pepper.jms.IAReorderGateway;

import org.springframework.jms.core.support.JmsGatewaySupport;

public class IAReorderGatewayImpl extends JmsGatewaySupport implements IAReorderGateway {

    @Override
    public void sendReorderInfo(final IOSReleaseInfo releaseInfo) {
        getJmsTemplate().convertAndSend(releaseInfo);
    }

    @Override
    public IOSReleaseInfo receiveReleaseMessage() {
        // 以默认Destation接收
        IOSReleaseInfo releaseInfo = (IOSReleaseInfo) getJmsTemplate().receiveAndConvert();
        return releaseInfo;
    }

}