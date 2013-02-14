package com.jafj.simple3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Iterator;
import java.util.List;

public class Auxiliars {

    public static FloatBuffer createFloatBuffer(List<Float> values)
    {
        float[] ret = new float[values.size()];
        Iterator<Float> iterator = values.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().floatValue();
        }

        ByteBuffer vbb = ByteBuffer.allocateDirect(ret.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = vbb.asFloatBuffer();
        floatBuffer.put(ret);
        floatBuffer.position(0);

        return floatBuffer;
    }

    public static ShortBuffer createShortBuffer(List<Short> values)
    {
        short[] ret = new short[values.size()];
        Iterator<Short> iterator = values.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().shortValue();
        }

        ByteBuffer ibb = ByteBuffer.allocateDirect(ret.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = ibb.asShortBuffer();
        shortBuffer.put(ret);
        shortBuffer.position(0);

        return shortBuffer;	    
    }
}
