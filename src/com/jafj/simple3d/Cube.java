package com.jafj.simple3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube implements Entity {
	private float vertices[] = {
		      -1.0f,  1.0f, 0.0f,
		      -1.0f, -1.0f, 0.0f,
		       1.0f, -1.0f, 0.0f,
		       1.0f,  1.0f, 0.0f,
		       -1.0f,  1.0f, 1.0f,
		       -1.0f, -1.0f, 1.0f,
		       1.0f, -1.0f, 1.0f,
		       1.0f,  1.0f, 1.0f
	};
	
	private float normals[] = {
		      0.0f, 0.0f, 1.0f,
		      0.0f, 0.0f, 1.0f,
		      0.0f, 0.0f, 1.0f,
		      0.0f, 0.0f, 1.0f,
		      
		      0.0f, 0.0f, -1.0f,
		      0.0f, 0.0f, -1.0f,
		      0.0f, 0.0f, -1.0f,
		      0.0f, 0.0f, -1.0f
	};
	
	short indices[] = { 0, 4, 5,
						0, 5, 1,
						1, 5, 6,
						1, 6, 2,
						2, 6, 7,
						2, 7, 3,
						3, 7, 4,
						3, 4, 0,	
						4, 7, 6,
						4, 6, 5,
						3, 0, 1,
						3, 1, 2, };
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private ShortBuffer indexBuffer;
	
	public Cube() {
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = vertexByteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		ByteBuffer normalByteBuffer = ByteBuffer.allocateDirect(normals.length * 4);
		normalByteBuffer.order(ByteOrder.nativeOrder());
		normalBuffer = normalByteBuffer.asFloatBuffer();
		normalBuffer.put(normals);
		normalBuffer.position(0);

		ByteBuffer indexByteBuffer = ByteBuffer.allocateDirect(indices.length * 2);
		indexByteBuffer.order(ByteOrder.nativeOrder());
		indexBuffer = indexByteBuffer.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}
	
	@Override
	public void draw(GL10 gl, SceneNode node) {		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);	
		
		node.rotate(1.0f, 1.0f, 1.0f, 0.0f);
	}
}
