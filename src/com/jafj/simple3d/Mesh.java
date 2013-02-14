package com.jafj.simple3d;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Mesh implements Entity {
    public FloatBuffer vertexBuffer;
    public FloatBuffer normalBuffer;
    public FloatBuffer texcoordBuffer;
    public ShortBuffer indexBuffer;

    private Material material = null;

    @Override
    public void draw(GL10 gl, SceneNode node) {
        if (material != null)
            material.enable(gl);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glTexCoordPointer(3, GL10.GL_FLOAT, 0, texcoordBuffer);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.capacity(), GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        if (material != null)
            material.disable(gl);
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
