package com.mouver.threads;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;

public class ThreadMoveMouse extends Thread {

	// Display dimension
	private int widthDisplay;
	private int heightDisplay;
	
	private boolean alive;
	private int movePx_X, movePx_Y;
	private int speed;
	private boolean RIGHT, DOWN;
	
	public ThreadMoveMouse(int x, int y, int speed) {
		movePx_X = x;
		movePx_Y = y;
		alive = true;
		RIGHT = true;
		DOWN = true;
		this.speed = speed;
	}
	
	public void kill() {
		alive = false;
	}
	
	public void run() {
		int posX, posY;
		
		while(alive) {
			posX = getPosition(0);
			posY = getPosition(1);
			
			try {
				moveMouse(posX, posY, movePx_X, movePx_Y);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(500 - speed);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	private void moveMouse(int x, int y, int pxX, int pxY) throws AWTException {
		
		Robot robot = new Robot();
		
		widthDisplay  = Toolkit.getDefaultToolkit().getScreenSize().width;
		heightDisplay = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		if (RIGHT) {
			if (DOWN) {
				robot.mouseMove(x+pxX, y+pxY);
			} else {
				robot.mouseMove(x+pxX, y-pxY);
			}
		} else {
			if (DOWN) {
				robot.mouseMove(x-pxX, y+pxY);
			} else {
				robot.mouseMove(x-pxX, y-pxY);
			}
		}
		
		
		if (!RIGHT && x < 1) { RIGHT = true; }
		if (!DOWN  && y < 1) { DOWN = true; }
		if (RIGHT  && x > widthDisplay-5)   { RIGHT = false; }
		if (DOWN   && y > heightDisplay-50) { DOWN = false; }
		
		
		
	}
	
	private int getPosition(int coord) {
		
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		
		if (coord == 0) {
			return p.x;
		} else if(coord == 1) {
			return p.y;
		}
		
		return 0;
	}
	

}
