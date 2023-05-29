package com.xwarner.mdash.ui;

import java.lang.reflect.Constructor;

import com.xwarner.mdash.actions.Action;
import com.xwarner.mdash.actions.Attribute;
import com.xwarner.mdash.actions.FileAction;
import com.xwarner.mdash.actions.FolderAction;
import com.xwarner.mdash.actions.WebAction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ActionDialog extends Dialog<Action> {

	public ActionDialog(Action action) {
		if (action == null) {
			this.setTitle("New Action");
		} else {
			this.setTitle("Edit Action");
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

		Label typeLabel = new Label("Type: ");
		GridPane.setConstraints(typeLabel, 0, 2);
		// TODO get this from somewhere
		ChoiceBox<String> type = new ChoiceBox<String>(FXCollections.observableArrayList(Action.actions.keySet()));
		GridPane.setConstraints(type, 1, 2);
		GridPane.setMargin(type, new Insets(2));

		Label field1Label = new Label("");
		GridPane.setConstraints(field1Label, 0, 3);
		TextField field1 = new TextField("");
		field1.setVisible(false);
		GridPane.setConstraints(field1, 1, 3);
		GridPane.setMargin(field1, new Insets(2));

		if (action != null) {
			name.setText(action.getName());
			picker.setEmoji(action.getIconHex());

			type.setDisable(true);
			String typeName = action.getType();
			type.setValue(typeName.substring(0, 1).toUpperCase() + typeName.substring(1));

			// load attributes from field class

			// TODO handle more than one attribute
			if (action.attributes.size() > 0) {
				// TODO check if editable
				Attribute attribute = action.attributes.get(action.attributes.keySet().toArray()[0]);
				field1Label.setText(attribute.displayName);
				field1.setText((String) attribute.value);
				field1.setVisible(true);
			}
		}

		type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ob_value, String old_value, String new_value) {
				field1Label.setText("");
				field1.setVisible(false);

				// TODO there must be a better way of doing this - but I don't want to store the
				// config data in more than one place
				if (Action.actions.containsKey(new_value)) {
					try {
						Class actionClass = Action.actions.get(new_value);
						Class[] types = { String.class, String.class };
						Constructor constructor = actionClass.getConstructor(types);
						Object[] parameters = { "", "" };
						Action instance = (Action) constructor.newInstance(parameters);

						// TODO handle more than one attribute
						if (instance.attributes.size() > 0) {
							// TODO check if editable
							Attribute attribute = instance.attributes.get(instance.attributes.keySet().toArray()[0]);
							field1Label.setText(attribute.displayName);
							field1.setText((String) attribute.value);
							field1.setVisible(true);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});

		GridPane.setConstraints(type, 1, 2);

		root.getChildren().addAll(nameLabel, name, iconLabel, pickerPane, typeLabel, type, field1Label, field1);

		this.getDialogPane().contentProperty().set(root);
		this.getDialogPane().setMaxHeight(400);

		if (action == null) {
			this.getDialogPane().getButtonTypes().addAll(new ButtonType("Create", ButtonData.OK_DONE),
					new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
		} else {
			this.getDialogPane().getButtonTypes().addAll(new ButtonType("Update", ButtonData.OK_DONE),
					new ButtonType("Cancel", ButtonData.CANCEL_CLOSE));
		}
		this.setResultConverter(new Callback<ButtonType, Action>() {
			public Action call(ButtonType btype) {
				if (btype.getButtonData() == ButtonData.CANCEL_CLOSE)
					return null;
				else if (btype.getButtonData() == ButtonData.OK_DONE) {
					String atype = type.getValue();
					if (atype.equals("Folder")) {
						FolderAction action = new FolderAction(name.getText(), picker.getEmoji());
						action.setPath(field1.getText());
						return action;
					} else if (atype.equals("Web")) {
						WebAction action = new WebAction(name.getText(), picker.getEmoji());
						action.setUrl(field1.getText());
						return action;
					} else if (atype.equals("File")) {
						FileAction action = new FileAction(name.getText(), picker.getEmoji());
						action.setPath(field1.getText());
						return action;
					}
				}
				return null;
			}
		});

	}

}
