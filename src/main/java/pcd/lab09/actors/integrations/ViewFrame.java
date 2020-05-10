package pcd.lab09.actors.integrations;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import akka.actor.ActorRef;

public class ViewFrame extends JFrame {

	public ViewFrame(ActorRef actorView) {
		super(".:: Test Swing | Actors interaction ::.");
		setSize(150, 60);
		setVisible(true);
		JButton button = new JButton("Press me");
		button.addActionListener((ActionEvent ev) -> {
			actorView.tell(new PressedMsg(), ActorRef.noSender());
		});

		getContentPane().add(button);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(-1);
			}
		});
	}
}