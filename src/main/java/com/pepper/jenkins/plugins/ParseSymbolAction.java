package com.pepper.jenkins.plugins;

import java.io.IOException;

import javax.servlet.ServletException;

import com.pepper.jenkins.manager.JobDSYMFileManager;

import org.apache.commons.fileupload.FileItem;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.Action;
import hudson.model.Item;
import hudson.model.Project;

public class ParseSymbolAction implements Action, StaplerProxy {
	private Project project;
	private JobDSYMFileManager dsymFileManager;
	private String searchDsymLink;

	public ParseSymbolAction() {
		this.dsymFileManager = new JobDSYMFileManager();
	}

	public void setProject(Project project) {
		this.project = project;
		this.dsymFileManager.setProject(project);
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
		return "解析崩溃日志";
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
		final String nextPage = "";
		final FileItem fileItem = request.getFileItem("file.dsym");
		if (null != fileItem) {

		}
		// System.out.println(fileItem.getString("utf8"));
		execute(request, response, nextPage);
	}

	public void doSearch(final StaplerRequest request, final StaplerResponse response)
			throws IOException, ServletException {
		final String versionNumStr = getRequestParameter(request, "versionNum");
		final int versionNum = Integer.parseInt(versionNumStr.trim());
		this.searchDsymLink = this.dsymFileManager.findDsymLink(versionNum);
		execute(request, response, "");
	}

	private void execute(final StaplerRequest request, final StaplerResponse response, final String nextPage)
			throws IOException, ServletException {
		final String url = this.project.getAbsoluteUrl() + this.getUrlName() + nextPage;
		response.sendRedirect(url);
	}

	private static String getRequestParameter(final StaplerRequest request, final String key) throws ServletException {
		return request.getSubmittedForm().getString(key);
	}

	public String getSearchDsymLink() {
		return searchDsymLink;
	}

	public void setSearchDsymLink(String searchDsymLink) {
		this.searchDsymLink = searchDsymLink;
	}

}
