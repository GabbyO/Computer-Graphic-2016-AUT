
/**

 * Chopper - yeah.
 * 
 * @author Jacqueline Whalley
 */

import com.sun.opengl.util.FPSAnimator;
import scene.Camera;
import scene.Lighting;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

/**
 * Starting class for Assignment 2 - Project Part I (2016)
 * Sets up prospective, a basic static camera and lighting
 * Renders a Utah Teapot at the origin
 * 
 * @author Gabriela Orellana
 *
 */
public class Heli16Flight implements GLEventListener, KeyListener
{
	//BOOLEANS
	private boolean forward, backward, left, right, down, up, sRight, sLeft, viewKey; //Storing the boolean for the Keys
	private boolean light, allLight;

	//scene objects
	private Camera camera;
	
	//global
	private float globalAmbient[] = { 0.7f, 0.7f, 0.7f, 1 }; 	// global light properties
	
	//Class
	private Lighting lights; 
	private Ground ground;
	private Sky sky;
	private Tree tree;
	private Pymraid pymraid;
	private Origin origin; //passed on the Origin
	private Helicopter heli; //passed on the Helicopter
	
	//Pyrmaid and Tree
	private double  pyrmaidX, pyrmaidZ;
	private Random pyrmaidR1, pyrmaidR2, treeR1, treeR2;
	private double treeX, treeZ;
	private double treeLoc[] = {0, 0, 0};
	
	//colour
	private float[] white = { 1f ,1f ,1f };
	
	//CAMERA
	private double camX;
	private double camZ;
	private double camY;
	
	//FOG
	protected boolean fog;
    private int     fogMode;
    private float   fogEnd;
    private float   fogDensity;
    
	public static void main(String[] args) 
	{
		//Frame
		Frame frame = new Frame("A2");
		
		//Canvas
		GLCanvas canvas = new GLCanvas();
		
		System.out.println("Key mapping:");
        System.out.println("UP/DOWN - Fly up/Fly down");
        System.out.println("LEFT/RIGHT- Turn Left/Turn Right");
        System.out.println("W/S - Move Forward/Move Backward");
        System.out.println("A/D - Move Left/Move Right");
        System.out.println("C - Change Camera Top-View/Bottom-View");
        System.out.println("F -   Toggle Fog On/Off");
        System.out.println("L -   Toggle Light On/Off");
        System.out.println("Z -   Toggle Whole Light On/Off");
        
		//Title
		Heli16Flight app = new Heli16Flight();
		
		//Add Listeners
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);
		
		//Add canvas
		frame.add(canvas);
		
		//Size
		frame.setSize(1000, 600);
		final FPSAnimator animator = new FPSAnimator(canvas,60);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		animator.start();
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
		GL gl = drawable.getGL();
		
		// Enable VSync
		gl.setSwapInterval(1);
		
		// Setup the drawing area and shading mode
		gl.glEnable (GL.GL_BLEND);
		gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glClearColor(0, 0, 0, 0);
		gl.glShadeModel(GL.GL_SMOOTH);
		
		// setup FOG
        fogSandstromSetup(gl);
        
        //CAMERA
		camera = new Camera();
		
		//SET UP class
		origin = new Origin();	
		heli = new Helicopter();
		ground = new Ground();
		tree = new Tree();
		sky = new Sky();
		pymraid = new Pymraid();
		
		//BOOLEANS
		left = false;
		right = false;
		down = false;
		up = false;
		forward = false;
		backward = false;
		sRight = false;
		sLeft = false;
		viewKey = true;
		fog = false;
		light = false;
		allLight = false;
		
		//new RANDOM
        pyrmaidR1= new Random();
        pyrmaidR2= new Random();
        treeR1= new Random();
        treeR2= new Random();
        
		//RANDOM position 
		//PYRMAID
		pyrmaidX = pyrmaidR1.nextInt(600);
		pyrmaidZ = pyrmaidR2.nextInt(600);
		
		//TREE
		treeX = treeR1.nextInt(600);
		treeZ = treeR2.nextInt(300);
		
		lights = new Lighting(gl);
	}

	//HOLD
	private void fogLinearSetup(GL gl)
	{
		fogEnd     /= 15.0f;
        fogDensity = 0.01f;
        gl.glFogf(GL.GL_FOG_DENSITY, 0.05f);
        gl.glFogf(GL.GL_FOG_START,0.1f);
        gl.glFogf(GL.GL_FOG_END, fogEnd);
        gl.glFogf(GL.GL_FOG_DENSITY, fogDensity);
        float[] fogColour = {0.87f, 0.65f, 0.0f, 1.0f};
        gl.glFogfv(GL.GL_FOG_COLOR, fogColour, 0);
        fogMode = GL.GL_LINEAR;
	}

	private void fogSandstromSetup(GL gl) 
	{
		fogEnd = 1500.0f;
        fogDensity = 0.01f;
        gl.glFogf(GL.GL_FOG_DENSITY, 0.05f);
        gl.glFogf(GL.GL_FOG_START,1500f);
        gl.glFogf(GL.GL_FOG_END, fogEnd);
        gl.glFogf(GL.GL_FOG_DENSITY, fogDensity);
        float[] fogColour = {0.87f, 0.65f, 0.0f, 1.0f};
        gl.glFogfv(GL.GL_FOG_COLOR, fogColour, 0);
        fogMode = GL.GL_EXP;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
		camera.newWindowSize(width, height);
	}

	@Override
	public void display(GLAutoDrawable drawable) 
	{
		GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// set the global ambient light level
		gl.glEnable(GL.GL_LIGHTING);
	    gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, globalAmbient, 0);
	    gl.glEnable(GL.GL_DEPTH_TEST);

		//HELI MOVEMENTS ENABLE
		heliMovement();
		
		//SET UP CAMERA
		cameraView();
		camera.setLookAt(heli.loc[0], heli.loc[1], heli.loc[2]);
		camera.setEye(camX, camY, camZ);

		//FOG
		fogToggle(gl);
		
		//DRAW
		camera.draw(gl);	
		origin.draw(gl);
		ground.draw(gl, white);
		
		gl.glPushMatrix();
			gl.glScaled(1250, 1250, 1250);
			gl.glRotated(90, 90, 90, 90);
			sky.draw(gl, white);
		gl.glPopMatrix();

		gl.glPushMatrix();	
			gl.glDisable(GL.GL_COLOR_MATERIAL);
				gl.glTranslated(treeX, 0, treeZ);
				tree.draw(gl);
				
				gl.glTranslated(treeX, 0, treeZ);
					
				treeLoc[0] = treeX;
				treeLoc[1] = 0;
				treeLoc[2] = treeZ;
					
			gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glPopMatrix();	
		
		gl.glPushMatrix();	
			gl.glDisable(GL.GL_COLOR_MATERIAL);
			gl.glTranslated(pyrmaidX, 0, pyrmaidZ);
			pymraid.draw(gl);
			gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glPopMatrix();
		
		gl.glPushMatrix();	
			gl.glDisable(GL.GL_COLOR_MATERIAL);
			lights.enable();
			    lights.draw(gl);
				heli.draw(gl);
			lights.disable();
			gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glPopMatrix();	
		
		lightToggle(gl);
		
		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}

	private void lightToggle(GL gl)
	{
		if(this.light)
			heli.lighning(gl);
		else
			gl.glDisable(GL.GL_LIGHT1);
		
		if(allLight)
			lighning(gl);
		else
			gl.glDisable(GL.GL_LIGHT2);
	}

	private void fogToggle(GL gl) 
	{
		if ( this.fog ) 
        	gl.glEnable(GL.GL_FOG);
        else       
        	gl.glDisable(GL.GL_FOG);
	}

	private void cameraView() 
	{
		if(this.viewKey)
		{
			camX = heli.loc[0] + Math.sin(Math.toRadians(heli.heading-90))*15;
			camY= heli.loc[1] + 5;
			camZ = heli.loc[2] + Math.cos(Math.toRadians(heli.heading- 90))*15;
		} else {
			//topdown
			camX = heli.loc[0] + Math.sin(Math.toRadians(heli.heading-90))*15;
			camY= heli.loc[1] + 35;
			camZ = heli.loc[2] + Math.cos(Math.toRadians(heli.heading- 90))*15;
		}
	}

	//methods not used
	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

	public void lighning(GL gl)
	{
			float diffuse[] = {1, 1, 1, 1};
			float ambient[] = {0.9f, 0.9f, 1, 1};
			float specular[] = {1, 1, 1, 1};
			
			// enable light 2
	        gl.glEnable(GL.GL_LIGHT2);
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, ambient, 0);
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, diffuse, 0);
		    gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, specular, 0);
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int a = e.getKeyCode();

		if(a == KeyEvent.VK_W)
		{
			this.forward = true;
		}
		
		if(a == KeyEvent.VK_S)
		{
			this.backward = true;
		}
		
		if(a == KeyEvent.VK_A)
		{
			this.sLeft = true;
		}
		
		if(a == KeyEvent.VK_D)
		{
			this.sRight = true;
		}
		
		if(a == KeyEvent.VK_LEFT)
		{
			this.left = true;
		}
		
		if(a == KeyEvent.VK_RIGHT)
		{
			this.right = true;
		}
		
		if(a == KeyEvent.VK_UP)
		{
			this.up = true;
		}
		
		if(a == KeyEvent.VK_DOWN)
		{
			this.down = true;
		}

		switch ( e.getKeyCode() )
        {
			case KeyEvent.VK_C: 
					this.viewKey = !this.viewKey;
				break;
					
			case KeyEvent.VK_F:
					this.fog = !this.fog;
				break;
					
			case KeyEvent.VK_L:
					this.light = !this.light;
				break;
			
			case KeyEvent.VK_Z:
					this.allLight = !this.allLight;
				break;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int a = e.getKeyCode();
		
		if(a == KeyEvent.VK_UP)
		{
			this.up = false;
		}
		
		if(a == KeyEvent.VK_DOWN)
		{
			this.down = false;
		}
		
		if(a == KeyEvent.VK_LEFT)
		{
			this.left = false;
		}
		
		if(a == KeyEvent.VK_RIGHT)
		{
			this.right = false;
		}
		
		if(a == KeyEvent.VK_S)
		{
			this.backward = false;
		}
		
		if(a == KeyEvent.VK_W)
		{
			this.forward = false;
		}
		
		if(a == KeyEvent.VK_A)
		{
			this.sLeft = false;
		}
		
		if(a == KeyEvent.VK_D)
		{
			this.sRight = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	}

	//HELI MOVEMENTS
	public void heliMovement()
	{
		//HELI MOVEMENTS
		if(this.up)
		{
			heli.moveUp();
		} 
	
		if(this.down)
		{
			heli.moveDown();
		} 
				
		if(this.forward)
		{
			heli.moveForward();
		} 
			
		if(this.backward)
		{
			heli.moveBackward();
		} 
				
		if(this.right)
		{
			heli.turnRight();
		} 
				
		if(this.left)
		{
			heli.turnLeft();
		} 
				
		if(this.sLeft)
		{
			heli.strafeLeft();
		} 
				
		if(this.sRight)
		{
			heli.strafeRight();
		}
	}
}
