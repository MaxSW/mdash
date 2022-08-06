package com.xwarner.mdash;

import com.xwarner.mdash.ui.MenuBarView;
import com.xwarner.mdash.ui.ProjectSetView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static final String version = "1.0";

	private Data data;
	private ProjectSetView projectsView;

	public void start(Stage stage) throws Exception {
		data = new Data();
		data.load();
		projectsView = new ProjectSetView();
		createWindow(stage);
	}

	public void createWindow(Stage stage) {
		VBox root = new VBox();
		projectsView.create(data);
		VBox.setVgrow(projectsView, Priority.ALWAYS);
		root.getChildren().addAll(projectsView, new MenuBarView(data, projectsView, stage));
		stage.setTitle("MDASH " + version);
		stage.setScene(new Scene(root, 400, 300));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../../../twemoji/1f5c3.png")));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
