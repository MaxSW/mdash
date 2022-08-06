package com.xwarner.mdash.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.xwarner.mdash.Data;
import com.xwarner.mdash.Project;
import com.xwarner.mdash.actions.Action;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class ProjectView extends TreeView<String> {

	public ProjectView(Project project, Data data, ProjectSetView view) {
		TreeItem<String> rootItem = new TreeItem<String>("");
		rootItem.getChildren().add(new RootProjectView(project));
		for (Action action : project.getActions()) {
			rootItem.getChildren().add(new ActionView(action));
		}
		rootItem.setExpanded(true);
		setRoot(rootItem);

		ContextMenu menu = new ContextMenu();
		MenuItem addItem = new MenuItem("Add");
		MenuItem editItem = new MenuItem("Edit");
		MenuItem deleteItem = new MenuItem("Delete");
		MenuItem moveupItem = new MenuItem("Move Up");
		MenuItem movedownItem = new MenuItem("Move Down");

		addItem.setOnAction((event) -> {
			ActionDialog input = new ActionDialog(null);
			input.showAndWait().ifPresent(action -> {
				if (action != null) {
					rootItem.getChildren().add(new ActionView(action));
					project.addAction(action);
					try {
						data.save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

		});

		editItem.setOnAction((event) -> {
			if (this.getSelectionModel().getSelectedItem() != null) {
				String name = this.getSelectionModel().getSelectedItem().getValue();
				if (name.equals(project.getName())) {
					ProjectDialog input = new ProjectDialog(project);
					input.showAndWait().ifPresent(newProject -> {
						if (newProject != null) {
							project.update(newProject);
							((RootProjectView) rootItem.getChildren().get(0)).update(newProject);
							try {
								data.save();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					ActionDialog input = new ActionDialog(project.getAction(name));
					input.showAndWait().ifPresent(action -> {
						if (action != null) {
							for (TreeItem<?> item : rootItem.getChildren()) {
								if ((String) item.getValue() == name) {
									((ActionView) item).updateAction(action);
								}
							}
							project.updateAction(name, action);
							try {
								data.save();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		deleteItem.setOnAction((event) -> {
			if (this.getSelectionModel().getSelectedItem() != null) {
				String name = this.getSelectionModel().getSelectedItem().getValue();
				if (name.equals(project.getName())) {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Delete project \"" + name + "\"?", ButtonType.YES,
							ButtonType.CANCEL);
					alert.showAndWait().ifPresent(action -> {
						if (action == ButtonType.YES) {
							view.getChildren().remove(this);
							data.deleteProject(project);
							try {
								data.save();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Delete action \"" + name + "\"?", ButtonType.YES,
							ButtonType.CANCEL);
					alert.showAndWait().ifPresent(action -> {
						if (action == ButtonType.YES) {
							TreeItem<?> toRemove = null;
							for (TreeItem<?> item : rootItem.getChildren()) {
								if ((String) item.getValue() == name) {
									toRemove = item;
								}
							}
							rootItem.getChildren().remove(toRemove);
							project.deleteAction(name);
							try {
								data.save();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		moveupItem.setOnAction((event) -> {
			if (this.getSelectionModel().getSelectedItem() != null) {
				String name = this.getSelectionModel().getSelectedItem().getValue();
				if (name.equals(project.getName())) {
					ArrayList<Project> projects = data.getProjects();
					int loc = projects.indexOf(project);
					if (loc == 0)
						return;
					Collections.swap(projects, loc, loc - 1);
					// hacky - but got exceptions the cleaner way for some reason
					ArrayList<Node> list = new ArrayList<Node>(view.getChildren());
					Collections.swap(list, loc, loc - 1);
					view.getChildren().clear();
					view.getChildren().addAll(list);
					try {
						data.save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Action action = project.getAction(name);
					ArrayList<Action> actions = project.getActions();
					int loc = actions.indexOf(action);
					if (loc == 0)
						return;
					Collections.swap(actions, loc, loc - 1);
					Collections.swap(rootItem.getChildren(), loc + 1, loc);
					try {
						data.save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		movedownItem.setOnAction((event) -> {
			if (this.getSelectionModel().getSelectedItem() != null) {
				String name = this.getSelectionModel().getSelectedItem().getValue();
				if (name.equals(project.getName())) {
					ArrayList<Project> projects = data.getProjects();
					int loc = projects.indexOf(project);
					if (loc == projects.size() - 1)
						return;
					Collections.swap(projects, loc, loc + 1);
					// hacky - but got exceptions the cleaner way for some reason
					ArrayList<Node> list = new ArrayList<Node>(view.getChildren());
					Collections.swap(list, loc, loc + 1);
					view.getChildren().clear();
					view.getChildren().addAll(list);
					try {
						data.save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Action action = project.getAction(name);
					ArrayList<Action> actions = project.getActions();
					int loc = actions.indexOf(action);
					if (loc == actions.size() - 1)
						return;
					Collections.swap(actions, loc, loc + 1);
					Collections.swap(rootItem.getChildren(), loc + 1, loc + 2);
					try {
						data.save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		menu.getItems().addAll(addItem, editItem, moveupItem, movedownItem, deleteItem);
		setContextMenu(menu);

		setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			public TreeCell<String> call(TreeView<String> p) {
				return new ActionCellView();
			}
		});

		setShowRoot(false);

	}

}
