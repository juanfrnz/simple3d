package com.jafj.simple3d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import android.content.res.AssetManager;
import android.util.Log;

public class OBJLoader {
    public static final int VERTICES_PER_FACE = 3;

    private ArrayList<Float> vertices = new ArrayList<Float>();
    private ArrayList<Float> normals = new ArrayList<Float>();
    private ArrayList<Float> textureCoords = new ArrayList<Float>();
    private HashMap<String, ArrayList<FaceElement> > groups = new HashMap<String, ArrayList<FaceElement> >();

    public Mesh getMesh(String groupname) {
        Mesh mesh = new Mesh();
        ArrayList<Float> vertices = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();
        ArrayList<Float> texcoords = new ArrayList<Float>();
        ArrayList<Short> faces = new ArrayList<Short>();
        HashMap<Integer, Integer> index = new HashMap<Integer, Integer>();

        short currentIndex = 0;
        for(FaceElement face : groups.get(groupname)) {
            vertices.add(this.vertices.get(face.vertex * 3));
            vertices.add(this.vertices.get(face.vertex * 3 + 1));
            vertices.add(this.vertices.get(face.vertex * 3 + 2));

            Vector3 normal = new Vector3(this.normals.get(face.normal * 3),
                    this.normals.get(face.normal * 3 + 1),
                    this.normals.get(face.normal * 3 + 2));

            normals.add(this.normals.get(face.normal * 3));
            normals.add(this.normals.get(face.normal * 3 + 1));
            normals.add(this.normals.get(face.normal * 3 + 2));

            texcoords.add(this.textureCoords.get(face.texcoord * 3));
            texcoords.add(this.textureCoords.get(face.texcoord * 3 + 1));
            texcoords.add(this.textureCoords.get(face.texcoord * 3 + 2));

            faces.add(currentIndex);
            currentIndex++;
        }

        mesh.vertexBuffer = Auxiliars.createFloatBuffer(vertices);
        mesh.normalBuffer = Auxiliars.createFloatBuffer(normals);
        mesh.texcoordBuffer = Auxiliars.createFloatBuffer(texcoords);
        mesh.indexBuffer = Auxiliars.createShortBuffer(faces);

        return mesh;
    }

    public OBJLoader(AssetManager assetManager, String filename) throws IOException {
        //InputStream filestream = ClassLoader.getSystemResourceAsStream(filename);
        InputStream filestream = assetManager.open(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(filestream));

        String line = new String();
        String group = "";
        while((line = reader.readLine()) != null)
        {
            StringTokenizer tokenizer = new StringTokenizer(line);
            while(tokenizer.hasMoreElements())
            {
                String token = tokenizer.nextToken();

                if(token.equals("#")) {
                    // Skip comments
                    break;
                } else if(token.equals("v")) {
                    // Vertex
                    for(int i = 0; i < 3; i++) {
                        Float value = new Float(tokenizer.nextToken());
                        vertices.add(value);
                    }
                } else if(token.equals("vn")) {
                    // Normal
                    for(int i = 0; i < 3; i++) {
                        Float value = new Float(tokenizer.nextToken());
                        normals.add(value);
                    }
                } else if(token.equals("vt")) {
                    // Texcoord
                    for(int i = 0; i < 3; i++) {
                        Float value = new Float(tokenizer.nextToken());
                        textureCoords.add(value);
                    }
                } else if(token.equals("g")) {
                    group = tokenizer.nextToken();
                    groups.put(group, new ArrayList<FaceElement>());
                } else if(token.equals("f")) {
                    if(group == "")
                        throw new IOException();

                    for(int i = 0; i < VERTICES_PER_FACE; i++) {
                        StringTokenizer faceToken = new StringTokenizer(tokenizer.nextToken(), "/");
                        FaceElement faceElement = new FaceElement();
                        faceElement.vertex = new Integer(faceToken.nextToken()) - 1;
                        faceElement.texcoord = new Integer(faceToken.nextToken()) - 1;
                        faceElement.normal = new Integer(faceToken.nextToken()) - 1;

                        ArrayList<FaceElement> faces = groups.get(group);
                        faces.add(faceElement);
                    }
                }
            }
        }
    }
}

