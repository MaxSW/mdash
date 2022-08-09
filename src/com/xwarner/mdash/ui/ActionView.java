package com.xwarner.mdash.ui;

import com.xwarner.mdash.Data;
import com.xwarner.mdash.actions.Action;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ActionView extends TreeItem<String> {

	private Action action;

	public ActionView(Action action) {
		updateAction(action);
	}

	public void run() {
		try {
			this.action.run();
		} catch (Exception e) {
			// TODO Popup window with errors
			e.printStackTrace();
		}
	}

	public void updateAction(Action action) {
		this.action = action;
		this.setValue(action.getName());
		if (action.hasIcon()) {
			ImageView icon = new ImageView(new Image(Data.emojiPath + action.getIconHex() + ".png"));
			icon.setFitWidth(24.0);
			icon.setFitHeight(24.0);
			this.setGraphic(icon);
		}
	}

}
