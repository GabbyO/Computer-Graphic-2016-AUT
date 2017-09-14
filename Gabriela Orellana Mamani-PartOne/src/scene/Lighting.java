package scene;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Basic Lighting setup for scene
 * Consists of global ambient lighting and 
 * @author Jacqueline Whalley
 *
 */
public class Lighting {
	public GLU glu;
	public GL gl;
	public GLUquadric quadric;
	float globalAmbient[] = { 0.4f, 0.4f, 0.4f, 1 }; 	// global light properties
	
	public float[] lightPosition = { 5.0f, 5.0f, 5.0f, 1.0f }; //point light
	public float[] ambientLight = { 0, 0, 0, 1 };
	public float[] diffuseLight = { 1, 1, 1, 1 };
	public float[] specularLight = { 1, 1, 1, 1 };

	/**
	 * Creates and enables scene's lights using default light0 position
	 * @param gl
	 */
	public Lighting(GL gl) {
		glu = new GLU();
		this.gl = gl;
		// set the global ambient light level
	    gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, globalAmbient, 0);
	    
		// setup the light 0 properties
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specularLight, 0);
		
		// normalize the normals
	    gl.glEnable(GL.GL_NORMALIZE);
	    
	   // position the light
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0);
	}

	/**
	 * Creates and enables scene's light and specifies a position for light 0
	 * @param position light0's position
	 * @param gl
	 */
	public Lighting(float[] position, GL gl) {
		//TO DO
	}
	
	/**
	 * enable both global ambient and light0
	 */
	public void enable(){
		// enable lighting
	    gl.glEnable(GL.GL_LIGHTING);
	    // enable light 0
	    gl.glEnable(GL.GL_LIGHT0);
	}
	
	/**
	 * disables light0 only
	 * global ambient light remains enabled
	 */
	public void disable(){
		gl.glDisable(GL.GL_LIGHT0); //keep ambient light
	}

	/**
	 * Function to aid rendering and debugging
	 * draws a small white sphere for the light source
	 * draws a line from the light position through the XZ
	 * plane - helps once other objects are in scene.
	 * helps locate light0 in the scene
	 * @param gl
	 */
	public void draw(GL gl) {
		
		quadric = glu.gluNewQuadric();
		gl.glPushMatrix();
		gl.glDisable(GL.GL_LIGHTING);
		gl.glColor4d(1, 1, 1, 1);
		gl.glTranslated(lightPosition[0], lightPosition[1], lightPosition[2]);
		glu.gluSphere(quadric, 0.1, 16, 8); // draw sphere translated to light position
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0, 50, 0); 
			gl.glVertex3f(0, -100, 0); 
		gl.glEnd();
		
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPopMatrix();
	}
}