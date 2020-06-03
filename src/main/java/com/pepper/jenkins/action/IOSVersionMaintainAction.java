package com.pepper.jenkins.action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.Action;
import hudson.model.Item;
import hudson.model.Project;

public class IOSVersionMaintainAction implements Action, StaplerProxy {

	private Project project;

	public IOSVersionMaintainAction() {
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	@Override
	public String getIconFileName() {
		if (this.project != null) {
			return this.project.hasPermission(Item.CONFIGURE) ? "clock.gif" : null;
		}
		return null;
	}

	@Override
	public String getDisplayName() {
		return "版本维护";
	}

	@Override
	public String getUrlName() {
		return "versionMaintain";
	}

	@Override
	public Object getTarget() {
		this.project.checkPermission(Item.CONFIGURE);
		return this;
	}

	public void doList(final StaplerRequest request, final StaplerResponse response) throws ServletException {
		final String nextPage = "";
		execute(request, response, nextPage);
	}

	private void execute(final StaplerRequest request, final StaplerResponse response, final String nextPage) {
		final String url = this.project.getAbsoluteUrl() + this.getUrlName() + nextPage;
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private static String getRequestParameter(final StaplerRequest request, final String key) throws ServletException {
		return request.getSubmittedForm().getString(key);
	}
}
