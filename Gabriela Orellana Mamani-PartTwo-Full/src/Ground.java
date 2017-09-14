import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class Ground 
{
	private float square_width = 15f;
	private final double y = -1;
	boolean click;
	private boolean isTextured = true;
	private Texture ground;
	
	private float whiteMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private float greyMaterial[]  = { 0.5f, 0.5f, 0.5f, 1.0f };
    
	public Ground()
	{
		try 
		{
			ground = TextureIO.newTexture(new File("./textures/ground.jpg"), true);
			ground.isUsingAutoMipmapGeneration();
			ground.bind();			
		} 
		catch (IOException e)
		{
			System.out.println("No file found ofr ground texture");
		}
	}
	
	public void draw(GL gl, float[] colour) 
	{
		gl.glEnable(GL.GL_NORMALIZE);
		
        // setup material
		gl.glMaterialfv(GL.GL_BACK, GL.GL_AMBIENT,   greyMaterial,  0);
		gl.glMaterialfv(GL.GL_BACK, GL.GL_DIFFUSE,   whiteMaterial, 0);
		gl.glMaterialfv(GL.GL_BACK, GL.GL_SPECULAR,  greyMaterial,  0);
		gl.glMaterialf( GL.GL_BACK, GL.GL_SHININESS, 1f);
		
		if (isTextured)
		{
			//set up texture mapping
			ground.enable();
			 // set clamping parameters
			ground.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			ground.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		}
		
		gl.glPushMatrix();
			gl.glTranslated(-1250, 0, -1250);
			
			for(int x = 0; x < 2500; x += square_width)
			{
				for(int z = 0; z < 2500; z += square_width)
				{
					gl.glBegin(GL.GL_QUADS);
					gl.glNormal3d(0, 1, 0);
					gl.glColor3fv(colour, 0);
					
					gl.glTexCoord2d(0, 0);
				    gl.glNormal3d(0, 1, 0);
					gl.glVertex3d(x, y, z);

					gl.glTexCoord2d(1,0);
					gl.glNormal3d(0, 1,0);
					gl.glVertex3d(x + square_width, y, z);
					
					gl.glTexCoord2d(1, 1);
					gl.glNormal3d(0, 1, 0);
					gl.glVertex3d(x + square_width, y, z + square_width);
					
					gl.glTexCoord2d(0, 1);
					gl.glNormal3d(0 , 1, 0);
					gl.glVertex3d(x,  y,  z + square_width);
				
					gl.glEnd();
				}
			}
		gl.glPopMatrix();
		
		ground.disable();
		
	}
	
	public void changeGround(GL gl, float[] colour)
	{
		if(click)
		{
			gl.glPushMatrix();
				// Turn on wireframe mode
				gl.glPolygonMode(GL.GL_FRONT,GL. GL_LINE);
				gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE);
			gl.glPopMatrix();
			
			gl.glFlush();
			gl.glEnd();
		}
		
		if(!click)
		{
			gl.glPushMatrix();
			// Turn off wireframe mode
				gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
				gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);
			gl.glPopMatrix();
			//gl.glFlush();
			//gl.glEnd();
		}
	}
}
