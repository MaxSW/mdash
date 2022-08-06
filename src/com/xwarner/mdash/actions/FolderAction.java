package com.xwarner.mdash.actions;

import java.awt.Desktop;
import java.io.File;

import org.json.JSONObject;

public class FolderAction extends Action {

	private String path;

	public FolderAction(JSONObject obj) {
		super(obj);
		if (obj.has("path"))
			this.path = obj.getString("path");
		this.type = "folder";
	}

	public FolderAction(String name, String icon) {
		super(name, icon);
		this.type = "folder";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = super.toJSONObject();
		obj.put("path", this.path);
		return obj;
	}

	public void update(Action action) {
		super.update(action);
		this.path = ((FolderAction) action).getPath();
	}

	public void run() throws Exception{
		File file = new File(this.path);
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
	}

}
