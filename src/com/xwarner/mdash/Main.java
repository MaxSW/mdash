package com.xwarner.mdash;

import java.io.IOException;

import com.xwarner.mdash.ui.MenuBarView;
import com.xwarner.mdash.ui.ProjectSetView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static final String version = "1.3 DEV";

	private ProjectSetView projectsView;
	private Stage stage;

	public void start(Stage stage) throws Exception {
		this.stage = stage;
		projectsView = new ProjectSetView();
		createWindow(stage);
	}

	public void createWindow(Stage stage) {
		VBox root = new VBox();
		projectsView.create();
		VBox.setVgrow(projectsView, Priority.ALWAYS);
		root.getChildren().addAll(projectsView, new MenuBarView(projectsView, stage));
		stage.setTitle("MDASH " + version);
		if (Data.hasParam("width")) {
			stage.setScene(new Scene(root, Data.getIntParam("width"), Data.getIntParam("height")));
		} else {
			stage.setScene(new Scene(root, 400, 300));
		}
		stage.getIcons().add(Data.emojis.get("1f5c3"));
		stage.show();
	}

	public void stop() {
		Data.setParam("width", (int) stage.getWidth());
		Data.setParam("height", 300); // TODO
		try {
			Data.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Data.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}

}
