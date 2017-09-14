import javax.media.opengl.GL;
//import javax.media.opengl.GLAutoDrawable; NOT USED so DELETE 
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Origin 
{
	// quadric for sphere
    private GLUquadric quadric;
    private GLU glu;

    //DRAW
	public void draw(GL gl)
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		
		gl.glPushMatrix();
	        gl.glColor4d(1, 1, 1, 1);
	        glu.gluSphere(quadric, 0.1, 20, 20);
	        gl.glEnable(GL.GL_LIGHTING);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
			gl.glLineWidth(3.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glColor3d(1, 0, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(50, 0, 0);
			gl.glColor3d(0, 1, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 50, 0);
			gl.glColor3d(0, 0, 1);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(0, 0, 50);
		gl.glPopMatrix();
		
		//END
		gl.glEnd();
	}
}
