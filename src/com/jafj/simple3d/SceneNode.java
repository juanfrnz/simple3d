package com.jafj.simple3d;

import java.util.Vector;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.Matrix;

public class SceneNode {
    private float[] model_matrix = new float[MatrixStack.MATRIX_SIZE];
    private Vector<SceneNode> child_nodes = new Vector<SceneNode>();
    private Entity entity;
    private Camera camera = null;
    private Light light = null;

    public SceneNode() {
        Matrix.setIdentityM(model_matrix, 0);
    }

    public void addChildNode(SceneNode node) {
        child_nodes.add(node);
    }

    public void removeChildNode(SceneNode node) {
        child_nodes.remove(node);
    }

    public void setModelMatrix(float[] model_matrix) {
        this.model_matrix = model_matrix;
    }

    public float[] getModelMatrix() {
        return model_matrix;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void translate(float x, float y, float z) {
        Matrix.translateM(model_matrix, 0, x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(model_matrix, 0, angle, x, y, z);
    }

    public void scale(float x, float y, float z) {
        Matrix.scaleM(model_matrix, 0, x, y, z);
    }

    public void draw(GL10 gl) {
        gl.glPushMatrix();
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        if (light != null)
            light.draw(gl, this);

        if (camera != null) {
            camera.update();
            gl.glMultMatrixf(camera.getModelMatrix(), 0);
        }
        gl.glMultMatrixf(model_matrix, 0);

        if(entity != null)
            entity.draw(gl, this);

        for(SceneNode node : child_nodes) {
            node.draw(gl);
        }

        gl.glPopMatrix();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Light getLight() {
        return this.light;
    }
}
