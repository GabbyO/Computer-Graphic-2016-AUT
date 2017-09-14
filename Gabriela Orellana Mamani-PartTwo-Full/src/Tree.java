import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Tree 
{
	private GLU glu;
    private GLUquadric quadric;
    protected float ambientBranch[] = {0.6f, 0.36f, 0.2f, 1};
    protected float diffuseBranch[] = { 0.01f, 0, 0, 1f};
	protected float specularBranch[] = {0.09f, 0.06f, 0.14f, 1f};
	protected float shininessBranch = 93;
	protected float ambientLeaf[] = {0.36f, 1f, 0.43f, 1};
	protected float diffuseLeaf[] = { 0.17f, 0.01f, 0, 1f};
	protected float specularLeaf[] = {0.09f, 0.06f, 0.14f, 1f};
	protected float shininessLeaf = 7;
	
    public Tree()
    {
    	glu = new GLU();
    	quadric = glu.gluNewQuadric();
    }

    public void draw(GL gl)
    {
    	gl.glShadeModel(GL.GL_SMOOTH); 
    	
	   	gl.glPushMatrix();	
			gl.glPushMatrix();
				gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientLeaf, 0);
		    	gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseLeaf, 0);
		    	gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularLeaf, 0);
		    	gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessLeaf);
			 	gl.glScaled(2, 2, 2);
			 	gl.glRotated(270, 270, 0, 0);
			 	gl.glTranslated(0, -2, 4);
			 	glu.gluCylinder(quadric, 1.5, 0, 5, 40, 20);
		 	gl.glPopMatrix();
			 	
		 	gl.glPushMatrix();
			 	gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientBranch, 0);
		    	gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseBranch, 0);
		    	gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularBranch, 0);
		    	gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessBranch);
			 	gl.glScaled(2, 2, 2);
			 	gl.glRotated(270, 270, 0, 0);
			 	gl.glTranslated(0, -2, 0);
			 	glu.gluQuadricTexture(quadric, true);
			 	glu.gluCylinder(quadric, 0.55, 0.55, 4, 40, 20);
			gl.glPopMatrix();
    	gl.glPopMatrix();
    }
}
