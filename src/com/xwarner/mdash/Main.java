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

	public static final String version = "1.2";

	private Data data;
	private ProjectSetView projectsView;
	private Stage stage;

	public void start(Stage stage) throws Exception {
		this.stage = stage;
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
		if (data.hasParam("width")) {
			stage.setScene(new Scene(root, data.getIntParam("width"), data.getIntParam("height")));
		} else {
			stage.setScene(new Scene(root, 400, 300));
		}
		stage.getIcons().add(new Image(Data.emojiPath + "1f5c3.png"));
		stage.show();
	}

	public void stop() {
		data.setParam("width", (int) stage.getWidth());
		data.setParam("height", (int) stage.getHeight());
		try {
			data.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
