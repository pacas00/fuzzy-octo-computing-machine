package net.petercashel.focm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.*;

public class main {

	private static String uname = "";
	private static boolean debug = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean canRun = false;
		
		CommandLine commandLine;
        Option option_u = OptionBuilder.withArgName("opt1").hasArg().withDescription("The u option").create("u");
        Option option_debug = new Option("debug", "The debug option");
        Options options = new Options();
        CommandLineParser parser = new GnuParser();

        options.addOption(option_u);
        options.addOption(option_debug);
                
        try
        {
            commandLine = parser.parse(options, args);

            if (commandLine.hasOption("u"))
            {
                System.out.print("Generating Random Code For Name: ");
                System.out.println(uname = commandLine.getOptionValue("u"));
                canRun = true;
            } else {
            	System.out.print("Please specify a name. e.g. -u Bob");
            	System.exit(0);
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
		
        if (canRun) {
        	Generate();
        }
		

	}

	private static void Generate() {

		String ASCIIName = toASCII(uname);
		int intASCIIName = Integer.parseInt(ASCIIName);
		
		if (debug) {
			System.out.println(ASCIIName);
		}


		
		
		
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
