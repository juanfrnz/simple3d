package com.jafj.simple3d;

import java.io.IOException;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.res.AssetManager;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class EngineRenderer implements Renderer {
	
	private SceneNode root_node;
	private SceneUpdater scene_updater;
	private MatrixStack matrix_stack = new MatrixStack();
	private GL10 gl;
	
	public EngineRenderer() {
		matrix_stack.glLoadIdentity();
		scene_updater = null;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			
		if(scene_updater != null)
			scene_updater.run(root_node);
		root_node.draw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 90.0f, (float) width / (float) height, 1f, 300.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		gl.glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glDisable(GL10.GL_LIGHTING);
				
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
		
        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		this.setGL(gl);
	}
	
	public void setScene(SceneNode node) {
		root_node = node;
	}
	
	public void addSceneUpdater(SceneUpdater updater) {
		this.scene_updater = updater;
	}

	public GL10 getGL() {
		return gl;
	}

	private void setGL(GL10 gl) {
		this.gl = gl;
	}
}
