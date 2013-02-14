package com.jafj.simple3d;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLUtils;
import android.util.Log;

public class Texture {
    private int textureID;
    private GL10 gl;
    private Boolean loaded;
    private AssetManager assetManager;
    private String filename;

    public Texture(AssetManager assetManager, String filename) throws IOException
    {
        this.loaded = false;
        this.assetManager = assetManager;
        this.filename = filename;
    }

    @SuppressLint("NewApi")
    private void load(GL10 gl) throws IOException
    {
        this.gl = gl;
        // In which ID will we be storing this texture?
        textureID = newTextureID(gl);

        // We need to flip the textures vertically:
        Matrix flip = new Matrix();
        flip.postScale(1f, -1f);

        // This will tell the BitmapFactory to not scale based on the device's pixel density:
        // (Thanks to Matthew Marshall for this bit)
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        // Load up, and flip the texture:
        InputStream filestream = assetManager.open(filename);
        Bitmap temp = BitmapFactory.decodeStream(filestream);
        Bitmap bmp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), flip, false);
        temp.recycle();

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);

        // Set all of our texture parameters:
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);

        bmp.recycle();
        this.loaded = true;
    }

    public void enable(GL10 gl) throws IOException
    {
        if(!loaded)
            load(gl);

        //gl.glCullFace(GL10.GL_FRONT_AND_BACK);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
    }

    public void disable(GL10 gl)
    {
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    private static int newTextureID(GL10 gl) {
        int[] temp = new int[1];
        gl.glGenTextures(1, temp, 0);
        return temp[0];        
    }
}
