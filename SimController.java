package sim;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * Write a description of class SimController here.
 *
 * @author (Syed Basim Ali)
 * @version (November 28, 2022)
 */
public class SimController implements ActionListener
{
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem quitMenuItem;
    
    private JPanel topPanel;
    private JLabel topLabel;
    
    private JPanel midPanel;
    private JButton resetButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton stepButton;
    private JButton slowerButton;
    private JTextField timerDelayTextField;
    private JButton fasterButton;
    private JButton runToButton;
    private JTextField runToStepTextField;
    
    private JPanel bottomPanel;
    private JLabel bottomLabel;    
    
    private Simulator simulator;
    
    private Timer simTimer;
    
    public SimController(){
        initialize();
        this.simulator = new Simulator();
        simTimer = new Timer(getTimerDelay(), this); // e -> runSimulation());
        //simTimer.start();
    }
    
    public void initialize(){
        frame = new JFrame();
        this.frame.setTitle("Sim Control");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(300, 300);
        this.frame.setLocationRelativeTo(null);
        //this.frame.setLayout(new FlowLayout());
        
        setUpMenuBar(frame);
        
        setUptopPanel();
        
        setUpMidPanel();
        
        setUpBottomPanel();
        
        this.frame.setVisible(true);
        //this.frame.pack();
    }
    
    public void setUpMenuBar(JFrame frame){
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        loadMenuItem = new JMenuItem("Load Settings");
        loadMenuItem.addActionListener(this);
        saveMenuItem = new JMenuItem("Save Settings");
        saveMenuItem.addActionListener(this);
        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(this);
        
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(quitMenuItem);
        
        menuBar.add(fileMenu);
        
        frame.setJMenuBar(menuBar);
    }
    
    public void setUptopPanel(){
        topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.BLUE);
        frame.add(topPanel, BorderLayout.NORTH);
                
        topLabel = new JLabel();
        topLabel.setText("Sim Not Running");
        topLabel.setForeground(Color.YELLOW);
        topPanel.add(topLabel);
    }
    
    public void setUpMidPanel(){
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stepButton = new JButton("Step");
        stepButton.addActionListener(this);
        slowerButton = new JButton("Slower");
        slowerButton.addActionListener(this);
        fasterButton = new JButton("Faster");
        fasterButton.addActionListener(this);
        runToButton = new JButton("Run To");
        runToButton.addActionListener(this);
        
        midPanel = new JPanel();
        midPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(midPanel, BorderLayout.CENTER);
        timerDelayTextField = new JTextField();
        timerDelayTextField.setPreferredSize(new Dimension(60, 20));
        timerDelayTextField.setText("500");
        runToStepTextField = new JTextField();
        runToStepTextField.setPreferredSize(new Dimension(60, 20));
        runToStepTextField.setText("10");
        
        midPanel.add(timerDelayTextField);
        midPanel.add(runToStepTextField);
        midPanel.add(resetButton);
        midPanel.add(runButton);
        midPanel.add(stopButton);
        midPanel.add(stepButton);
        midPanel.add(slowerButton);
        midPanel.add(fasterButton);
        midPanel.add(runToButton);
    }
    
    public void setUpBottomPanel(){
        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.CYAN);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        bottomLabel = new JLabel();
        bottomLabel.setText("Status Bar");
        bottomPanel.add(bottomLabel);
    }
    
    public void actionPerformed(ActionEvent e ){
        
        if (e.getSource() == loadMenuItem){
            loadOrSaveMenuItem();
        }
        
        if (e.getSource() == saveMenuItem){
            loadOrSaveMenuItem();
        }
        
        if (e.getSource() == quitMenuItem){
            this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
            this.quit();
        }
        
        if (e.getSource() == resetButton){
            resetSimulation();
        }
        
        if (e.getSource() == runButton){
            runSimulation();
        }
        
        if (e.getSource() == stopButton){
            stopSimulation();
            simTimer.stop();
        }
        
        if (e.getSource() == stepButton){
            step();
        }
        
        if (e.getSource() == slowerButton){
            slower();
        }
        
        if (e.getSource() == fasterButton){
            faster();
        }
        
        if (e.getSource() == runToButton){
            runTo();
        }
    }
    
    public void loadOrSaveMenuItem(){
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null); //Select file to load or save
            
        if (response == JFileChooser.APPROVE_OPTION){
            JOptionPane.showMessageDialog(null, "Feature not yet implemented", "File Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setTopLabel(String label){
        topLabel.setText(label);
        topPanel.add(topLabel);
    }
    
    public void setBottomLabel(String label){
        bottomLabel.setText(label);
        bottomPanel.add(bottomLabel);
    }
    
    public void resetSimulation(){
        simulator.reset();
        setDefaultTextFieldValues();
    }
    
    public void setDefaultTextFieldValues(){
        setTopLabel("Sim Not Running");
        setBottomLabel("Status");
        timerDelayTextField.setText("500");
        runToStepTextField.setText("10");
    }
    
    public void setTimerDelay(String delay){
        int timerDelay = Integer.parseInt(delay);
        simTimer.setDelay(timerDelay);
        timerDelayTextField.setText(delay);
    }
    
    public int getTimerDelay(){
        int timerDelay = Integer.parseInt(timerDelayTextField.getText());
        return timerDelay;
    }
    
    public void sleep(){
        try {
            Thread.sleep(getTimerDelay());
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public void runSimulation(){
        simTimer.setDelay(getTimerDelay());
        simTimer.start();
        setTopLabel("Sim Running");

        new Thread(() -> {
            while(simTimer.isRunning()){
                simulator.simulateOneStep();
                sleep();
                //System.out.println("Step: " + simulator.getStep());
                setBottomLabel("[" + simulator.getStep() + "] " + simulator.getDetails());
            }
        }).start();
    }
    
    public void stopSimulation(){
        simTimer.stop();
        //simulator.endSimulation();
        setTopLabel("Sim Not Running");
    }
    
    public void step(){
        simulator.simulateOneStep();
        setBottomLabel("[" + simulator.getStep() + "] " + simulator.getDetails());
    }
    
    public void slower(){
        int timerDelay = (getTimerDelay() * 2);
        simTimer.setDelay(timerDelay);
        timerDelayTextField.setText(Integer.toString(simTimer.getDelay()));
    }
    
    public void faster(){
        if(getTimerDelay() >= 5){
            int timerDelay = (getTimerDelay() / 2);
            simTimer.setDelay(timerDelay);
            timerDelayTextField.setText(Integer.toString(simTimer.getDelay()));
        }
        else{
            timerDelayTextField.setText(Integer.toString(simTimer.getDelay()));
        }
    }
    
    public void runTo(){
        simTimer.setDelay(getTimerDelay());
        int runToStep = Integer.parseInt(runToStepTextField.getText());
        while(simulator.getStep() != runToStep){
            simulator.simulateOneStep();
            sleep();
            setBottomLabel("[" + simulator.getStep() + "] " + simulator.getDetails());
            //System.out.println("Step: " + simulator.getStep());
        }
    }
    
    public Simulator getSimulator(){
        return this.simulator;
    }
    
    public void quit(){
        setDefaultTextFieldValues();
        simTimer.stop();
        frame.dispose();
        simulator.endSimulation();
        frame.setVisible(false);
        frame.dispose();
    }
}
