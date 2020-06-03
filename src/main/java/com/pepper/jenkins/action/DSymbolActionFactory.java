package com.pepper.jenkins.action;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        VersionMaintainAction vmAction = new VersionMaintainAction();
        vmAction.setProject(project);
        Set<Action> set = Stream.of(symbolAction, vmAction).collect(Collectors.toSet());
        return set;
    }

}