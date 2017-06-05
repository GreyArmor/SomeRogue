package shell;
 import com.jogamp.newt.event.WindowEvent;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

import Engine.Factories.InputHandlingFactory;
import Engine.Factories.RenderFactory;
import Engine.Factories.TerrainFactory;
import Engine.Systems.InputSystem;
import Engine.Systems.RenderingSystem;
import abstraction.IEntity;
import abstraction.ISystem;
import data.GameSettings;

import com.jogamp.opengl.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JFrame;
 
  public class Game extends JFrame implements GLEventListener,java.awt.event.KeyListener {
      private static final long serialVersionUID = 1L;
     
      List <IEntity> Entities;   
      List <ISystem> Systems;  
      InputSystem inputsystem;
      
      public  List <IEntity> GetEntities()
      {
    	  return Entities;
      }
     
      
      public <T> List<IEntity> GetEntitiesByComponentClass(Class<T> t)
      {
    	  List<IEntity> results = Entities.stream().filter(v->v.GetComponentOfType(t)!=null).map(v->v).collect(Collectors.toList());
    	  return results;
      }
      
      public  <T> IEntity GetEntityByComponentClass(Class<T> t)
      {
    	  List<IEntity> results = GetEntitiesByComponentClass(t);
    	  return results.size() > 0 ? results.get(0) : null;
      }
      
      public List <ISystem> GetSystems()
      {
    	  return Systems;
      }
      
      
      private GameSettings settings;
      
      
	  //private Viewport currentScene;
	  private boolean started = false;
      public Game() {
    	  super("Nameless rogue");
    	  GLProfile profile = GLProfile.getDefault();
    	  GLCapabilities capabilities = new GLCapabilities(profile);
    	  
    	  GLCanvas canvas = new GLCanvas(capabilities);
    	  canvas.addGLEventListener(this);
    	  canvas.addKeyListener(this);
    	 
    	  this.setName("Nameless rogue");
    	  this.getContentPane().add(canvas);
    	  //TODO: move to config later
    	  final int width = 60;
          final int height = 40;
    	  settings = new GameSettings(width, height);
  
    	  this.setSize(width*settings.getFontSize(), height* settings.getFontSize());
    	  this.setLocationRelativeTo(null);
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setVisible(true);
    	  this.setResizable(false);
  
    	  canvas.requestFocusInWindow();
        	 
    	  
    	  
    	  inputsystem = new InputSystem();
    	  
    	  
    	  
    	  Entities = new ArrayList<>();
    	  Systems = new ArrayList<>();
    	  
    	  
    	  Entities.add(RenderFactory.CreateViewport(settings));
    	  Entities.add(TerrainFactory.CreateWorld());
    	  Entities.add(InputHandlingFactory.CreateInput());
    	  
    	  
    	  Systems.add(inputsystem);
    	  Systems.add(new RenderingSystem(settings)); 	  
    	  
    	
    	  FPSAnimator animator = new FPSAnimator(60);
          animator.add(canvas);
          animator.start();
    	  
      }
      
      public int getActualWidth()
      {
    	  return settings.getWidth()*settings.getFontSize();
      }
      public int getActualHeight()
      {
    	  return settings.getHeight()*settings.getFontSize();
      }
      
      @Override
      public void keyPressed(KeyEvent e) {
    	  inputsystem.keyPressed(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
    	  inputsystem.keyReleased(e);
      }
      
      
      @Override
      public void display(GLAutoDrawable drawable) {
    	  if(started)
    	  {
       	   GL gl = drawable.getGL().getGL();
    	   gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    	   for(ISystem system : this.Systems)
    	   {
    		   system.Update(null, this);
    	   }
    	  
    	   gl.glFlush();
    	  }
      }
      
      
      
      public void play() {
    	  started  = true;
    	  while(true)
    	  {
    		 
    	  }
      }
 
     @Override
     public void dispose(GLAutoDrawable drawable) {
     }
 
     @Override
     public void init(GLAutoDrawable drawable) {
    	   GL gl = drawable.getGL().getGL();
    	   gl.glClearColor(0,0,0, 1.0f);
     }
 
     @Override
     public void reshape(GLAutoDrawable drawable, int x, int y, int width,
             int height) {
     }



	public GameSettings getSettings() {
		return settings;
	}



	void setSettings(GameSettings settings) {
		this.settings = settings;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}  
  }
