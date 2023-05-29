package com.xwarner.mdash.actions;

import java.util.HashMap;

import org.json.JSONObject;

public class Action {

	public static HashMap<String, Class<?>> actions = new HashMap<String, Class<?>>();

	static {
		actions.put("File", FileAction.class);
		actions.put("Folder", FolderAction.class);
		actions.put("Web", WebAction.class);
	}

	private String name;
	private String icon;
	protected String type;

	// everything that isn't common to all actions (name, icon, type)
	public HashMap<String, Attribute> attributes;

	public Action(String name, String icon) {
		this.name = name;
		this.icon = icon;
		this.attributes = new HashMap<String, Attribute>();
	}

	public Action(JSONObject obj) {
		this.name = obj.getString("name");
		this.attributes = new HashMap<String, Attribute>();
		if (obj.has("icon"))
			this.icon = obj.getString("icon");
	}

	public String getName() {
		return name;
	}

	public boolean hasIcon() {
		return this.icon != null && this.icon != "";
	}

	public String getIconHex() {
		return this.icon;
	}

	public void run() throws Exception {
		System.out.println("Running: " + this.name);
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("name", this.name);
		obj.put("icon", this.icon);
		obj.put("type", this.type);

		for (String name : attributes.keySet()) {
			obj.put(name, attributes.get(name).value);
		}

		return obj;
	}

	public void update(Action action) {
		this.name = action.name;
		this.icon = action.icon;

		// keep 1 and 3 merges
		for (String name : action.attributes.keySet()) {
			this.attributes.put(name, action.attributes.get(name));
		}
	}

	public String getType() {
		return type;
	}

}
