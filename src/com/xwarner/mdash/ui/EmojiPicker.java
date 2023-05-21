package com.xwarner.mdash.ui;

import java.util.HashMap;

import com.xwarner.mdash.Data;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class EmojiPicker extends GridPane {

	private ToggleGroup group;
	private HashMap<String, ToggleButton> buttons;

	public EmojiPicker() {
		group = new ToggleGroup();
		buttons = new HashMap<String, ToggleButton>();

		final int width = 10;
		int i = 0;
		for (String code : Data.emojiOrder) {
			ToggleButton button = new ToggleButton();
			ImageView iv = new ImageView(Data.emojis.get(code));
			iv.setFitHeight(20);
			iv.setPreserveRatio(true);
			button.setGraphic(iv);
			button.setToggleGroup(group);
			button.setUserData(code);
			add(button, i % width, Math.floorDiv(i, width));
			buttons.put(code, button);
			i++;
		}
	}

	public void setEmoji(String icon) {
		if (buttons.containsKey(icon))
			group.selectToggle(buttons.get(icon));
	}

	public String getEmoji() {
		if (group.getSelectedToggle() == null)
			return "";
		return (String) group.getSelectedToggle().getUserData();
	}

}
