package com.cosmichorizon.taicho;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		 Taicho.setPlatformResolver(new DesktopResolver());
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Taicho";
		cfg.useGL20 = true;
		cfg.width = 1280;//1380; //orig 1280
		cfg.height = 720;//820; //orig 720
		
		new LwjglApplication(new Taicho(), cfg);
	}
}
