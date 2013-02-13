package com.jafj.simple3d;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

public class Material {
	private Texture texture = null;

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void enable(GL10 gl) {
		if(texture != null) {
			try {
				texture.enable(gl);
				
			} catch (IOException e) {
				e.printStackTrace();
				texture = null;
			}
		}
	}
	
	public void disable(GL10 gl) {
		if(texture != null)
			texture.disable(gl);
	}
}
