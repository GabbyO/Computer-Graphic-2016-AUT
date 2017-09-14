import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class Pymraid 
{
	private GLU glu;
    private GLUquadric quadric;
	private Texture pymraid;
	private boolean isTextured = true;
	private float whiteMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private float greyMaterial[]  = { 0.5f, 0.5f, 0.5f, 1.0f };
	
    public Pymraid()
    {
    	glu = new GLU();
    	quadric = glu.gluNewQuadric();
    	
    	 try 
		 {
			pymraid = TextureIO.newTexture(new File("./textures/bg-bricks.jpg"), true);
			pymraid.isUsingAutoMipmapGeneration();
			pymraid.bind();
			
		 } 
		 catch (IOException e)
		 {
			System.out.println("No file found ofr ground texture");
		}
    }
    
	public void draw(GL gl)
	{	
		//SET UP MATERIAL
		gl.glMaterialfv(GL.GL_BACK, GL.GL_AMBIENT,   greyMaterial,  0);
		gl.glMaterialfv(GL.GL_BACK, GL.GL_DIFFUSE,   whiteMaterial, 0);
		gl.glMaterialfv(GL.GL_BACK, GL.GL_SPECULAR,  greyMaterial,  0);
		gl.glMaterialf( GL.GL_BACK, GL.GL_SHININESS, 1.0f);
		
		if (isTextured)
		{
			//set up texture mapping
			pymraid.enable();
			
			 // set clamping parameters
			pymraid.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_MIRRORED_REPEAT);
			pymraid.setTexParameteri(GL.GL_TEXTURE_WRAP_S,  GL.GL_MIRRORED_REPEAT);
		}
		
		gl.glPushMatrix();
		 	gl.glScaled(30, 30, 30);
		 	gl.glRotated(270, 270, 0, 0);
		 	glu.gluQuadricTexture(quadric, true);
		 	glu.gluCylinder(quadric, 2, 0, 2, 4, 1);
		 gl.glPopMatrix();
		 
		 pymraid.disable();
	}
}
