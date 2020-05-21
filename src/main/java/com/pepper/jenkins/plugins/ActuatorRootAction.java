package com.pepper.jenkins.plugins;

import com.pepper.utils.ActuatorDiskUtils;

import hudson.Extension;
import hudson.model.RootAction;

@Extension
public class ActuatorRootAction implements RootAction {

    @Override
    public String getIconFileName() {
        return "gear.png";
    }

    @Override
    public String getDisplayName() {
        return "花椒控制中心";
    }

    @Override
    public String getUrlName() {
        return "actuator";
    }

    public String getFreeDiskSpaceDetail() {
        return ActuatorDiskUtils.getFreeDiskSpaceDetail();
    }
}