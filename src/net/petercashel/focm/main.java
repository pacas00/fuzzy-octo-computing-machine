package net.petercashel.focm;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
		BigInteger BigIntASCIIName = new BigInteger(ASCIIName.trim());

		if (debug) {
			System.out.println(ASCIIName);
		}

		DateFormat dateFormat = new SimpleDateFormat("HHmmssa");
		Date date = new Date();

		if (debug) {
			System.out.println(dateFormat.format(date));
		}

		String ASCIITime = toASCII(dateFormat.format(date));
		BigInteger BigIntASCIITime = new BigInteger(ASCIITime);

		if (debug) {
			System.out.println(ASCIITime);
		}

		dateFormat = new SimpleDateFormat("EEEEddMMMyyyy");
		date = new Date();

		if (debug) {
			System.out.println(dateFormat.format(date));
		}

		String ASCIIDate = toASCII(dateFormat.format(date));
		ASCIIDate = toASCII(ASCIIDate.trim());
		BigInteger BigIntASCIIDate = new BigInteger(ASCIIDate);

		if (debug) {
			System.out.println(ASCIIDate);
		}

		BigInteger BigIntCode_1 = ((BigIntASCIIName.multiply(BigIntASCIITime)).multiply(BigIntASCIIDate));

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
