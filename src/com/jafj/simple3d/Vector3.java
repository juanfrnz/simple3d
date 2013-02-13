package com.jafj.simple3d;

import android.util.Log;

public class Vector3
{
	public float x;
	public float y;
	public float z;
	
	public Vector3()
	{
		this.x = this.y = this.z = 0f;
	}
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 v)
	{
		this(v.x, v.y, v.z);
	}

	public float length()
	{
		Log.d("TEST", String.valueOf(x) + "," + String.valueOf(y) + ", " + String.valueOf(z));
		return (float)Math.sqrt( x*x + y*y + z*z);
	}

	public Vector3 normalize()
	{
		return new Vector3( x/length(), y/length(), z/length() );
	}
}