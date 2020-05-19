package com.pepper.jenkins.plugins;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

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

	public void doUpload(final StaplerRequest request, final StaplerResponse response)
			throws IOException, ServletException {
		String nextPage = "";
		final FileItem fileItem = request.getFileItem("file.dsym");
		// System.out.println(fileItem.getString("utf8"));
		execute(request, response, nextPage);
	}

	private void execute(final StaplerRequest request, final StaplerResponse response, final String nextPage)
			throws IOException, ServletException {
		String url = request.getContextPath() + "/" + getUrlName() + nextPage;
		response.sendRedirect(url);
	}

}
