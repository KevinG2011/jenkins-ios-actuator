package com.pepper.jenkins.plugins;

import org.kohsuke.stapler.StaplerProxy;

import hudson.model.Action;
import hudson.model.Item;
import hudson.model.Project;

// @Extension
public class ParseSymbolAction implements Action, StaplerProxy {
	private Project project;

	public ParseSymbolAction(Project project) {
		this.project = project;
	}

	@Override
	public String getIconFileName() {
		return this.project.hasPermission(Item.CONFIGURE) ? "clock.gif" : null;
	}

	@Override
	public String getDisplayName() {
		return "在线解析崩溃日志";
	}

	@Override
	public String getUrlName() {
		return "ParseSymbolAction";
	}

	@Override
	public Object getTarget() {
		this.project.checkPermission(Item.CONFIGURE);
		return this;
	}

	public int getBuildStepsCount() {
		return project.getBuilders().size();
	}

	public int getPostBuildStepsCount() {
		return project.getPublishersList().size();
	}

}
