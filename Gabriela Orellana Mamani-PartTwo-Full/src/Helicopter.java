import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import scene.Camera;
import scene.Lighting;

public class Helicopter 
{
	// create the quadric
	private GLU glu;
	private GLUquadric quadric; //

	public float loc[] = {0,0,0}; //location of the helicopter
	private float distance = 0.7f; //distance of the helicopter to camera
	public double heading = 0; //heading orignal for camera
	private Rotors rotors = new Rotors(); //passed on the Rotors
	private double speedRotorX; //x: speed for the Rotors
	
	private boolean forward;
	private boolean backward;
	private boolean right;
	private boolean left;
	private boolean landing;
	
	protected float[] ambientHeli = {0.62f,0.55f,0f,1f};
	protected float[] diffuseHeli= {0f,0.08f,0.34f,1f};
	protected float[] specularHeli= {0.43f,0.6f,0.52f,1f};

	protected float[] ambientRotor = {0.9f,0.47f,0f,1f};
	protected float[] diffuseRotor = {0f,0.38f,0.34f,1f};
	protected float[] specularRotor = {0.63f,0.6f,0.52f,1f};
	
	protected float shininessHeli, shininessRotor;
	
	public Helicopter()
	{
		forward = false;
		backward = false;
		right = false;
		left = false;
		landing = false;
	}
	
	//Draw the Helicopter Parts
	public void draw(GL gl)
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();

		gl.glPushMatrix();	
		    gl.glTranslated(loc[0], loc[1]+1, loc[2]);
		    gl.glScaled(0.5, 0.5, 0.5);
		    gl.glRotated(heading, 0, 1, 0);

		    movementRotate(gl);

		    //BODY PARTS
		    gl.glPushMatrix();
			    
				shininessHeli = 1.8f;
				shininessRotor = 1.30f;
				
			    //Helicopter Body
				gl.glPushMatrix();
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
					
			        gl.glScaled(1, 0.7, 1);
			        glu.gluSphere(quadric, 3, 20, 20);     
		        gl.glPopMatrix();
		       
		        //Helicopter back part #1
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
					
			        gl.glRotated(-90, 0, 1, 0);
			        glu.gluCylinder(quadric, 1, 0, 7, 50, 50);
		        gl.glPopMatrix();
		        
		        //Helicopter back part #2
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(-12, 1, 0);
			        gl.glRotated(-90, 1, 0, 0);
			        glu.gluCylinder(quadric, 0.5, 0.5, 10, 40, 10);
		        gl.glPopMatrix();
		        
		        //Rotor Rod
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
					
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(0, 21, 0);
			        gl.glRotated(-90, 1, 0, 0);
			        glu.gluCylinder(quadric, 0.5, 0.5, 15, 35, 15);
			    gl.glPopMatrix();        
			    
		        //Landing Rods #1
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(-1, -35, 2);
			        gl.glRotated(-90, 1, 0, 0);
			        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 155);
		        gl.glPopMatrix();
		        
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(2, -35, 2);
			        gl.glRotated(-90, 1, 0, 0);
			        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 25);
			    gl.glPopMatrix();
			    
			    //Landing Part #1
			    gl.glPushMatrix();
				    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientRotor, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseRotor, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularRotor, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessRotor);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(-4, -35, 2);
			        gl.glRotated(120, 120, 120, 120);
			        glu.gluCylinder(quadric, 0.5, 0.5, 10, 35, 15);
			    gl.glPopMatrix();
	
			  //Landing Rods #2
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(-1, -35, -2);
			        gl.glRotated(-90, 1, 0, 0);
			        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 155);
		        gl.glPopMatrix();
		        
		        gl.glPushMatrix();
			        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseHeli, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularHeli, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessHeli);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(2, -35, -2);
			        gl.glRotated(-90, 1, 0, 0);
			        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 25);
			    gl.glPopMatrix();
			    
			    //Landing Part #2
			    gl.glPushMatrix();
				    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientRotor, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseRotor, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularRotor, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessRotor);
			        gl.glScaled(0.5, 0.1, 0.5);
			        gl.glTranslated(-4, -35, -2);
			        gl.glRotated(120, 120, 120, 120);
			        glu.gluCylinder(quadric, 0.5, 0.5, 10, 35, 15);
			    gl.glPopMatrix();
	
			    //ROTORS
			    gl.glPushMatrix();
				    if(loc[1] > 1 && loc[1] < 4)
					{
					    xSpeed();
					    gl.glRotated(this.speedRotorX, 0, 1, 0);
					} 
					 else if(loc[1] > 4 )
					{
						xSpeedIncrease();
						gl.glRotated(this.speedRotorX, 0, 1, 0);
					}
				    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambientRotor, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diffuseRotor, 0);
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specularRotor, 0);
					gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, shininessRotor);
		    		rotors.draw(gl);
		    	gl.glPopMatrix();
	        	 
	        //BODY PARTS FINISHED
		    gl.glPopMatrix();
		    
		gl.glPopMatrix();
	}

	private void movementRotate(GL gl) 
	{
		//MOVEMENTS
	    if(this.right)
	    {
	    	gl.glRotated(10, 1, 0, 0);
	    } 
	    
	    if(this.left)
	    {
	    	gl.glRotated(-10, 1, 0, 0);
	    }
	    
	    if(this.forward)
	    {
	    	gl.glRotated(10, 1, 0, -60);
	    }
	    
	    if(this.backward)
	    {
	    	gl.glRotated(10, 1, 0, 60);
	    }
	    
	    if(this.landing)
		{
	    	gl.glRotated(0, 0, 0, 0);
	    }
	}

	//LIGHT
	public void lighning(GL gl)
	{
			float diffuse[] = {1, 1, 1, 1};
			float ambient[] = {0, 0, 0, 1};
			float specular[] = {1, 1, 1, 1};
			
			// enable light 1
			gl.glEnable(GL.GL_LIGHTING);
	        gl.glEnable(GL.GL_LIGHT1);
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, ambient, 0);
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuse, 0);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specular, 0);

			float position[] = {this.loc[0], this.loc[1]-0.5f, this.loc[2], 1};
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, position, 0);

			float dir[] = {0, -0.5f, 0};
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPOT_DIRECTION, dir, 0);
			gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 65);
	}
	
	
	//MOVEMENTS
	//Flying straight up
	public void moveUp()
	{
			this.loc[1] += distance;
	}
	
	//Flying straight down
	public void moveDown()
	{
		if(loc[1] >= 1)
		{
			this.landing = true;
			this.right = false;
			this.left = false;
			this.forward = false;
			this.backward = false;
			
			this.loc[1] -= distance;
		}
	}
	
	//Flying straight forward
	public void moveForward()
	{
		if(loc[1] > 1)
		{
			this.forward = true;
			this.backward = false;
			this.left = false;
			this.right = false;
			
			this.loc[0] -= Math.sin(Math.toRadians(heading-90))*distance; 
			this.loc[2] -= Math.cos(Math.toRadians(heading-90))*distance;
		}
	}
	
	//Flying straight backward
	public void moveBackward(){
		if(loc[1] > 1)
		{
			this.backward = true;
			this.forward = false;
			this.left = false;
			this.right = false;
			
			this.loc[0] += Math.sin(Math.toRadians(heading-90))*distance; 
			this.loc[2] += Math.cos(Math.toRadians(heading-90))*distance;
		}
	}
	
	//Flying move left sideway
	public void strafeLeft()
	{
		if(loc[1] > 1)
		{
			this.left = true;
			this.right = false;
			this.backward = false;
			this.forward = false;
			
			this.loc[0] -= Math.sin(Math.toRadians(heading))*distance; 
			this.loc[2] -= Math.cos(Math.toRadians(heading))*distance;
		}
	}
	
	//Flying move right sideway
	public void strafeRight()
	{
		if(loc[1] > 1)
		{
			this.right = true;
			this.left = false;
			this.backward = false;
			this.forward = false;
			
			this.loc[0] += Math.sin(Math.toRadians(heading))*distance; 
			this.loc[2] += Math.cos(Math.toRadians(heading))*distance;
		}
	}
	
	//Turn the camera right
	public void turnRight()
	{
		if(loc[1] > 1)
			heading -= 2;
	}
	
	//Turn the camera left
	public void turnLeft()
	{
		if(loc[1] >  1)
			heading += 2; 
	}
	
	//x speed for Rotors when taking off
	public double xSpeed()
	{
		for(int i = 0; i <= 10; i ++)
			this.speedRotorX += 10;
		return this.speedRotorX;
	}
	
	//x speed for Rotors increasing when it got higher
	private double xSpeedIncrease() 
	{
		for(int i = 0; i <= 25; i++)
			this.speedRotorX += 75;
		return this.speedRotorX;
	}
	
	//MOVEMENTS FINISHED
	
}
