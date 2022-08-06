package com.xwarner.mdash.actions;

import java.awt.Desktop;
import java.net.URI;

import org.json.JSONObject;

public class WebAction extends Action {

	private String url;

	public WebAction(JSONObject obj) {
		super(obj);
		if (obj.has("url"))
			this.url = obj.getString("url");
		this.type = "web";
	}

	public WebAction(String name, String icon) {
		super(name, icon);
		this.type = "web";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = super.toJSONObject();
		obj.put("url", this.url);
		return obj;
	}

	public void update(Action action) {
		super.update(action);
		this.url = ((WebAction) action).getUrl();
	}

	public void run() throws Exception {
		URI uri = new URI(this.url);
		Desktop desktop = Desktop.getDesktop();
		desktop.browse(uri);
	}

}
