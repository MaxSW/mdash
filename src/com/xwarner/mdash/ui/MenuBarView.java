package com.xwarner.mdash.ui;

import java.io.IOException;

import com.xwarner.mdash.Data;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenuBarView extends HBox {

	private boolean onTop;

	public MenuBarView(Data data, ProjectSetView view, Stage stage) {
		/*
		 * Menu menu = new Menu("File"); MenuItem newProjectItem = new
		 * MenuItem("New Project"); newProjectItem.setOnAction(e -> { ProjectDialog
		 * input = new ProjectDialog(null); input.showAndWait().ifPresent(newProject ->
		 * { if (newProject != null) { data.createProject(newProject);
		 * view.addProject(newProject, data); try { data.save(); } catch (IOException
		 * ex) { ex.printStackTrace(); } } });
		 * 
		 * }); menu.getItems().addAll(newProjectItem); getMenus().add(menu);
		 */
		ToggleButton onTopButton = new ToggleButton("On Top");
		onTopButton.setOnAction(e -> {
			onTop = !onTop;
			stage.setAlwaysOnTop(onTop);
		});
		HBox.setMargin(onTopButton, new Insets(5, 2, 5, 4));

		Button projectButton = new Button("New Project");
		projectButton.setOnAction(e -> {
			ProjectDialog input = new ProjectDialog(null);
			input.showAndWait().ifPresent(newProject -> {
				if (newProject != null) {
					data.createProject(newProject);
					view.addProject(newProject, data);
					try {
						data.save();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
		});
		HBox.setMargin(projectButton, new Insets(5, 2, 5, 2));

		Button settingsButton = new Button("Settings");
		HBox.setMargin(settingsButton, new Insets(5, 2, 5, 2));

		Button aboutButton = new Button("About");
		aboutButton.setOnAction(e -> {
			new AboutWindow();
		});
		HBox.setMargin(aboutButton, new Insets(5, 2, 5, 2));

		// settingsButton,
		getChildren().addAll(onTopButton, projectButton, aboutButton);

	}

}
