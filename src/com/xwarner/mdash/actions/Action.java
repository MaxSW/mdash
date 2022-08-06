package com.xwarner.mdash.actions;

import org.json.JSONObject;

public class Action {

	private String name;
	private String icon;
	protected String type;

	public Action(String name, String icon) {
		this.name = name;
		this.icon = icon;
	}

	public Action(JSONObject obj) {
		this.name = obj.getString("name");
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
		return obj;
	}

	public void update(Action action) {
		this.name = action.name;
		this.icon = action.icon;
	}

	public String getType() {
		return type;
	}

}
