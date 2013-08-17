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
	public static boolean debug = false;
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
			
			System.out.println("Generated Code is");
			System.out.println(MainDialog.Generate(uname));
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

}
