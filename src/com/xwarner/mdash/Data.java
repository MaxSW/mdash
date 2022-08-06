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

import javafx.scene.layout.HBox;

public class Data {

	private File file;

	private HashMap<String, Object> params;
	private ArrayList<Project> projects;

	public Data() {
		params = new HashMap<String, Object>();
		projects = new ArrayList<Project>();
	}

	public void load() throws IOException {
		String path = System.getenv("APPDATA") + File.separator + "mdash" + File.separator + "config.json";
		file = new File(path);
		if (!file.exists()) {
			System.out.println("Doesn't exist");
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
		}
	}

	public void save() throws IOException {
		JSONObject out = new JSONObject();
		out.accumulate("version", Main.version);
		JSONArray array = new JSONArray();
		for (Project project : projects) {
			array.put(project.toJSONObject());
		}
		out.put("projects", array);

		BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
		writer.write(out.toString());
		writer.close();
	}

	public HBox createTree() {
		HBox box = new HBox();
		return box;
	}

	public void createProject(Project newProject) {
		projects.add(newProject);
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void deleteProject(Project project) {
		projects.remove(project);
	}

}
