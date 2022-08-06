package com.xwarner.mdash.ui;

import com.xwarner.mdash.Data;
import com.xwarner.mdash.Project;

import javafx.scene.layout.HBox;

public class ProjectSetView extends HBox {

	public void create(Data data) {
		for (Project project : data.getProjects()) {
			getChildren().add(new ProjectView(project, data, this));
		}
	}

	public void addProject(Project project, Data data) {
		getChildren().add(new ProjectView(project, data, this));
	}

}
