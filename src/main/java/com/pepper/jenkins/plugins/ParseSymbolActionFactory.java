package com.pepper.jenkins.plugins;

import static java.util.Collections.singleton;

import java.util.Collection;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Project;
import jenkins.model.TransientActionFactory;

@Extension
public class ParseSymbolActionFactory extends TransientActionFactory<Project> {

    @Override
    public Class<Project> type() {
        return Project.class;
    }

    @Override
    public Collection<? extends Action> createFor(Project project) {
        System.out.println(project.getRootDir());
        return singleton(new ParseSymbolAction(project));
    }

}