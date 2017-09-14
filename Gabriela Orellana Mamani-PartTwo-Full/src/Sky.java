import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Sky 
{
	private Texture sky;
	private boolean isTextured = true;
	private GLU glu;
	private GLUquadric quadric;
	
	private float whiteMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private float greyMaterial[]  = { 0.5f, 0.5f, 0.5f, 1.0f };
	
	public Sky()
	{
		 try 
		 {
			sky = TextureIO.newTexture(new File("./textures/skybetter.jpg"), true);
			sky.isUsingAutoMipmapGeneration();
			sky.bind();
			
		 } 
		 catch (IOException e)
		 {
			System.out.println("No file found ofr ground texture");
		}
	}
	
	public void draw(GL gl, float[] colour)
	{		
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		
		// setup material
		gl.glMaterialfv(GL.GL_BACK, GL.GL_AMBIENT,   greyMaterial,  0);
		gl.glMaterialfv(GL.GL_BACK, GL.GL_DIFFUSE,   whiteMaterial, 0);
		gl.glMaterialfv(GL.GL_BACK, GL.GL_SPECULAR,  greyMaterial,  0);
		gl.glMaterialf( GL.GL_BACK, GL.GL_SHININESS, 1.0f);
		
		if (isTextured)
		{
			//set up texture mapping
			sky.enable();
			
			 // set clamping parameters
			sky.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_MIRRORED_REPEAT);
			sky.setTexParameteri(GL.GL_TEXTURE_WRAP_S,  GL.GL_MIRRORED_REPEAT);
		}
		
		gl.glPushMatrix();
			glu.gluQuadricTexture(quadric, true);
			glu.gluSphere(quadric, 1.0, 40, 20);
	    gl.glPopMatrix();
	    
	    sky.disable();
    }
}
