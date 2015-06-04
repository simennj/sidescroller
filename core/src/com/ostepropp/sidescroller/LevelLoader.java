package com.ostepropp.sidescroller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;

public class LevelLoader {

	private List<List<Hindrance>> segments = new ArrayList<List<Hindrance>>();;
	private List<Float> segmentLength = new ArrayList<Float>();
	private String currentPath;

	public LevelLoader(String path) {
		loadLevel(path);
	}

	public void loadLevel() {
		Scanner scanner = null;
		try {
			List<Hindrance> hindrances = new ArrayList<Hindrance>();
			scanner = new Scanner(Gdx.files.internal(currentPath).file());
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(" ");
				if (line.length > 1)
					hindrances.add(new Hindrance(Float.parseFloat(line[1]),
							Float.parseFloat(line[2]), Float
									.parseFloat(line[3]), Float
									.parseFloat(line[4])));
				else {
					segments.add(hindrances);
					segmentLength.add(Float.parseFloat(line[0]));
					hindrances = new ArrayList<Hindrance>();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
	
	public void loadLevel(String path) {
		currentPath = path;
		loadLevel();
	}
	
	public void saveLevel(List<Hindrance> hindrances, int index) {
		try {
			Writer writer = new FileWriter(Gdx.files
					.internal("levels/test").file());
			for (Hindrance hindrance : hindrances) {
				writer.write(hindrance + "\n");
			}
			double asdf = hindrances.stream()
					.mapToDouble(h -> h.x + h.width).max().orElse(1280);
			writer.write(Double.toString(asdf));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Hindrance> getSegment(int i) {
		List<Hindrance> tmp = new ArrayList<Hindrance>(segments.get(i).size());
		for (Hindrance hindrance : segments.get(i)) {
			tmp.add((Hindrance) hindrance.clone());
		}
		return tmp;
	}

	public float getSegmentLength(int i) {
		return segmentLength.get(i);
	}

	public int totalSegments() {
		return segments.size();
	}

	public String[] getSegmentsList() {
		String[] result = new String[segments.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = "Segment: "+(i+1);
		}
		return result;
	}
}
