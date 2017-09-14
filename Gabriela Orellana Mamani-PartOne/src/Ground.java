import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Ground 
{
	private float square_width =10f;
	private float gridSize = 10f;
	private final double y = -1;
	private GLUquadric quadric;
	private GLU        glu;
	boolean click;
	
	public Ground()
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		click = false;
	}
	
	public void draw(GL gl, float[] colour) 
	{
			gl.glPushMatrix();
				changeGround(gl, colour);
				gl.glTranslated(-50, 0, -50);
				for(int x = 0; x < 100; x += gridSize)
				{
					for(int z = 0; z < 100; z += gridSize)
					{
						gl.glNormal3d(0, 1, 0);
						gl.glBegin(GL.GL_QUADS);
						gl.glColor3fv(colour, 0);
						gl.glVertex3d(x, y, z);
						gl.glVertex3d(x + square_width, y, z);
						gl.glVertex3d(x + square_width, y, z + square_width);
						gl.glVertex3d(x,  y,  z + square_width);
						gl.glFlush();
						gl.glEnd();
					}
				}
			gl.glPopMatrix();
		gl.glFlush();
		gl.glEnd();
	}
	
	public void changeGround(GL gl, float[] colour)
	{
		if(click == true)
		{
			gl.glPushMatrix();
				// Turn on wireframe mode
				gl.glPolygonMode(GL.GL_FRONT,GL. GL_LINE);
				gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE);
			gl.glPopMatrix();
			gl.glFlush();
			gl.glEnd();
		}
		
		if(click == false)
		{
			gl.glPushMatrix();
			// Turn off wireframe mode
				gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
				gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);
			gl.glPopMatrix();
			gl.glFlush();
			gl.glEnd();
		}
	}
}
