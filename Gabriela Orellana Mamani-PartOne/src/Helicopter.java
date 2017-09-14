import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Helicopter 
{
	// create the quadric
	private GLU glu;
	private GLUquadric quadric; //

	public float loc[] = {0,1,0}; //location of the helicopter
	private float distance = 1; //distance of the helicopter to camera
	public double heading = 0; //heading orignal for camera
	private Rotors rotors = new Rotors(); //passed on the Rotors
	private double x; //x: speed for the Rotors
	
	//Draw the Helicopter Parts
	public void draw(GL gl)
	{
		glu = new GLU();
		quadric = glu.gluNewQuadric();

		gl.glPushMatrix();
		
		    gl.glTranslated(loc[0], loc[1], loc[2]);
		    gl.glScaled(0.9, 0.9, 0.9);
		    gl.glTranslated(0, 2, 0);
		    gl.glRotated(heading, 0,1,0); //JW added
		    
		    //Helicopter Body
			gl.glPushMatrix();
		        gl.glColor4d(0.2, 0.2, 1, 1);
		        gl.glScaled(1, 0.7, 1);
		        glu.gluSphere(quadric, 3, 20, 20);
	        gl.glPopMatrix();
	        
	        //Helicopter back part #1
	        gl.glPushMatrix();
		        gl.glColor4d(1, 0.2, 0, 1);
		        gl.glRotated(-90, 0, 1, 0);
		        glu.gluCylinder(quadric, 1, 0, 7, 50, 50);
	        gl.glPopMatrix();
	        
	        //Helicopter back part #2
	        gl.glPushMatrix();
	        	gl.glColor4d(1, 0.2, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(-12, 1, 0);
		        gl.glRotated(-90, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.5, 0.5, 10, 40, 10);
	        gl.glPopMatrix();
	        
	        //Rotor Rod
	        gl.glPushMatrix();
	        	gl.glColor4d(1, 0.2, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(0, 21, 0);
		        gl.glRotated(-90, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.5, 0.5, 15, 35, 15);
		    gl.glPopMatrix();        
		    
	        //Landing Rods #1
	        gl.glPushMatrix();
		    	gl.glColor4d(1, 0.2, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(-1, -35, 2);
		        gl.glRotated(-90, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 155);
	        gl.glPopMatrix();
	        
	        gl.glPushMatrix();
		    	gl.glColor4d(1, 0.2, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(2, -35, 2);
		        gl.glRotated(-90, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 25);
		    gl.glPopMatrix();
		    
		    //Landing Part #1
		    gl.glPushMatrix();
		    	gl.glColor4d(0, 0, 0.99, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(-4, -35, 2);
		        gl.glRotated(120, 120, 120, 120);
		        glu.gluCylinder(quadric, 0.5, 0.5, 10, 35, 15);
		    gl.glPopMatrix();

		  //Landing Rods #2
	        gl.glPushMatrix();
		    	gl.glColor4d(1, 0.2, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(-1, -35, -2);
		        gl.glRotated(-90, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 155);
	        gl.glPopMatrix();
	        
	        gl.glPushMatrix();
		    	gl.glColor4d(1, 0.2, 0, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(2, -35, -2);
		        gl.glRotated(-90, 1, 0, 0);
		        glu.gluCylinder(quadric, 0.3, 0.3, 20, 15, 25);
		    gl.glPopMatrix();
		    
		    //Landing Part #2
		    gl.glPushMatrix();
		    	gl.glColor4d(0, 0, 0.99, 1);
		        gl.glScaled(0.5, 0.1, 0.5);
		        gl.glTranslated(-4, -35, -2);
		        gl.glRotated(120, 120, 120, 120);
		        glu.gluCylinder(quadric, 0.5, 0.5, 10, 35, 15);
		    gl.glPopMatrix();

		    gl.glPushMatrix();
			    if(loc[1] > 1 && loc[1] <10)
				{
				    	xSpeed();
				    	gl.glRotated(x, 0, 1, 0);
				} else if (loc[1] == 1)
				{
					gl.glRotated(0, 0, 1, 0);
				} else if(loc[1] > 10 )
				{
					xSpeedIncrease();
					gl.glRotated(x, 0, 1, 0);
				}
	    		rotors.draw(gl);
	    	gl.glPopMatrix();
	    	
	    gl.glPopMatrix();
	    
	    gl.glFlush();
		gl.glEnd();
	}
	
	//x speed for Rotors when taking off
	public double xSpeed()
	{
		this.x += 15;
		return this.x;
	}
	
	//x speed for Rotors increasing when it got higher
	private double xSpeedIncrease() 
	{
		this.x += 45;
		return this.x;
	}

	//Flying straight up
	public void moveUp()
	{
			this.loc[1] += distance;
	}
	
	//Flying straight down
	public void moveDown()
	{
		if(loc[1] >= 2)
			this.loc[1] -= distance;
	}
	
	//beacuse you have changed other things your forward and back are out by 90 degrees
	public void moveForward()
	{
		if(loc[1] > 1)
		{
			this.loc[0] -= Math.sin(Math.toRadians(heading-90))*distance; 
			this.loc[2] -= Math.cos(Math.toRadians(heading-90))*distance;
		}
	}
	
	//Flying straight backward
	public void moveBackward(){
		if(loc[1] > 1)
		{
			this.loc[0] += Math.sin(Math.toRadians(heading-90))*distance; 
			this.loc[2] += Math.cos(Math.toRadians(heading-90))*distance;
		}
	}
	
	//Flying move left sideway
	public void strafeLeft(){
		//Following the camera:
		if(loc[1] > 1)
		{
			this.loc[0] -= Math.sin(Math.toRadians(heading))*distance; 
			this.loc[2] -= Math.cos(Math.toRadians(heading))*distance;
		}
	}
	
	//Flying move right sideway
	public void strafeRight()
	{
		//Following the camera:
		if(loc[1] > 1)
		{
			this.loc[0] += Math.sin(Math.toRadians(heading))*distance; 
			this.loc[2] += Math.cos(Math.toRadians(heading))*distance;
		}
	}
	
	//Turn the camera right
	public void turnRight()
	{
		heading += 2; //JW
		//Following the camera
		//heading += Math.sin(Math.toRadians(15)); 
		//heading += Math.cos(Math.toRadians(15));
	}
	
	//Turn the camera left
	public void turnLeft()
	{
		
		heading -= 2; //JW
		//Following the camera:
			//heading -= Math.sin(Math.toRadians(15))*distance; 
			//heading -= Math.cos(Math.toRadians(15))*distance;
	}
}
