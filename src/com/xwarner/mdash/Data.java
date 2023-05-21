package com.xwarner.mdash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.image.Image;

public class Data {

	private static File file;

	private static HashMap<String, Object> params;
	private static ArrayList<Project> projects;
	public static HashMap<String, Image> emojis;
	public static ArrayList<String> emojiOrder;

	static {
		params = new HashMap<String, Object>();
		projects = new ArrayList<Project>();
		emojis = new HashMap<String, Image>();
		emojiOrder = new ArrayList<String>();
	}

	public static void load() throws IOException {
		String path = System.getenv("APPDATA") + File.separator + "mdash" + File.separator + "config.json";
		file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();

			JSONObject out = new JSONObject();
			out.accumulate("version", Main.version);
			out.append("projects", null);

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(out.toString());
			writer.close();

		} else {
			String input = Files.readString(file.toPath());
			JSONObject obj = new JSONObject(input);
			for (Object o : obj.getJSONArray("projects")) {
				if (o != null) {
					projects.add(new Project((JSONObject) o));
				}
			}
			for (String name : obj.keySet()) {
				if (!name.equals("projects")) {
					params.put(name, obj.get(name));
				}
			}
		}

		// load emojis
		String emojiPath = "file:" + System.getenv("APPDATA") + File.separator + "mdash" + File.separator + "twemoji"
				+ File.separator;
		path = System.getenv("APPDATA") + File.separator + "mdash" + File.separator + "emoji.json";
		File file = new File(path);
		String input = Files.readString(file.toPath());
		JSONArray dictionary = new JSONArray(input);
		for (int i = 0; i < dictionary.length(); i++) {
			JSONObject obj = dictionary.getJSONObject(i);
			String emoji = obj.getString("emoji");
			String code = Integer.toHexString(emoji.codePointAt(0));
			emojis.put(code, new Image(emojiPath + code + ".png"));
			emojiOrder.add(code);
		}
	}

	public static void save() throws IOException {
		JSONObject out = new JSONObject();
		out.accumulate("version", Main.version);
		JSONArray array = new JSONArray();
		for (Project project : projects) {
			array.put(project.toJSONObject());
		}
		out.put("projects", array);

		for (String name : params.keySet()) {
			out.put(name, params.get(name));
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(out.toString());
		writer.close();
	}

	public static void createProject(Project newProject) {
		projects.add(newProject);
	}

	public static ArrayList<Project> getProjects() {
		return projects;
	}

	public static void deleteProject(Project project) {
		projects.remove(project);
	}

	public static boolean hasParam(String name) {
		return params.containsKey(name);
	}

	public static int getIntParam(String name) {
		return (Integer) (params.get(name));
	}

	public static void setParam(String name, Object value) {
		params.put(name, value);
	}
}
