package com.pepper.jms.converter;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import com.pepper.entity.IOSReleaseInfo;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class IAReleaseInfoMessageConverter implements MessageConverter {

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        if (!(message instanceof MapMessage)) {
            throw new MessageConversionException("Message isn't a MapMessage");
        }
        MapMessage mapMessage = (MapMessage) message;
        IOSReleaseInfo releaseInfo = new IOSReleaseInfo();
        releaseInfo.setIpaName(mapMessage.getString("ipaName"));
        releaseInfo.setBundleVersion(mapMessage.getString("bundleVersion"));
        releaseInfo.setBuildVersion(mapMessage.getString("buildVersion"));
        return releaseInfo;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        IOSReleaseInfo releaseInfo = (IOSReleaseInfo) object;
        if (!(object instanceof IOSReleaseInfo)) {
            throw new MessageConversionException("object isn't a IOSReleaseInfo");
        }
        MapMessage message = session.createMapMessage();
        message.setString("ipaName", releaseInfo.getIpaName());
        message.setString("bundleVersion", releaseInfo.getBundleVersion());
        message.setString("buildVersion", releaseInfo.getBuildVersion());
        return message;
    }

}