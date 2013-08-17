package net.petercashel.focm;

import java.awt.Frame;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.UIManager;

import net.petercashel.focm.gui.MainDialog;

import org.apache.commons.cli.*;

public class main {

	private static String uname = "";
	private static boolean debug = false;
	private static boolean nogui = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean canRun = false;

		CommandLine commandLine;
		Option option_u = OptionBuilder.withArgName("opt1").hasArg().withDescription("The u option").create("u");
		Option option_debug = new Option("debug", "The debug option");
		Option option_nogui = new Option("nogui", "The nogui option");
		Options options = new Options();
		CommandLineParser parser = new GnuParser();

		options.addOption(option_u);
		options.addOption(option_debug);
		options.addOption(option_nogui);

		try
		{
			commandLine = parser.parse(options, args);

			if (commandLine.hasOption("nogui"))
			{
				nogui = true;
				if (commandLine.hasOption("u"))
				{
					System.out.print("Generating Random Code For Name: ");
					System.out.println(uname = commandLine.getOptionValue("u"));
					canRun = true;
				} else {
					System.out.print("Please specify a name. e.g. -u Bob");
					System.exit(0);
				}

			}


			if (commandLine.hasOption("debug"))
			{
				System.out.println("Debug is On.");
				debug = true;
			}

		}
		catch (ParseException exception)
		{
			System.out.print("Parse error: ");
			System.out.println(exception.getMessage());
			System.exit(1);
		}

		if (canRun ) {
			Generate();
		}
		if (!nogui) {
			spawnGui();
		}


	}

	private static void spawnGui() {
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}
		MainDialog gui = new MainDialog();
		gui.run();

	}

	private static void Generate() {

		String UNICODEName = toUNICODE(uname);
		BigInteger BigIntUNICODEName = new BigInteger(UNICODEName.trim());

		if (debug) {
			System.out.println(UNICODEName);
		}

		DateFormat dateFormat = new SimpleDateFormat("HHmmssa");
		Date date = new Date();

		if (debug) {
			System.out.println(dateFormat.format(date));
		}

		String UNICODETime = toUNICODE(dateFormat.format(date));
		BigInteger BigIntUNICODETime = new BigInteger(UNICODETime);

		if (debug) {
			System.out.println(UNICODETime);
		}

		dateFormat = new SimpleDateFormat("EEEEddMMMyyyy");
		date = new Date();

		if (debug) {
			System.out.println(dateFormat.format(date));
		}

		String UNICODEDate = toUNICODE(dateFormat.format(date));
		UNICODEDate = toUNICODE(UNICODEDate.trim());
		BigInteger BigIntUNICODEDate = new BigInteger(UNICODEDate);

		if (debug) {
			System.out.println(UNICODEDate);
		}

		BigInteger BigIntCode_1 = ((BigIntUNICODEName.multiply(BigIntUNICODETime)).multiply(BigIntUNICODEDate));

		if (debug) {
			System.out.println("BigIntCode_1");
			System.out.println(BigIntCode_1);
		}
		BigInteger BigIntDivider = BigIntCode_1.divide(new BigInteger(String.valueOf(64)));
		if (debug) {
			System.out.println("BigIntDivider");
			System.out.println(BigIntDivider);			
		}
		String BigIntDividedString = BigIntDivider.toString();
		Random random = new Random(Long.parseLong(BigIntDividedString.substring(4, 20)));
		BigInteger BigIntRandom_1 = new BigInteger(150, random);
		if (debug) {
			System.out.println("BigIntRandom_1");
			System.out.println(BigIntRandom_1);
		}

		BigInteger BigIntPreFinal = (BigIntCode_1.multiply(BigIntRandom_1)).divide(BigIntDivider);
		BigInteger BigIntFinal = (BigIntPreFinal.multiply(BigIntRandom_1)).multiply(BigIntCode_1);

		if (debug) {
			System.out.println("BigIntFinal");
			System.out.println(BigIntFinal);
		}

		int BigIntFinalLength = BigIntFinal.toString().length();

		int whole = 0;
		int remains = 0;

		whole = BigIntFinalLength / 4;
		int part = ((BigIntFinalLength / 4) / 4) * 2;
		String Hex = "";		

		if (debug) {
			System.out.println("HexStart");
			System.out.println(whole);
			System.out.println(part);
			System.out.println(remains);
		}
		for(int i = part; i < whole; i++)
		{
			String str = Integer.toHexString(Integer.parseInt(BigIntFinal.toString().substring(i * 4, (i * 4) + 4)));
			Hex = Hex + str;
			//			if (debug) {
			//				System.out.println("HexRun");
			//				System.out.println(str);
			//				System.out.println(Hex);
			//			}
		}

		System.out.println("Generated Code is");
		System.out.println(Hex);


	}

	private static String toUNICODE(String input) {
		String output = "";
		for(int i = 0; i < input.length(); i++)
		{
			try {
				char ch = input.charAt(i);
				int cp = String.valueOf(ch).codePointAt(0);
				output = output + cp;
			} catch (NullPointerException e) {
				//just incase it throws when the character is blank
			}
		}
		return output;
	}

}
