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
		// TODO Auto-generated method stub
		return "document.png";
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "Greeting";
	}

	@Override
	public String getUrlName() {
		// TODO Auto-generated method stub
		return "greeting";
	}

}
