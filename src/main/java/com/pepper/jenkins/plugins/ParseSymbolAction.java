package com.pepper.jenkins.plugins;

import java.io.IOException;

import javax.servlet.ServletException;

import com.pepper.jenkins.manager.DSYMFileManager;
import com.pepper.symbol.SymbolicateResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.Action;
import hudson.model.Item;
import hudson.model.Project;

public class ParseSymbolAction implements Action, StaplerProxy {
	private Project project;
	private DSYMFileManager dsymFileManager;
	private SymbolicateResult symbolResult;
	private String searchDsymLink;

	public ParseSymbolAction() {
		this.dsymFileManager = new DSYMFileManager();
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

	public void doUpload(final StaplerRequest request, final StaplerResponse response) throws ServletException {
		this.symbolResult = null;
		final String nextPage = "";
		try {
			final FileItem fileItem = request.getFileItem("file.dsym");
			this.symbolResult = this.dsymFileManager.symbolicate(fileItem);
			this.dsymFileManager.cleanUpCrashFiles();
			execute(request, response, nextPage);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void doSearch(final StaplerRequest request, final StaplerResponse response)
			throws IOException, ServletException {
		final String str = getRequestParameter(request, "versionNum");
		if (StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)) {
			final int versionNum = Integer.parseInt(str.trim());
			this.searchDsymLink = this.dsymFileManager.findDSYMRemoteUrl(versionNum);
		}
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

	public SymbolicateResult getSymbolResult() {
		return symbolResult;
	}
}
