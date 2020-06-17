package com.pepper.jms;

import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.Session;

import com.pepper.entity.IOSReleaseInfo;

import org.springframework.jms.core.JmsTemplate;

public class IAReorderGatewayImpl implements IAReorderGateway {
    private JmsTemplate jmsTemplate;
    private Destination destination;

    @Override
    public void sendReorderInfo(IOSReleaseInfo releaseInfo) {
        jmsTemplate.send(destination, (Session session) -> {

            MapMessage message = session.createMapMessage();
            message.setString("ipaName", releaseInfo.getIpaName());
            message.setString("bundleVersion", releaseInfo.getBundleVersion());
            message.setString("buildVersion", releaseInfo.getBuildVersion());
            return message;
        });
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}