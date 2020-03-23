package pcd.lab03.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class TestJFXThread extends Application {
    public static void main(String[] args) {
		whoAmI("main");
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    		whoAmI("start");
        primaryStage.setTitle("Test JFX Thread");
        Button btn = new Button();
        btn.setText("Press me");
        btn.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
            	whoAmI("handle");
                System.out.println("Pressed!");
    	  		try {
    	  			Thread.sleep(10000);
    	  		} catch (Exception ex) {};
    	    	// while (true){}
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
    
    
    private static void whoAmI(String where) {
		System.out.println("Who Am I? : "+Thread.currentThread()+" in "+where );
    }
}