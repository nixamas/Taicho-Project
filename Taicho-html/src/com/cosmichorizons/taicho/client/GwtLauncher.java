package com.cosmichorizons.taicho.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.cosmichorizons.taicho.Taicho;

public class GwtLauncher extends GwtApplication {
	public GwtLauncher() {
		super();
		Taicho.setPlatformResolver(new WebGLResolver());
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(960, 540);
		return cfg;
	}

	public ApplicationListener getApplicationListener () {
		return new Taicho();
	}
	
	
//	@Override
//	public GwtApplicationConfiguration getConfig () {
//		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
//		return cfg;
//	}
//
//	@Override
//	public ApplicationListener getApplicationListener () {
//		return new Taicho();
//	}
}