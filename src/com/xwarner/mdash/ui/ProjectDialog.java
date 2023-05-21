package com.xwarner.mdash.ui;

import com.xwarner.mdash.Project;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ProjectDialog extends Dialog<Project> {

	public ProjectDialog(Project project) {
		if (project == null) {
			this.setTitle("New Project");
		} else {
			this.setTitle("Edit Project");
		}

		GridPane root = new GridPane();

		Label nameLabel = new Label("Name: ");
		GridPane.setConstraints(nameLabel, 0, 0);
		TextField name = new TextField("");
		GridPane.setConstraints(name, 1, 0);
		GridPane.setMargin(name, new Insets(2));

		Label iconLabel = new Label("Icon: ");
		GridPane.setConstraints(iconLabel, 0, 1);

		EmojiPicker picker = new EmojiPicker();
		ScrollPane pickerPane = new ScrollPane(picker);
		GridPane.setConstraints(pickerPane, 1, 1);
		GridPane.setMargin(pickerPane, new Insets(2));

		if (project != null) {
			name.setText(project.getName());
			picker.setEmoji(project.getIconHex());
		}

		root.getChildren().addAll(nameLabel, name, iconLabel, pickerPane);

		this.getDialogPane().contentProperty().set(root);
		this.getDialogPane().setMaxHeight(400);

		if (project == null) {
			this.getDialogPane().getButtonTypes().addAll(new ButtonType("Create", ButtonData.OK_DONE),
					new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
		} else {
			this.getDialogPane().getButtonTypes().addAll(new ButtonType("Update", ButtonData.OK_DONE),
					new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
		}
		this.setResultConverter(new Callback<ButtonType, Project>() {
			public Project call(ButtonType btype) {
				if (btype.getButtonData() == ButtonData.CANCEL_CLOSE)
					return null;
				else if (btype.getButtonData() == ButtonData.OK_DONE) {
					Project project = new Project(name.getText());
					project.setIconHex(picker.getEmoji());
					return project;
				}
				return null;
			}
		});

	}

}
