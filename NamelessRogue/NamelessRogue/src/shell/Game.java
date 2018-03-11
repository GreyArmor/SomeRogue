package shell;
 import Engine.Components.IComponent;
 import Engine.Infrastructure.Constants;
 import Engine.Infrastructure.EntityManager;
 import Engine.Factories.*;
 import Engine.Systems.*;
 import com.jogamp.opengl.*;
 import com.jogamp.opengl.awt.GLCanvas;
 import com.jogamp.opengl.util.FPSAnimator;

 import abstraction.IEntity;
import abstraction.ISystem;
 import data.GameSettings;

 import java.awt.*;
 import java.awt.event.KeyEvent;
 import java.util.ArrayList;
import java.util.List;
 import java.util.Optional;
 import java.util.UUID;
 import java.util.stream.Collectors;
 import java.util.stream.Stream;

 import javax.swing.*;
 import javax.swing.border.EmptyBorder;
 import javax.swing.border.LineBorder;
 import javax.swing.text.BadLocationException;

public class Game extends JFrame implements GLEventListener,java.awt.event.KeyListener {
      private static final long serialVersionUID = 1L;
     
      List <IEntity> Entities;
      List <ISystem> Systems;  
      InputSystem inputsystem;
      private long startTime;

      public GLCanvas getCanvas() {
		  return canvas;
	  }

	  private final GLCanvas canvas;

	  public List <IEntity> GetEntities()
      {
    	  return Entities;
      }
      public IEntity GetEntity(UUID id){
		  Stream<IEntity> iEntityStream = Entities.stream().filter(x -> x.GetId() == id);
		  Optional<IEntity> any = iEntityStream.findAny();
		  if(any.isPresent())
		  {
		  	return any.get();
		  }
		  else {
		  	return null;
		  }
	  }

	public void RemoveEntity(IEntity entity) {
	  	Entities.remove(entity);
		EntityManager.RemoveEntity(entity.GetId());
	}
      
      public <T extends IComponent> List<IEntity> GetEntitiesByComponentClass(Class<T> t)
      {
    	  List<IEntity> results = Entities.stream().filter(v->v.GetComponentOfType(t)!=null).map(v->v).collect(Collectors.toList());
    	  return results;
      }
      
      public  <T extends IComponent> IEntity GetEntityByComponentClass(Class<T> t)
      {
    	  List<IEntity> results = GetEntitiesByComponentClass(t);
    	  return results.size() > 0 ? results.get(0) : null;
      }
      
      public List <ISystem> GetSystems()
      {
    	  return Systems;
      }
      
      
      private GameSettings settings;

	  JPanel characterDisplayPanel;
      JTextArea textConsole;
	  //private Viewport currentScene;
	  private boolean started = false;
      public Game() {
		  super("Nameless rogue");
		  GLProfile profile = GLProfile.getDefault();
		  GLCapabilities capabilities = new GLCapabilities(profile);


		  //TODO: move to config later
		  final int width = 60;
		  final int height = 40;
		  settings = new GameSettings(width, height);

		  float weight = 1.25f;

		  int windowWidth = width * settings.getFontSize();
		  int windowHeight = (int) (height * settings.getFontSize() * weight);
		  int canvasSize = height * settings.getFontSize();
		  int uiAreaSize = windowHeight - canvasSize;

		  canvas = new GLCanvas(capabilities);
		  canvas.addGLEventListener(this);
		  canvas.addKeyListener(this);

		  this.setName("Nameless rogue");

		  JPanel mainPanel = new JPanel(new BorderLayout());
		  mainPanel.setBackground(Color.BLACK);

		  characterDisplayPanel = new JPanel();
		  characterDisplayPanel.setBorder(new LineBorder(Color.black, 1));

		  characterDisplayPanel.setSize(windowWidth / 2, uiAreaSize);


		  textConsole = new JTextArea();
		  textConsole.append("New game started!.");
		  textConsole.setBackground(Color.BLACK);
		  textConsole.setForeground(Color.white);
		  textConsole.setLineWrap(true);
		  textConsole.setWrapStyleWord(true);
		  textConsole.setSize(windowWidth / 2, uiAreaSize);
		  textConsole.setBorder(new EmptyBorder(0, 0, 0, 0));
		  textConsole.setMaximumSize(new Dimension(windowWidth / 2, uiAreaSize));
		  textConsole.setEditable(false);
		  JScrollPane paneScrollPane = new JScrollPane(textConsole);
		  paneScrollPane.setVerticalScrollBarPolicy(
				  JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		  paneScrollPane.setPreferredSize(new Dimension(windowWidth / 2, uiAreaSize));
		  paneScrollPane.setMinimumSize(new Dimension(0, 0));


		  mainPanel.add(characterDisplayPanel, BorderLayout.CENTER);
		  mainPanel.add(paneScrollPane, BorderLayout.LINE_END);

		  canvas.setSize(windowWidth, canvasSize);
		  this.setSize(windowWidth + 5, windowHeight + 40);
		  this.setLocationRelativeTo(null);
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  this.setVisible(true);
		  this.setResizable(false);

		  this.getContentPane().add(canvas, BorderLayout.PAGE_START);
		  this.getContentPane().add(mainPanel, BorderLayout.PAGE_END);

		  canvas.requestFocusInWindow();
		  inputsystem = new InputSystem();

		  Entities = new ArrayList<>();
		  Systems = new ArrayList<>();

		  //TODO: for test
		  Entities.add(RenderFactory.CreateViewport(settings));
		  Entities.add(TerrainFactory.CreateWorld());
		  Entities.add(InputHandlingFactory.CreateInput());

		  Entities.add(BuildingFactory.CreateDummyBuilding(109* Constants.ChunkSize + 1,307*Constants.ChunkSize,10, this));
		  Entities.add(BuildingFactory.CreateDummyBuilding(109* Constants.ChunkSize + 13,307*Constants.ChunkSize,10, this));
		  Entities.add(BuildingFactory.CreateDummyBuilding(109* Constants.ChunkSize + 1,307*Constants.ChunkSize + 13,10, this));
		  Entities.add(BuildingFactory.CreateDummyBuilding(109* Constants.ChunkSize + 1 + 13,307*Constants.ChunkSize + 13,10, this));

		  Entities.add(CharacterFactory.CreateSimplePlayerCharacter(109* Constants.ChunkSize,307*Constants.ChunkSize));
		//  Entities.add(CharacterFactory.CreateBlankNpc(109* Constants.ChunkSize - 1,307*Constants.ChunkSize));
		//  Entities.add(CharacterFactory.CreateBlankNpc(109* Constants.ChunkSize - 3,307*Constants.ChunkSize));
		//  Entities.add(CharacterFactory.CreateBlankNpc(109* Constants.ChunkSize - 5,307*Constants.ChunkSize));
		  Entities.add(CharacterFactory.CreateBlankNpc(109* Constants.ChunkSize - 7,307*Constants.ChunkSize));
		  Entities.add(ItemFactory.CreateItem());


		  //

		  ChunkManagementSystem chunkManagementSystem = new ChunkManagementSystem();
		  //initialize reality bubble
		  chunkManagementSystem.Update(0,this);
		  //
		  Systems.add(new InitializationSystem());
		  Systems.add(inputsystem);
		  Systems.add(new IntentSystem());
		//  Systems.add(new AiSystem());
		  Systems.add(new MovementSystem());
		  Systems.add(new CombatSystem());
		  Systems.add(new SwitchSystem());
		  Systems.add(new DamageHandlingSystem());
		  Systems.add(new DeathSystem());
		  Systems.add(chunkManagementSystem);
		  Systems.add(new RenderingSystem(settings));

		  paneScrollPane.revalidate();
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
		  if (started) {
		  	  long gametime = System.currentTimeMillis() - startTime;
			//  WriteLineToConsole(String.valueOf(gametime));
			  GL2 gl = drawable.getGL().getGL2();
			  gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			  for (ISystem system : this.Systems) {
				  system.Update(gametime, this);
			  }
			  gl.glFlush();
		  }
	  }

      
      public void play() {
    	  started  = true;
    	  startTime = System.currentTimeMillis();
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
	/*	 GL2 gl = drawable.getGL().getGL2();
		 gl.glMatrixMode(GL2.GL_PROJECTION);
		 gl.glLoadIdentity();
		 gl.glOrtho(0,1,0,1,-1,1);
		 gl.glMatrixMode(GL2.GL_MODELVIEW);*/
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


	public void WriteLineToConsole(String text)
	{
		int lineCount = textConsole.getLineCount();
		if(lineCount>10)
		{
			int howMuchLinesToRemove = lineCount - 10;
			for (int i = 0;i<howMuchLinesToRemove;i++) {
				int end = 0;
				try {
					end = textConsole.getLineEndOffset(0);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				textConsole.replaceRange("", 0, end);
			}
		}
		textConsole.append("\n");
		textConsole.append(text);
		textConsole.setCaretPosition(textConsole.getDocument().getLength());

	}


}
