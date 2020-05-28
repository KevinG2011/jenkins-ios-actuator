package com.pepper.jenkins.action;

import static java.util.Collections.singleton;

import java.util.Collection;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Project;
import jenkins.model.TransientActionFactory;

@Extension
public class DSymbolActionFactory extends TransientActionFactory<Project> {
    private static DSymbolAction symbolAction = new DSymbolAction();

    @Override
    public Class<Project> type() {
        return Project.class;
    }

    @Override
    public Collection<? extends Action> createFor(Project project) {
        if (symbolAction != null) {
            symbolAction.setProject(project);
        }
        return singleton(symbolAction);
    }

}