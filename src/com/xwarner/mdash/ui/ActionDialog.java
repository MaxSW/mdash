package com.xwarner.mdash.ui;

import com.xwarner.mdash.actions.Action;
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
		TextField icon = new TextField("");
		GridPane.setConstraints(icon, 1, 1);
		GridPane.setMargin(icon, new Insets(2));

		Label typeLabel = new Label("Type: ");
		GridPane.setConstraints(typeLabel, 0, 2);
		ChoiceBox<String> type = new ChoiceBox<String>(FXCollections.observableArrayList("Folder", "Web"));
		GridPane.setConstraints(type, 1, 2);
		GridPane.setMargin(type, new Insets(2));

		Label field1Label = new Label("");
		GridPane.setConstraints(field1Label, 0, 3);
		TextField field1 = new TextField("");
		field1.setVisible(false);
		GridPane.setConstraints(field1, 1, 3);
		GridPane.setMargin(field1, new Insets(2));

		type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ob_value, String old_value, String new_value) {
				field1Label.setText("");
				field1.setVisible(false);

				if (new_value.equals("Folder")) {
					field1Label.setText("Path");
					field1.setVisible(true);
				} else if (new_value.equals("Web")) {
					field1Label.setText("URL");
					field1.setVisible(true);
				}
			}
		});

		if (action != null) {
			name.setText(action.getName());
			icon.setText(action.getIconHex());
			type.setDisable(true);
			if (action.getType().equals("folder")) {
				type.setValue("Folder");
				field1.setText(((FolderAction) action).getPath());
			} else if (action.getType().equals("web")) {
				type.setValue("Web");
				field1.setText(((WebAction) action).getUrl());
			}
		}

		GridPane.setConstraints(type, 1, 2);

		root.getChildren().addAll(nameLabel, name, iconLabel, icon, typeLabel, type, field1Label, field1);

		this.getDialogPane().contentProperty().set(root);

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
						FolderAction action = new FolderAction(name.getText(), icon.getText());
						action.setPath(field1.getText());
						return action;
					} else if (atype.equals("Web")) {
						WebAction action = new WebAction(name.getText(), icon.getText());
						action.setUrl(field1.getText());
						return action;
					}
				}
				return null;
			}
		});

	}

}
