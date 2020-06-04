package com.pepper.jenkins.action;

import com.pepper.utils.ActuatorDiskUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import hudson.Extension;
import hudson.model.RootAction;

@Extension
public class ActuatorRootAction implements RootAction {
    static {
        ApplicationContext context = new ClassPathXmlApplicationContext();
    }

    @Override
    public String getIconFileName() {
        return "gear.png";
    }

    @Override
    public String getDisplayName() {
        return "花椒监控中心";
    }

    @Override
    public String getUrlName() {
        return "actuator";
    }

    public String getFreeDiskSpaceDetail() {
        return ActuatorDiskUtils.getFreeDiskSpaceDetail();
    }
}