package com.xwarner.mdash.ui;

import com.xwarner.mdash.Main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutWindow extends Stage {

	public AboutWindow() {
		VBox root = new VBox();
		root.setPadding(new Insets(5));

		Label line1 = new Label("MDASH version " + Main.version + ". Built by Max Warner." + "\r\n");
		Label line2 = new Label("Open source licences:" + "\r\n");
		Label line3 = new Label("Twemoji - https://twemoji.twitter.com/");
		Label line4 = new Label("Copyright 2020 Twitter, Inc and other contributors\r\n"
				+ "Code licensed under the MIT License: http://opensource.org/licenses/MIT\r\n"
				+ "Graphics licensed under CC-BY 4.0: https://creativecommons.org/licenses/by/4.0/\r\n");
		Label line5 = new Label("JSON-java - https://github.com/stleary/JSON-java");
		Label line6 = new Label("Copyright (c) 2002 JSON.org\r\n" 
				+ "Permission is hereby granted, free of charge, to any person obtaining a copy\r\n"
				+ "of this software and associated documentation files (the \"Software\"), to deal\r\n"
				+ "in the Software without restriction, including without limitation the rights\r\n"
				+ "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\r\n"
				+ "copies of the Software, and to permit persons to whom the Software is\r\n"
				+ "furnished to do so, subject to the following conditions: \r\n"
				+ "The above copyright notice and this permission notice shall be included in all\r\n"
				+ "copies or substantial portions of the Software.\r\n"
				+ "The Software shall be used for Good, not Evil.\r\n"
				+ "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\r\n"
				+ "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\r\n"
				+ "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\r\n"
				+ "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\r\n"
				+ "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\r\n"
				+ "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\r\n" + "SOFTWARE.");

		root.getChildren().addAll(line1, line2, line3, line4, line5, line6);
		ScrollPane scroll = new ScrollPane(root);

		setTitle("About MDASH");
		getIcons().add(new Image(getClass().getResourceAsStream("../../../../twemoji/1f5c3.png")));
		setScene(new Scene(scroll, 450, 250));
		setResizable(false);
		show();
	}
}
