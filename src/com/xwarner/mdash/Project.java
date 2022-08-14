package com.xwarner.mdash;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.xwarner.mdash.actions.Action;
import com.xwarner.mdash.actions.FileAction;
import com.xwarner.mdash.actions.FolderAction;
import com.xwarner.mdash.actions.WebAction;

public class Project {

	private String name;
	private ArrayList<Action> actions;
	private String iconHex;

	public Project(JSONObject obj) {
		this.name = obj.getString("name");
		if (obj.has("icon"))
			this.iconHex = obj.getString("icon");
		this.actions = new ArrayList<Action>();
		for (Object o : obj.getJSONArray("actions")) {
			JSONObject jobj = (JSONObject) o;
			if (jobj.getString("type").equals("folder")) {
				this.actions.add(new FolderAction(jobj));
			} else if (jobj.getString("type").equals("web")) {
				this.actions.add(new WebAction(jobj));
			} else if (jobj.getString("type").equals("file")) {
				this.actions.add(new FileAction(jobj));
			}
		}
	}

	public Project(String name) {
		this.name = name;
		this.actions = new ArrayList<Action>();
	}

	public ArrayList<Action> getActions() {
		return this.actions;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("name", this.name);
		obj.put("icon", this.iconHex);
		JSONArray array = new JSONArray();
		for (Action action : this.actions) {
			array.put(action.toJSONObject());
		}
		obj.put("actions", array);
		return obj;
	}

	public Action getAction(String name) {
		for (Action action : actions) {
			if (action.getName() == name)
				return action;
		}
		return null;
	}

	public void updateAction(String name, Action newAction) {
		for (Action action : actions) {
			if (action.getName() == name)
				action.update(newAction);
		}
	}

	public void deleteAction(String name) {
		Action toDelete = null;
		for (Action action : actions) {
			if (action.getName() == name)
				toDelete = action;
		}
		actions.remove(toDelete);
	}

	public String getIconHex() {
		return iconHex;
	}

	public void setIconHex(String iconHex) {
		this.iconHex = iconHex;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void update(Project project) {
		this.name = project.getName();
		this.iconHex = project.getIconHex();
	}

	public boolean hasIcon() {
		return this.iconHex != null && this.iconHex != "";
	}

}
