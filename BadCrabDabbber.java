import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.datatransfer.*;

/**
 * BadCrabDabber for Slack. AKA the worst program I've ever written.
 * 
 * @author Jayden Weaver
 *
 */
public class BadCrabDabbber {

	static final String VERSION = "0.0.1";
	static final int windowWidth = 800;
	static final int windowHeight = 800;
	static double mouseX = 0, mouseY = 0;
	static double emoteMouseX = 0, emoteMouseY = 0;
	static double usernameMouseX = 0, usernameMouseY = 0;
	static double userPhotoMouseX = 0, userPhotoMouseY = 0;
	// Array for holding emotes
	static String[] emoteArray;
	static int sleepTime = 500; // Default Sleep Time
	static boolean doListen = false;

	public static void main(String args[]) throws AWTException {

		// Try to set the look and feel for the current OS.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// do nothing
		}

		JFrame frame = new JFrame("BadCrabDabber " + VERSION);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		frame.setLocationRelativeTo(null);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton sendEmotes = new JButton("Send Custom Emotes");
		JTextArea emotesTextArea = new JTextArea(10, 50);
		BufferedImage logoImage = null;
		try {
			logoImage = ImageIO.read(new File("img/logo.png"));

		} catch (Exception e) {
			System.out.println("cannot find images.");
		}

		// Button for setting emotes
		JButton setEmotes = new JButton("Set Emotes");
		setEmotes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = emotesTextArea.getText();
				emoteArray = temp.split("\n");
				System.out.println(emoteArray.length);
			}
		});

		// Button for calibrating
		JButton calibrateButton = new JButton("Calibrate");
		calibrateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Place cursor in Slack window. You have five seconds.");
					TimeUnit.SECONDS.sleep(5);
					System.out.println("done");
					mouseX = MouseInfo.getPointerInfo().getLocation().getX();
					mouseY = MouseInfo.getPointerInfo().getLocation().getY();
					System.out.println(mouseX);
					System.out.println(mouseY);
					System.out.println("Place cursor on user photo. You have five seconds.");
					TimeUnit.SECONDS.sleep(5);
					userPhotoMouseX = MouseInfo.getPointerInfo().getLocation().getX();
					userPhotoMouseY = MouseInfo.getPointerInfo().getLocation().getY();
					System.out.println(userPhotoMouseX);
					System.out.println(userPhotoMouseY);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton dabButton = new JButton();
		Image dabIcon = Toolkit.getDefaultToolkit().getImage("img/dab.png");
		dabButton.setIcon(new ImageIcon(dabIcon));
		dabButton.setFocusable(false);
		dabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emoteArray = ("+:d:\n+:a:\n+:b:".split("\n"));
				sendEmotes.doClick();
			}
		});

		JButton crabButton = new JButton();
		Image crabIcon = Toolkit.getDefaultToolkit().getImage("img/crab.png");
		crabButton.setIcon(new ImageIcon(crabIcon));
		crabButton.setFocusable(false);
		crabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emoteArray = ("+:copyright:\n+:registered:\n+:ab:".split("\n"));
				sendEmotes.doClick();
			}
		});

		JButton badButton = new JButton();
		Image badIcon = Toolkit.getDefaultToolkit().getImage("img/bad.png");
		badButton.setIcon(new ImageIcon(badIcon));
		badButton.setFocusable(false);
		badButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emoteArray = ("+:b:\n+:a:\n+:d:".split("\n"));
				sendEmotes.doClick();
			}
		});

		JButton madButton = new JButton();
		Image madIcon = Toolkit.getDefaultToolkit().getImage("img/mad.png");
		madButton.setIcon(new ImageIcon(madIcon));
		madButton.setFocusable(false);
		madButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emoteArray = ("+:m:\n+:a:\n+:d:".split("\n"));
				sendEmotes.doClick();
			}
		});

		// Button and code for listening for new messages.
		JButton listenButton = new JButton("listen");
		listenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doListen = !doListen;
				try {
					Robot robot = new Robot();
					Color color = robot.getPixelColor((int) userPhotoMouseX, (int) userPhotoMouseY);
					System.out.println("Listening. Press Enter in the console to stop.");
					while (System.in.available() == 0) {
						if (!robot.getPixelColor((int) userPhotoMouseX, (int) userPhotoMouseY).equals(color)) {
							// Can fetch username here and decide whether to
							// emote.
							sendEmotes.doClick();
							break;
						}
					}
					System.out.println("You have stopped listening.");
				} catch (IOException | AWTException e1) {
					System.out.println("Failed to listen.");
					e1.printStackTrace();
				}
			}
		});
		panel1.add(listenButton);

		sendEmotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// beep boop
					Robot robot = new Robot();
					double originalMousePositionX = MouseInfo.getPointerInfo().getLocation().getX();
					double originalMousePositionY = MouseInfo.getPointerInfo().getLocation().getY();
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection stringSelection;
					robot.mouseMove((int) mouseX, (int) mouseY);
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					for (String s : emoteArray) {
						stringSelection = new StringSelection(s);
						clipboard.setContents(stringSelection, stringSelection);
						// CTRL+V
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						// Press Enter
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						System.out.println(s);

						TimeUnit.MILLISECONDS.sleep(sleepTime);

					}
					// Return mouse to original position
					robot.mouseMove((int) originalMousePositionX, (int) originalMousePositionY);

				} catch (Exception ex) {

				}
			}

		});

		JTextField delayField = new JTextField();
		delayField.setText("" + sleepTime);

		JButton delayButton = new JButton("Set Delay");
		delayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sleepTime = Integer.parseInt(delayField.getText());
			}
		});

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(700, 700));
		tabbedPane.setFocusable(false); // gets rid of ugly dotted line when a
										// tab is selected

		JLabel picLabel = new JLabel(new ImageIcon(logoImage));
		panel1.setLayout(new FlowLayout());
		panel1.add(badButton);
		panel1.add(crabButton);
		panel1.add(dabButton);
		panel1.add(madButton);

		panel1.add(sendEmotes);
		tabbedPane.add("Send Emotes", panel1);

		panel2.add(emotesTextArea);
		panel2.add(setEmotes);
		panel2.add(calibrateButton);
		panel2.add(delayField);
		panel2.add(delayButton);
		tabbedPane.add("Settings", panel2);

		JPanel panel3 = new JPanel();
		panel3.add(new JLabel(
				"<html>i can't come into work today i have bad crabs <br/> you must manually specify emotes to use the listener</html>"));
		tabbedPane.add("About", panel3);

		frame.setLayout(new FlowLayout());
		frame.add(picLabel);
		frame.add(tabbedPane);
		frame.setVisible(true);

	}

	/**
	 * Returns the author of the most recent slack message.
	 * 
	 * @return
	 */
	public static String getUsername() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		String username = "Failed to get username";
		try {
			Robot robot = new Robot();
			// Check Username
			robot.mouseMove((int) usernameMouseX, (int) usernameMouseY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			TimeUnit.MILLISECONDS.sleep(100);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			TimeUnit.MILLISECONDS.sleep(100);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			TimeUnit.MILLISECONDS.sleep(100);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_C);
			TimeUnit.MILLISECONDS.sleep(100);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			username = (String) clipboard.getData(DataFlavor.stringFlavor);
			String[] usernameArray = username.split("\n");

			int i = 0;
			System.out.println("begin loop");
			System.out.println(usernameArray.length);
			while (i < usernameArray.length - 1) {
				username = usernameArray[usernameArray.length - i - 1];
				if (username.length() > 2) {
					System.out.println(username.substring(username.length() - 1, username.length()));
					if (username.substring(username.length() - 1, username.length()).equals("]")) {
						break;
					}
				}

				i++;
			}

			username = username.substring(0, username.length() - 11);
			System.out.println("USERNAME: " + username);
		} catch (UnsupportedFlavorException | IOException | InterruptedException | AWTException e) {
			e.printStackTrace();
		}

		return username;
	}

}