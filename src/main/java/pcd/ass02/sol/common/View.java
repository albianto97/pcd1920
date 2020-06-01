package pcd.ass02.sol.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.swing.*;


public class View {
    
    private ViewFrame frame;
    private ViewModel conceptGraph;
    private List<InputListener> listeners;
    
    public View(int w, int h, ViewModel conceptGraph){
        this.conceptGraph = conceptGraph;
        frame = new ViewFrame(w, h, this);
        listeners = new LinkedList<InputListener>();
    }
   
    public void showUp() {
    	SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
    	});
    }

    public void setRunning() {
    	frame.setRunning();
    }

    public void setIdle() {
    	frame.setIdle();
    }

    public void setStopped() {
    	frame.setStopped();
    }
    
    public ViewModel getModel() {
    	return conceptGraph;
    }
     
	public void refresh() {
		frame.repaint();
	}    

	class ViewFrame extends JFrame {
        
        private VisualiserPanel bodyAreaPanel;
        private JButton startButton, stopButton;
        private JButton zoomIn, zoomOut;
        private JTextField concept, level, state;
        private int w, h;
        private double scale = 1;
        
        public ViewFrame(int w, int h, View view){
            setTitle(".:: Concept Graph ::.");
            this.w = w;
            this.h = h;
            setSize(w, h + 40);
            setResizable(false);
    		
    		JPanel controlPanel = new JPanel();    		
            concept = new JTextField(10);
            concept.setText("COVID-19");
            level = new JTextField(3);
            level.setText("1");
    		startButton = new JButton("start");
    		stopButton = new JButton("stop");
    		zoomIn = new JButton("+");
    		zoomOut = new JButton("-");
    		
    		controlPanel.add(new JLabel("Concept"));
    		controlPanel.add(concept);
    		controlPanel.add(new JLabel("Max level"));
    		controlPanel.add(level);
    		controlPanel.add(startButton);
    		controlPanel.add(stopButton);
    		controlPanel.add(zoomIn);
    		controlPanel.add(zoomOut);

    		JPanel statePanel = new JPanel();    
    		statePanel.setLayout(new FlowLayout());
            state = new JTextField(40);
            state.setEditable(false);
            state.setText("Waiting to start");
    		statePanel.add(new JLabel("State"));
    		statePanel.add(state);
    		
            bodyAreaPanel = new VisualiserPanel(w,h);
            
    		JPanel cp = new JPanel();
    		LayoutManager layout = new BorderLayout();
    		cp.setLayout(layout);
    		cp.add(BorderLayout.NORTH, controlPanel);
    		cp.add(BorderLayout.CENTER, bodyAreaPanel);
    		cp.add(BorderLayout.SOUTH, statePanel);
    		setContentPane(cp);		
            
    		startButton.setEnabled(true);
    		startButton.addActionListener(ev -> {
    			for (InputListener l: listeners) {
    				l.notifyStarted(concept.getText(), Integer.parseInt(level.getText()));
    			}
    		});
    		
    		stopButton.setEnabled(false);
    		stopButton.addActionListener(ev -> {
    			for (InputListener l: listeners) {
    				l.notifyStopped();
    			}
    		});
    		
    		zoomIn.addActionListener(ev -> {
    			scale = scale * 1.1;
    			bodyAreaPanel.updateScale(scale);
    			repaint();
    		});

    		zoomOut.addActionListener(ev -> {
    			scale = scale / 1.1;
    			bodyAreaPanel.updateScale(scale);
    			repaint();
    		});
    		
    		addWindowListener(new WindowAdapter(){
    			public void windowClosing(WindowEvent ev){
    				System.exit(-1);
    			}
    			public void windowClosed(WindowEvent ev){
    				System.exit(-1);
    			}
    		});
        }
                
        public void setRunning() {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
        	state.setText("Running...");
        }

        public void setIdle() {
        	state.setText("Idle");
        }

        public void setStopped() {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
        	state.setText("Waiting to start");
        }

        
        public void showUp() {
        	SwingUtilities.invokeLater(() -> {
                setVisible(true);
        	});
        }
         
        class VisualiserPanel extends JPanel {
            
            private int dy, dx;
            private int ox, oy;
            private double scale = 1;
            private HashMap<String,ViewModelNode> nodes;
            
            public VisualiserPanel(int w, int h){
                setSize(w,h);
                ox = w/2;
                oy = h/2;
                dx = w/2 - 20;
                dy = h/2 - 20;
                nodes = new HashMap<String,ViewModelNode>();
            }

            public void updateScale(double scale) {
            	this.scale = scale;
            }
            
            public void paint(Graphics g){
            	Graphics2D g2 = (Graphics2D) g;
        		
        		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        		          RenderingHints.VALUE_ANTIALIAS_ON);
        		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
        		          RenderingHints.VALUE_RENDER_QUALITY);
        		g2.clearRect(0,0,this.getWidth(),this.getHeight());

        		nodes.clear();
        		Optional<ViewModelNode> node = conceptGraph.getRootNode();
        		if (node.isPresent()) {
        			drawNode(g2, node.get());
        		}
        		g2.drawString("Num concepts: " + conceptGraph.getSize(), 10,20);
            }
            
            private void drawNode(Graphics2D g, ViewModelNode node) {

            	P2d pos = node.getPos();
    			double w = node.getWeight();
    			int rad = (int) (20 * node.getWeight()/100*scale);
    			int x = 0;
    			int y = 0;
    			if (w == 100) {
    				x = ox;
    				y = oy;
    			} else {
	            	x = ox + (int)(pos.x * dx * scale);
	    			y = oy - (int)(pos.y * dy * scale);
    			}
		        g.drawOval(x, y, rad, rad);
		        g.drawString(node.getConcept().getContent(), x, y - 10);
            	nodes.put(node.getConcept().getContent(),node);
            	
		        for (ViewModelNode n: node.getLinked()) {
		        	ViewModelNode l = nodes.get(n.getConcept().getContent());
		        	if (l == null) {
		        		drawNode(g, n);
		        	}
	            	P2d pos2 = n.getPos();
	    			int x2 = ox + (int)(pos2.x * scale * dx);
	    			int y2 = oy - (int)(pos2.y * scale * dy);
	    			int rad2 = (int) (20 * n.getWeight()/100*scale);
		        	g.drawLine(x + rad/2, y + rad/2, x2 + rad2/2, y2 + rad2/2);
		        }
            }

        }
        
    }
	
	public void addInputListener(InputListener l) {
		listeners.add(l);
	}

}
