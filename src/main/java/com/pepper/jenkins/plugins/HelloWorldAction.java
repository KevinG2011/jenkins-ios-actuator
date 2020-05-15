package com.pepper.jenkins.plugins;

import hudson.model.Run;
import jenkins.model.RunAction2;

public class HelloWorldAction implements RunAction2 {
	private transient Run run;
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
		return null;
	}

	@Override
	public String getDisplayName() {
		return "状态";
	}

	@Override
	public String getUrlName() {
		return "greeting";
	}

	@Override
	public void onAttached(Run<?, ?> run) {
		this.run = run;
	}

	@Override
	public void onLoad(Run<?, ?> run) {
		this.run = run;
	}

	/**
	 * @return the run
	 */
	public Run getRun() {
		return run;
	}

}
