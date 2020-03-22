package pcd.lab03.gui;

import javax.swing.*;
import java.awt.event.*;

public class TestSwingThread {	
	static class MyFrame extends JFrame {	
	  public MyFrame(){
		super("Test Swing thread");
  		whoAmI("MyFrame constructor");
	    setSize(150,60);
	    setVisible(true);
	    JButton button = new JButton("Press me");
	    button.addActionListener((ActionEvent ev) -> {
	  		System.out.println("Pressed!");
	    		whoAmI("Action listener");
	  		try {
	  			Thread.sleep(10000);
	  		} catch (Exception ex) {};
	    		// while (true){}
	    });
	    getContentPane().add(button);
	    addWindowListener(new WindowAdapter(){
	      public void windowClosing(WindowEvent ev){
	        System.exit(-1);
	      }
	    });
	  }
	}

  static public void main(String[] args){
	whoAmI("main");
    // SwingUtilities.invokeLater(()->{
    		new MyFrame();
    		new MyFrame();
    // });
  }

  private static void whoAmI(String where) {
	System.out.println("Who Am I? : "+Thread.currentThread()+" in "+where );
  }
}
