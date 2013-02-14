package com.jafj.simple3d;

import javax.microedition.khronos.opengles.GL10;

public class Light implements Entity {
    private float position[] = { 0.0f, -10.0f, -10.0f, 1.0f };
    private float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private float ambient[] = {0.5f, 0.5f, 0.5f, 1.0f};

    @Override
    public void draw(GL10 gl, SceneNode node) {
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position, 0);

        gl.glEnable(GL10.GL_LIGHT0);
        gl.glEnable(GL10.GL_LIGHTING);
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z, float t) {
        this.position[0] = x;
        this.position[1] = y;
        this.position[2] = z;
        this.position[3] = t;
    }
}
