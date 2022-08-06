package com.xwarner.mdash.ui;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;

public class ActionCellView extends TreeCell<String> {

	public ActionCellView() {
		super();
		setOnMouseClicked(event -> {
			TreeItem<String> item = getTreeItem();
			if (item != null & event.getClickCount() >= 2) {
				if (item instanceof ActionView) {
					((ActionView) item).run();
				}
			}
		});

	}

	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			setText(getString());
			setGraphic(getTreeItem().getGraphic());
			if (getTreeItem() instanceof RootProjectView) {
				setStyle(" -fx-font-weight: bold ;");
			}
		}
	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}

}
