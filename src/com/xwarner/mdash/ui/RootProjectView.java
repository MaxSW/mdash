package com.xwarner.mdash.ui;

import com.xwarner.mdash.Data;
import com.xwarner.mdash.Project;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootProjectView extends TreeItem<String> {

	public RootProjectView(Project project) {
		update(project);
	}

	public void update(Project project) {
		this.setValue(project.getName());
		if (project.hasIcon()) {
			ImageView icon = new ImageView(Data.emojis.get(project.getIconHex()));
			icon.setFitWidth(24.0);
			icon.setFitHeight(24.0);
			this.setGraphic(icon);
		} else {
			this.setGraphic(null);
		}
	}

}
