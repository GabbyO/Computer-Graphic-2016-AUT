import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;


public class Rotors 
{
	private GLU glu;
	private GLUquadric quadric;
	
	public void draw(GL gl)
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();
		
		gl.glPushMatrix();
		
			//Heli Rotor #1
		    gl.glPushMatrix();
		    	gl.glColor4d(1, 1, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(0, 35, 7);
		        gl.glRotated(-180, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.5, 0.5, 15, 35, 15);
	        gl.glPopMatrix();
	        
	      //Heli Rotor #2
		    gl.glPushMatrix();
		    	gl.glColor4d(1, 1, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(-8, 35, 0);
		        gl.glRotated(120, 120, 120, 120);
		        glu.gluCylinder(quadric, 0.5, 0.5, 15, 35, 15);
	        gl.glPopMatrix();
	    gl.glPopMatrix();
	}
}
