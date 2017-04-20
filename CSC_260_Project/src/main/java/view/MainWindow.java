package view;

import java.util.HashMap;
import java.util.LinkedList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JOptionPane;

import model.FSM;
import model.FSMRunner;
import model.Observer;
import model.StateLocations;
import util.Saver;
import util.Loader;

/**
* A window which displays a provides editing 
* capabilities of a finite state machine.
*
*@author Matthew Goff
*/
public class MainWindow extends JFrame implements ActionListener, MouseListener, Observer, KeyListener
{
	private static final int TOP_GRAPHICAL_OFFSET	= 54;
	private static final int SIDE_GRAPHICAL_OFFSET	= 8;
	private static final int DEFAULT_WINDOW_WIDTH	= 700;
	private static final int DEFAULT_WINDOW_HEIGHT	= 500;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveOption;
	private JMenuItem loadOption;
	private JMenuItem runOption;
	private JMenuItem stopOption;

	private JPopupMenu rightClickMenu;
	private JMenuItem renameOption;
	private JMenuItem addStateOption;	
	private JMenuItem deleteStateOption;
	private JMenuItem makeAcceptingStateOption;
	private JMenuItem removeAcceptingStateOption;
	private JMenuItem makeStartingStateOption;
	private JMenuItem removeStartingStateOption;
	private JMenuItem addTransitionOption;
	private JMenuItem removeTransitionOption;

	private FSM theStateMachine;
	private FSMRunner theRunner;
	private StateLocations stateLocations;
	private MouseEvent currentCommandMouseEvent;
	private HashMap<Object, Command> commands;
	private LinkedList<StatePanel> statePanels;
	private PixelCoords currentEventCoords;
	private StatePanel currentEventPanel;
	private StatePanel panelAwaitingTransition;

	/**
	* Create a MainWindow and all instanciated components
	* but does not make the window visible. See MainWindow.open()
	*/
	public MainWindow()
	{
		//The order of these methods is very important.
		init_window();
		init_menuBar();
		init_rightClickMenu();
		init_commands();
		
		theStateMachine = new FSM();
		theRunner = new FSMRunner(theStateMachine);
		stateLocations = new StateLocations();
		statePanels = new LinkedList<StatePanel>();

		//theStateMachine.addObserver(this);
		//theRunner.addObserver(this);

		updateUI();
	}

	private void init_window()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WINDOW_WIDTH,DEFAULT_WINDOW_HEIGHT);		
		this.setLocation(	Toolkit.getDefaultToolkit().getScreenSize().width/2 - this.getWidth()/2,
							Toolkit.getDefaultToolkit().getScreenSize().height/2 - this.getHeight()/2
						);
		this.setTitle("MAD - FSM simulator");
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
	}

	/**
	* http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
	*/
	private void init_menuBar()
	{
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");

		saveOption = new JMenuItem("Save");
		loadOption = new JMenuItem("Load");
		runOption = new JMenuItem("Run");
		stopOption = new JMenuItem("Stop");

		saveOption.addActionListener(this);
		loadOption.addActionListener(this);
		runOption.addActionListener(this);
		stopOption.addActionListener(this);
		
		fileMenu.add(saveOption);
		fileMenu.add(loadOption);
		fileMenu.add(runOption);
		fileMenu.add(stopOption);

		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
	}

	/**
	* http://www.java2s.com/Code/Java/Swing-JFC/AsimpleexampleofJPopupMenu.htm
	*/
	private void init_rightClickMenu()
	{
		rightClickMenu = new JPopupMenu();

		renameOption = new JMenuItem("Rename");
		addStateOption = new JMenuItem("Add state");
		deleteStateOption = new JMenuItem("Delete");
		makeAcceptingStateOption = new JMenuItem("Make accepting state");
		removeAcceptingStateOption = new JMenuItem("Remove accepting state");
		makeStartingStateOption = new JMenuItem("Make starting state");
		removeStartingStateOption = new JMenuItem("Remove starting state");
		addTransitionOption = new JMenuItem("Add transition");
		removeTransitionOption = new JMenuItem("Remove transition");

		renameOption.addActionListener(this);
		addStateOption.addActionListener(this);
		deleteStateOption.addActionListener(this);
		makeAcceptingStateOption.addActionListener(this);
		removeAcceptingStateOption.addActionListener(this);
		makeStartingStateOption.addActionListener(this);
		removeStartingStateOption.addActionListener(this);
		addTransitionOption.addActionListener(this);
		removeTransitionOption.addActionListener(this);

		rightClickMenu.add(renameOption);
		rightClickMenu.add(addStateOption);
		rightClickMenu.add(deleteStateOption);
		rightClickMenu.add(makeAcceptingStateOption);
		rightClickMenu.add(removeAcceptingStateOption);
		rightClickMenu.add(makeStartingStateOption);
		rightClickMenu.add(removeStartingStateOption);
		rightClickMenu.add(addTransitionOption);
		rightClickMenu.add(removeTransitionOption);
	}

	/**
	* http://stackoverflow.com/questions/4480334/how-to-call-a-method-stored-in-a-hashmap-java
	*/
	private void init_commands()
	{
		commands = new HashMap<Object, Command>();

		commands.put(saveOption, new Command() {
			public void runCommand() { saveOptionSelected(); }
		});

		commands.put(loadOption, new Command() {
			public void runCommand() { loadOptionSelected(); }
		});

		commands.put(addStateOption, new Command() {
			public void runCommand() { addStateOptionSelected(); }
		});

		commands.put(renameOption, new Command() {
			public void runCommand() { renameOptionSelected(); }
		});

		commands.put(deleteStateOption, new Command() {
			public void runCommand() { deleteStateOptionSelected(); }
		});

		commands.put(runOption, new Command() {
			public void runCommand() { runOptionSelected(); }
		});

		commands.put(stopOption, new Command() {
			public void runCommand() { stopOptionSelected(); }
		});		

		commands.put(makeAcceptingStateOption, new Command() {
			public void runCommand() { makeAcceptingStateOptionSelected(); }
		});

		commands.put(removeAcceptingStateOption, new Command() {
			public void runCommand() { removeAcceptingStateOptionSelected(); }
		});

		commands.put(makeStartingStateOption, new Command() {
			public void runCommand() { makeStartingStateOptionSelected(); }
		});

		commands.put(removeStartingStateOption, new Command() {
			public void runCommand() { removeStartingStateOptionSelected(); }
		});

		commands.put(addTransitionOption, new Command() {
			public void runCommand() { addTransitionOptionSelected(); }
		});

		commands.put(removeTransitionOption, new Command() {
			public void runCommand() { removeTransitionOptionSelected(); }
		});
	}

	/**
	* Makes the window visible
	*/
	public void open()
	{
		this.setVisible(true);
	}

	public void notifyObserver()
	{
		this.updateUI();
	}

	private void updateUI()
	{
		if (theRunner.isRunning())
		{
			System.out.println("the runner is running");
			runOption.setVisible(false);
			stopOption.setVisible(true);
		}
		else
		{
			System.out.println("the runner is not running");
			runOption.setVisible(true);
			stopOption.setVisible(false);
		}
		menuBar.updateUI();
		
		invalidate();
		revalidate();
		repaint();
		System.out.println(theStateMachine.toString());
	}

	public void actionPerformed(ActionEvent event)
	{
		commands.get(event.getSource()).runCommand();
	}

	public void mouseClicked(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e)
	{

	}

	public void mouseReleased(MouseEvent e)
	{
		currentEventCoords = new PixelCoords(e.getX()-SIDE_GRAPHICAL_OFFSET,e.getY()-TOP_GRAPHICAL_OFFSET);
		
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			if (theRunner.isRunning())
			{
				JOptionPane.showMessageDialog(this, "Cannot edit the FSM while running.");
				return;//This is not good design;
			}
			if (e.getSource() == this)
			{
				setWindowMenu();//Updates the menu options
				rightClickMenu.show(this, e.getX(), e.getY());
			}
			else //The the source must be a statePanel
			{
				currentEventPanel = (StatePanel) e.getSource();
				setStateMenu((currentEventPanel).getState());//Updates the menu options
				rightClickMenu.show(this, e.getX()+currentEventPanel.getUpperLeftX()+SIDE_GRAPHICAL_OFFSET, e.getY()+currentEventPanel.getUpperLeftY()+TOP_GRAPHICAL_OFFSET);//Do something about this hideousness
			}

			
		}
		else if(e.getButton() == MouseEvent.BUTTON1)
		{
			if (e.getSource() == this)
			{
				System.out.println("You left clicked in the window");
			}
			else //The the source must be a statePanel
			{
				currentEventPanel = (StatePanel) e.getSource();
				if (panelAwaitingTransition == null)
				{
					System.out.println("You left clicked on "+currentEventPanel.getState());
				}
				else
				{
					String newTransitionName = (String) JOptionPane.showInputDialog(
						this,
						"New Transition from "+panelAwaitingTransition.getState()+" to "+currentEventPanel.getState()+" :",
						"New Transition Dialog",
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						"a");
					theStateMachine.addTransition(panelAwaitingTransition.getState(),currentEventPanel.getState(),newTransitionName.charAt(0));
					currentEventPanel = null;
				}
			}
		}
		else
		{
			//If the user used the midle butotn.
		}
	}

	public void keyTyped(KeyEvent e) {
		theRunner.acceptInput(e.getKeyChar());
		updateUI();
	}

	
	public void keyPressed(KeyEvent e) {
	}

	
	public void keyReleased(KeyEvent e) {
	}

	/**
	* http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	*/
	private void saveOptionSelected()
	{

		String saveName = (String) JOptionPane.showInputDialog(
			this,
			"Save file as:",
			"Save Dialog",
			JOptionPane.PLAIN_MESSAGE,
			null,
			null,
			"MyStateMachineFile");

		if (saveName!=null)// If the user didn't hit 'cancel'
		{
			boolean successful = Saver.save(theStateMachine,stateLocations,saveName);

			if (successful)
			{
				JOptionPane.showMessageDialog(this, "State Machine saved.");
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Error saving. Please try again.");
			}			
		}
	}

	/**
	* http://stackoverflow.com/questions/5841620/how-to-read-directories-in-java
	*/
	private void loadOptionSelected()
	{
		String[] savedFiles = Loader.getAvailableFileNames();

		if (savedFiles==null)
		{
			JOptionPane.showMessageDialog(this, "There are no FSM files in your current directory.");
		}
		else
		{
			String loadName = (String)JOptionPane.showInputDialog(
				this,
				"Select a file to load:",
				"Load Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,
				savedFiles,
				null);

			for (StatePanel panel : statePanels)
			{
				this.getContentPane().remove(panel);
			}

			Object[] loadedObjects = Loader.load(loadName);
			theStateMachine = (FSM) loadedObjects[0];
			stateLocations = (StateLocations) loadedObjects[1];
			
			theRunner = new FSMRunner(theStateMachine);

			for (String state : theStateMachine.getStates())
			{
				createStatePanel(state);
			}



			if (theStateMachine==null)
			{
				JOptionPane.showMessageDialog(this, "Error Loading. Please try again.");
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Load successful");
			}
		}	
	}

	private void runOptionSelected()
	{
		if (theRunner.isRunning())
		{
			JOptionPane.showMessageDialog(this, "The state machine is already running"); //This should never need to appear, but just encase
		}
		else
		{
			boolean successful = theRunner.run();
			if (!successful)
			{
				JOptionPane.showMessageDialog(this, "Cannot run this state machine.\n You need one and only one starting state to run.");
			}
		}
		updateUI();
	}

	private void stopOptionSelected()
	{
		System.out.println("you selected stop");
		theRunner.stop();
	}

	private void addStateOptionSelected()
	{
		PixelCoords newStateCoords = new PixelCoords(currentEventCoords.getX(), currentEventCoords.getY());

		if (validStateLocation(newStateCoords))
		{
			String newStateName = (String) JOptionPane.showInputDialog(
				this,
				"New state name:",
				"New State Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"State A");

			if (newStateName!=null)// If the user didn't hit 'cancel'
			{
				boolean successful = theStateMachine.addState(newStateName);

				if (successful)
				{
					//The order of these two statements is incredibly important.
					stateLocations.putStateLocation(newStateName,newStateCoords);
					createStatePanel(newStateName);	
				}
				else
				{
					JOptionPane.showMessageDialog(this, "There is already a state by that name. You cannot have two states with the same name.");
				}			
			}			
		}
		else
		{
			JOptionPane.showMessageDialog(this, "That is not a valid location for a state. It is too close to another state.");
		}
	}

	private void createStatePanel(String stateName)
	{
		StatePanel newStatePanel = new StatePanel(theStateMachine, stateName, stateLocations, theRunner);
		statePanels.add(newStatePanel);

		this.getContentPane().add(newStatePanel);
		newStatePanel.setBounds(newStatePanel.getUpperLeftX(),
							newStatePanel.getUpperLeftY(),
							newStatePanel.getWidth(),
							newStatePanel.getHeight());
		newStatePanel.addMouseListener(this);

		updateUI();
	}

	private void renameOptionSelected()
	{
		System.out.println("renameOption selected");
	}

	private void deleteStateOptionSelected()
	{

		System.out.println("deleteStateOption selected");
	}

	private void makeAcceptingStateOptionSelected()
	{
		theStateMachine.makeAcceptingState(currentEventPanel.getState());
		updateUI();//This shouldn't be here in the long run
	}

	private void removeAcceptingStateOptionSelected()
	{
		theStateMachine.removeAcceptingState(currentEventPanel.getState());
		updateUI();//This shouldn't be here in the long run
	}

	private void makeStartingStateOptionSelected()
	{
		theStateMachine.makeStartingState(currentEventPanel.getState());
		updateUI();//This shouldn't be here in the long run
	}

	private void removeStartingStateOptionSelected()
	{
		theStateMachine.removeStartingState(currentEventPanel.getState());
		updateUI();//This shouldn't be here in the long run
	}

	private void addTransitionOptionSelected()
	{
		panelAwaitingTransition = currentEventPanel;
	}

	private void removeTransitionOptionSelected()
	{
		System.out.println("removeTransitionOption selected");
	}

	private boolean validStateLocation(PixelCoords coords)
	{
		for (PixelCoords otherCoords: stateLocations.getLocations())
		{
			if (coords.verticalDistanceFrom(otherCoords)<100 && coords.horizontalDistanceFrom(otherCoords)<100)
			{
				return false;
			}
		}

		return true;
	}

	public void setStateMenu(String currentState)
	{
		renameOption.setVisible(true);
		addStateOption.setVisible(false);
		deleteStateOption.setVisible(true);
		addTransitionOption.setVisible(true);
		removeTransitionOption.setVisible(true);

		if (theStateMachine.isAcceptingState(currentState))
		{
			makeAcceptingStateOption.setVisible(false);
			removeAcceptingStateOption.setVisible(true);
		}
		else
		{
			makeAcceptingStateOption.setVisible(true);
			removeAcceptingStateOption.setVisible(false);
		}
		if (theStateMachine.isStartingState(currentState))
		{
			makeStartingStateOption.setVisible(false);
			removeStartingStateOption.setVisible(true);
		}
		else
		{
			makeStartingStateOption.setVisible(true);
			removeStartingStateOption.setVisible(false);
		}
	}

	public void setWindowMenu()
	{
		renameOption.setVisible(false);
		addStateOption.setVisible(true);
		deleteStateOption.setVisible(false);
		makeAcceptingStateOption.setVisible(false);
		removeAcceptingStateOption.setVisible(false);
		makeStartingStateOption.setVisible(false);
		removeStartingStateOption.setVisible(false);
		addTransitionOption.setVisible(false);
		removeTransitionOption.setVisible(false);
	}

	public void paint(Graphics g)
	{
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.translate(SIDE_GRAPHICAL_OFFSET,TOP_GRAPHICAL_OFFSET);

		//g2.drawLine(10,0,20,0);
		//g2.drawLine(0,10,0,20);

		if (theRunner.inAcceptingState())
		{
			g2.setStroke(new BasicStroke(10));
			g2.setColor(Color.CYAN);
			g2.drawRect(5,5,DEFAULT_WINDOW_WIDTH-(SIDE_GRAPHICAL_OFFSET*2)-10,DEFAULT_WINDOW_HEIGHT-(TOP_GRAPHICAL_OFFSET+8)-10);
		}
	}

	private StatePanel getPanelByState(String state)
	{
		for (StatePanel panel : statePanels)
		{
			if (panel.getState().equals(state))
			{
				return panel;
			}
		}

		return null;
	}
}