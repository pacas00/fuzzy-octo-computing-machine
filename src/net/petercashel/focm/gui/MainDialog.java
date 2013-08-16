package net.petercashel.focm.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import java.awt.Point;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JTextField;

import net.petercashel.focm.main;

public class MainDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField authUserField;
	private JTextField authCodeField;
	public static String folder = null;

	public MainDialog(Frame parent) {
		super(parent);
		setLocation(new Point(0, 24));

		setModal(true);
				
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Hex AuthCode Generator", 0);
		label.setBorder(new EmptyBorder(0, 0, 16, 0));
		label.setFont(new Font("Default", 1, 16));
		panel.add(label, "North");
		
		JPanel optionsPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridLayout(0, 1));
		JPanel fieldPanel = new JPanel(new GridLayout(0, 1));
		optionsPanel.add(labelPanel, "West");
		optionsPanel.add(fieldPanel, "East");

		authUserField = new JTextField(30);

		JLabel lblauthUser = new JLabel("Auth User");
		labelPanel.add(lblauthUser);
		fieldPanel.add(authUserField);

		authCodeField = new JTextField(75);

		JLabel lblauthCode = new JLabel("Auth Code");
		labelPanel.add(lblauthCode);
		fieldPanel.add(authCodeField);

		panel.add(optionsPanel, "Center");

		getContentPane().add(panel);
		panel.setBorder(new EmptyBorder(16, 24, 8, 24));
		
		
		//define these first.
		final JButton SaveButton = new JButton("Generate");
		
		SaveButton.setDefaultCapable(true);
		SaveButton.setBounds(SaveButton.getX(), SaveButton.getY(), 20, 10);
		SaveButton.setSize(20, 10);
		SaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				authCodeField.setText(Generate(authUserField.getText()));
			}
		});
		fieldPanel.add(SaveButton);

		
		//Fix spacing
		JLabel lblblank = new JLabel(" ");
		labelPanel.add(lblblank);

		pack();
		setLocationRelativeTo(parent);
	}
	
	public MainDialog() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       	}
	
	public void run()
    {
        Frame emptyFrame = new Frame("Mod system installer");
        emptyFrame.setUndecorated(true);
        emptyFrame.setVisible(true);
        emptyFrame.setLocationRelativeTo(null);
		new MainDialog(emptyFrame).setVisible(true);
        emptyFrame.dispose();
    }
	
	private static String Generate(String name) {

		String ASCIIName = toASCII(name);
		BigInteger BigIntASCIIName = new BigInteger(ASCIIName.trim());

		DateFormat dateFormat = new SimpleDateFormat("HHmmssa");
		Date date = new Date();

		String ASCIITime = toASCII(dateFormat.format(date));
		BigInteger BigIntASCIITime = new BigInteger(ASCIITime);

		dateFormat = new SimpleDateFormat("EEEEddMMMyyyy");
		date = new Date();

		String ASCIIDate = toASCII(dateFormat.format(date));
		ASCIIDate = toASCII(ASCIIDate.trim());
		BigInteger BigIntASCIIDate = new BigInteger(ASCIIDate);

		BigInteger BigIntCode_1 = ((BigIntASCIIName.multiply(BigIntASCIITime)).multiply(BigIntASCIIDate));

		BigInteger BigIntDivider = BigIntCode_1.divide(new BigInteger(String.valueOf(64)));

		String BigIntDividedString = BigIntDivider.toString();
		Random random = new Random(Long.parseLong(BigIntDividedString.substring(4, 20)));
		BigInteger BigIntRandom_1 = new BigInteger(150, random);

		BigInteger BigIntPreFinal = (BigIntCode_1.multiply(BigIntRandom_1)).divide(BigIntDivider);
		BigInteger BigIntFinal = (BigIntPreFinal.multiply(BigIntRandom_1)).multiply(BigIntCode_1);

		int BigIntFinalLength = BigIntFinal.toString().length();

		int whole = 0;

		whole = BigIntFinalLength / 4;
		int part = ((BigIntFinalLength / 4) / 4) * 2;
		String Hex = "";		

		for(int i = part; i < whole; i++)
		{
			String str = Integer.toHexString(Integer.parseInt(BigIntFinal.toString().substring(i * 4, (i * 4) + 4)));
			Hex = Hex + str;
		}

		return Hex;
	}

	private static String toASCII(String input) {
		String output = "";
		for(int i = 0; i < input.length(); i++)
		{
			try {
				output = output + Character.getNumericValue(input.charAt(i));
			} catch (NullPointerException e) {
				//just incase it throws when the character is blank
			}
		}
		return output;
	}
}