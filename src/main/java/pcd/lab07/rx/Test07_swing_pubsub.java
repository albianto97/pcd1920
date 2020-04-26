package pcd.lab07.rx;

import javax.swing.*;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Test07_swing_pubsub {	

	static class MyFrame extends JFrame {	

		private PublishSubject<Integer> stream;
		
		public MyFrame(PublishSubject<Integer> stream){
			super("Swing + RxJava");
			this.stream = stream;
			setSize(150,60);
			setVisible(true);
			JButton button = new JButton("Press me");
			button.addActionListener((ActionEvent ev) -> {
				stream.onNext(1);
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
		
		PublishSubject<Integer> clickStream = PublishSubject.create();
		
		SwingUtilities.invokeLater(()->{
			new MyFrame(clickStream);
		});

		clickStream
		.subscribe((v) -> {
			System.out.println("click: "+System.currentTimeMillis());
		});

		clickStream
			.buffer(clickStream.throttleWithTimeout(250, TimeUnit.MILLISECONDS))
			.map(xs -> xs.size())
			.filter((v) -> v >= 2)
			.subscribe((v) -> {
				System.out.println("Multi-click: "+v);
			});
		
	}

}
