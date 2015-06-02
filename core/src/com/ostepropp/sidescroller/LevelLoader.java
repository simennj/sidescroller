package com.ostepropp.sidescroller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;

public class LevelLoader {

	public List<Hindrance> loadLevel(String path) {
		Scanner scanner = null;
		List<Hindrance> hindrances = new ArrayList<Hindrance>();
		try {
			scanner = new Scanner(Gdx.files.internal(path).file());
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(" ");
				if (line.length > 0)
					hindrances.add(new Hindrance(Float.parseFloat(line[1]),
							Float.parseFloat(line[2])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return hindrances;
	}
}
