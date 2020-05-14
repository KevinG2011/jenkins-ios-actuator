package com.pepper.jenkins.plugins;

import hudson.model.Action;

public class HelloWorldAction implements Action {
	private String name;

	public HelloWorldAction(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getIconFileName() {
		return "document.png";
	}

	@Override
	public String getDisplayName() {
		return "Greeting";
	}

	@Override
	public String getUrlName() {
		return "greeting";
	}

}
