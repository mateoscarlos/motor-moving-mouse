package com.mouver.main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;

import com.mouver.threads.ThreadMoveMouse;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;

public class MainWindow {

	private final int DEFAULT_MOVE_PX = 5;
	
	private JFrame frmMouver;
	private ThreadMoveMouse moveMouse;
	private JButton btnApply, btnStop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMouver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMouver = new JFrame();
		frmMouver.setResizable(false);
		frmMouver.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource(Constants.PATH_ICON)));
		frmMouver.setTitle(Constants.TITLE);
		frmMouver.setBounds(500, 200, 500, 250);
		frmMouver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Kill all threads
		frmMouver.getContentPane().setLayout(null);
		
		initElements();
	}
	
	private void initElements() {

		// EXIT BUTTON
		JButton btnExit = new JButton(Constants.TEXT_BTN_EXIT);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		btnExit.setBounds(395, 182, 81, 21);
		frmMouver.getContentPane().add(btnExit);
		
		JSlider sliderSpeed = new JSlider();
		sliderSpeed.setValue(250);
		sliderSpeed.setMaximum(500);
		sliderSpeed.setBounds(138, 125, 200, 22);
		frmMouver.getContentPane().add(sliderSpeed);
		
		// APPLY BUTTON
		btnApply = new JButton(Constants.TEXT_BTN_APPLY);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Init mouse thread
				moveMouse = new ThreadMoveMouse(DEFAULT_MOVE_PX, DEFAULT_MOVE_PX, sliderSpeed.getValue());
				moveMouse.start();
				
				btnStop.setEnabled(true);
				btnApply.setEnabled(false);
				sliderSpeed.setEnabled(false);
			}

		});
		btnApply.setBounds(253, 74, 103, 29);
		frmMouver.getContentPane().add(btnApply);
		
		// STOP BUTTON
		btnStop = new JButton(Constants.TEXT_BTN_STOP);
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMouse.kill();
				btnStop.setEnabled(false);
				btnApply.setEnabled(true);
				sliderSpeed.setEnabled(true);
			}

		});
		btnStop.setBounds(116, 74, 103, 29);
		frmMouver.getContentPane().add(btnStop);
		
		JLabel lblInfoVersion = new JLabel(Constants.TEXT_LBL_INFO_VERSION);
		lblInfoVersion.setFont(new Font("Tahoma", Font.PLAIN, 7));
		lblInfoVersion.setBounds(10, 182, 188, 13);
		frmMouver.getContentPane().add(lblInfoVersion);
		
		JLabel lblInfoOwner = new JLabel(Constants.TEXT_LBL_INFO_DEVELOPED);
		lblInfoOwner.setFont(new Font("Tahoma", Font.PLAIN, 7));
		lblInfoOwner.setBounds(10, 190, 188, 13);
		frmMouver.getContentPane().add(lblInfoOwner);
	}
}
