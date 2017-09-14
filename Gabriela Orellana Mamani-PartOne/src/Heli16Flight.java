
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
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

/**
 * Starting class for Assignment 2 - Project Part I (2016)
 * Sets up prospective, a basic static camera and lighting
 * Renders a Utah Teapot at the origin
 * 
 * @author Jacqueline Whalley
 *
 */
public class Heli16Flight implements GLEventListener, KeyListener
{
	private boolean forward, backward, left,right,down,up, sRight, sLeft, lKey; //Storing the boolean for the Keys
	private Origin origin; //passed on the Origin
	private Helicopter heli; //passed on the Helicopter
	
	//scene objects
	private Camera camera;
	private Lighting lights;
	private Ground ground;
	
	//colour
	private float[] greenColour = { 0.35f ,0.87f ,0.7f };
	private float[] redColour = { 0.99f ,0.37f ,0.3f };
	
	public static void main(String[] args) 
	{
		//Frame
		Frame frame = new Frame("A2");
		
		//Canvas
		GLCanvas canvas = new GLCanvas();
		
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
		gl.glClearColor(0.74902f, 0.8847059f, 0.97059f, 0);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);

		ground = new Ground();
		camera = new Camera();
		lights = new Lighting(gl);
		origin = new Origin();	
		heli = new Helicopter();
		
		left = false;
		right = false;
		down = false;
		up = false;
		forward = false;
		backward = false;
		sRight = false;
		sLeft = false;
		lKey = false;
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
		
		gl.glShadeModel(GL.GL_SMOOTH); 
        gl.glEnable(GL.GL_DEPTH_TEST);
    	
    	// enable lighting
        gl.glEnable(GL.GL_LIGHTING);

        // enable light 0
        gl.glEnable(GL.GL_LIGHT0);

        // normalize the normals
        gl.glEnable(GL.GL_NORMALIZE);
        
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
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
		
		//if(this.lKey == this.ground.click) // not needed!
		//{
			//ground.draw(gl, greenColour); DRAWN IN WRONG PLACE!! BEFORE CAMERA SET_UP
		//}
		
		//SET UP CAMERA
		camera.setLookAt(heli.loc[0], heli.loc[1], heli.loc[2]);
		
		double camX =  heli.loc[0] + Math.sin(Math.toRadians(heli.heading-90))*15;
		double camZ =  heli.loc[2] + Math.cos(Math.toRadians(heli.heading-90))*15;
		double camY = heli.loc[1] + 6.5;
		
		camera.setEye(camX, camY, camZ);
		
		//DRAW
		camera.draw(gl);	
		lights.draw(gl);
		origin.draw(gl);
		ground.draw(gl, greenColour); 
		heli.draw(gl);
		
		//END
		//gl.glEnd(); ?? not needed
		// Flush all drawing operations to the graphics card
		gl.glFlush();
		
	}

	//methods not used
	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int a =e.getKeyCode();

		if(a == KeyEvent.VK_W)
		{
			this.forward = true;
			System.out.println("FORWARD");
		}
		
		if(a == KeyEvent.VK_S)
		{
			this.backward = true;
			System.out.println("BACKWARD");
		}
		
		if(a == KeyEvent.VK_A)
		{
			this.sLeft = true;
			System.out.println("STRAFE LEFT");
		}
		
		if(a == KeyEvent.VK_D)
		{
			this.sRight = true;
			System.out.println("STRAFE RIGHT");
		}
		
		if(a == KeyEvent.VK_LEFT)
		{
			this.left = true;
			System.out.println("TURN LEFT");
		}
		
		if(a == KeyEvent.VK_RIGHT)
		{
			this.right = true;
			System.out.println("TURN RIGHT");
		}
		
		if(a == KeyEvent.VK_UP)
		{
			this.up = true;
			System.out.println("UP");
		}
		
		if(a == KeyEvent.VK_DOWN)
		{
			this.down = true;
			System.out.println("DOWN");
		}
		
		if(a == KeyEvent.VK_L)
		{
			this.lKey = true;
			ground.click = true;
			System.out.println("CHANGE THE GROUND");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
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
		

		if(a == KeyEvent.VK_L)
		{
			this.lKey = false;
			ground.click = false;
			System.out.println("CHANGE THE GROUND");
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	}
}
