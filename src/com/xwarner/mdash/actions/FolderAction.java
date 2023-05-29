package com.xwarner.mdash.actions;

import java.awt.Desktop;
import java.io.File;

import org.json.JSONObject;

public class FolderAction extends Action {

	public FolderAction(JSONObject obj) {
		super(obj);
		if (obj.has("path"))
			this.attributes.put("path", new Attribute("Path", obj.getString("path")));
		else
			this.attributes.put("path", new Attribute("Path"));
		this.type = "folder";
	}

	public FolderAction(String name, String icon) {
		super(name, icon);
		this.attributes.put("path", new Attribute("Path"));
		this.type = "folder";
	}

	public String getPath() {
		return (String) this.attributes.get("path").value;
	}

	public void setPath(String path) {
		this.attributes.get("path").value = path;
	}

	public void run() throws Exception {
		File file = new File((String) this.attributes.get("path").value);
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
	}
}
