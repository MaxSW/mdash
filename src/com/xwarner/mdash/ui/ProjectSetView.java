package com.xwarner.mdash.ui;

import com.xwarner.mdash.Data;
import com.xwarner.mdash.Project;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class ProjectSetView extends HBox {

	public void create() {
		for (Project project : Data.getProjects()) {
			getChildren().add(new ProjectView(project, this));
		}
	}

	public void addProject(Project project) {
		getChildren().add(new ProjectView(project, this));
	}

	public void resetSelected(Project project) {
		for (Node n : getChildren()) {
			ProjectView view = (ProjectView) n;
			if (!view.project.equals(project)) {
				// view.getSelectionModel().clearSelection();
				if (!view.getSelectionModel().isEmpty())
					view.getSelectionModel().clearSelection();
			}
		}
	}

}
