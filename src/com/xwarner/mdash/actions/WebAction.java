package com.xwarner.mdash.actions;

import java.awt.Desktop;
import java.net.URI;

import org.json.JSONObject;

public class WebAction extends Action {

	public WebAction(JSONObject obj) {
		super(obj);
		if (obj.has("url"))
			this.attributes.put("url", new Attribute("URL", obj.getString("url")));
		else
			this.attributes.put("url", new Attribute("URL"));
		this.type = "web";
	}

	public WebAction(String name, String icon) {
		super(name, icon);
		this.attributes.put("url", new Attribute("URL"));
		this.type = "web";
	}

	public String getUrl() {
		return (String) this.attributes.get("url").value;
	}

	public void setUrl(String url) {
		this.attributes.get("url").value = url;
	}

	public void run() throws Exception {
		URI uri = new URI((String) this.attributes.get("url").value);
		Desktop desktop = Desktop.getDesktop();
		desktop.browse(uri);
	}

}
