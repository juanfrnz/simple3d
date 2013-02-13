package com.jafj.simple3d;

import javax.microedition.khronos.opengles.GL10;

public interface Entity {
	public void draw(GL10 gl, SceneNode node);
}
