package com.pepper.jenkins.action;

import java.io.IOException;

import javax.servlet.ServletException;

import com.pepper.jenkins.controller.DSymbolController;
import com.pepper.symbol.SymbolicResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.Action;
import hudson.model.Item;
import hudson.model.Project;

public class DSymbolAction implements Action, StaplerProxy {
	private Project project;
	private DSymbolController dsymbolController;
	private SymbolicResult symbolResult;
	private String searchDsymLink;

	public DSymbolAction() {
		this.dsymbolController = new DSymbolController();
	}

	public void setProject(Project project) {
		this.project = project;
		this.dsymbolController.setProject(project);
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
		return "DSymbolAction";
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
			this.symbolResult = this.dsymbolController.symbolicate(fileItem);
		} catch (Exception e) {
			System.err.println(e);
		}
		execute(request, response, nextPage);
	}

	public void doSearch(final StaplerRequest request, final StaplerResponse response) throws ServletException {
		this.symbolResult = null;
		try {
			final String str = getRequestParameter(request, "versionNum");
			if (StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)) {
				final int versionNum = Integer.parseInt(str.trim());
				this.symbolResult = this.dsymbolController.findDSYMRemoteUrl(versionNum);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		execute(request, response, "");
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

	public String getSearchDsymLink() {
		return searchDsymLink;
	}

	public SymbolicResult getSymbolResult() {
		return symbolResult;
	}
}
