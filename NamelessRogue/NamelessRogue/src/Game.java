 import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
  
  public class Game extends JFrame implements GLEventListener {
      private static final long serialVersionUID = 1L;
  
      

           final private int width = 800;
           final private int height = 600;
       
           public Game() {
        	            super("HelloWorld");
        	            GLProfile profile = GLProfile.getDefault();
        	            GLCapabilities capabilities = new GLCapabilities(profile);
        	    
        	            GLCanvas canvas = new GLCanvas(capabilities);
        	            canvas.addGLEventListener(this);
        	    
        	            this.setName("Minimal OpenGL");
        	            this.getContentPane().add(canvas);
        	    
        	            this.setSize(width, height);
        	            this.setLocationRelativeTo(null);
        	            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	            this.setVisible(true);
        	            this.setResizable(false);
        	            canvas.requestFocusInWindow();
        	        }
      
      @Override
      public void display(GLAutoDrawable drawable) {
    	   GL3 gl = drawable.getGL().getGL3();
    	   gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
    	    
    	   // call your draw code here
    	   TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 12));
    	   textRenderer.beginRendering(900, 700);
    	   textRenderer.setColor(Color.YELLOW);
    	   textRenderer.setSmoothing(true);

    	   textRenderer.draw("Hello world!!", 0, 0);
    	   textRenderer.endRendering();
    	   gl.glFlush();
      }
      
      public void play() {
      }
 
     @Override
     public void dispose(GLAutoDrawable drawable) {
     }
 
     @Override
     public void init(GLAutoDrawable drawable) {
    	 GL3 gl = drawable.getGL().getGL3();
    	   gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);
     }
 
     @Override
     public void reshape(GLAutoDrawable drawable, int x, int y, int width,
             int height) {
     }  
  }
