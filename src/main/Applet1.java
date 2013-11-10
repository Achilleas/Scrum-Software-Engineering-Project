package main;

import java.applet.Applet;
import java.awt.*;

public class Applet1 extends Applet {
	
	TextField field1;
	
	public void init(){
		field1 = new TextField(30);
		field1.setText("Oh hai.");
	}

}
