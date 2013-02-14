package com.jafj.simple3d;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.Matrix;

public class Camera extends SceneNode {

    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float roll = 0;
    private float yaw = 0;
    private float pitch = 0;

    public void position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void roll(float roll) {
        this.roll = roll;
    }

    public void pitch(float pitch) {
        this.pitch = pitch;
    }

    public void yaw(float yaw) {
        this.yaw = yaw;
    }

    public void update() {
        Matrix.setIdentityM(this.getModelMatrix(), 0);
        this.rotate(roll, 1, 0, 0);
        this.rotate(yaw, 0, 1, 0);
        this.rotate(pitch, 0, 0, 1);
        this.translate(x, y, z);
    }
}
