package de.onewaypub.diceware.generator.diceware;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;

/**
 * Generates three diceware password types. one lowercase, one camelcase and one camelcase with leetspeak replacements
 *
 */
public class DicewareGenerator {
	public static void main(String[] args) throws IOException,
			URISyntaxException {
		// Load file
		File f = new File(DicewareGenerator.class.getResource("/diceware_german.txt").toURI());
		Map<String, String> l = new HashMap<String, String>();
		FileUtils utils = new FileUtils();
		List<String> lines = utils.readLines(f);
		for (String line : lines) {
			if (line.length() > 1) {
				String[] keyValuePair = line.split("        ");
				if (keyValuePair != null && keyValuePair.length > 1)
					l.put(keyValuePair[0], keyValuePair[1]);
			}
		}

		int min = 11111;
		int max = 66666;
		String pwd = "";
		String pwdUpperCase = "";
		String pwdUpperCaseWithSonderzeichen = "";

		// generate 4x4 random numbers
		Random ran = new Random();
		for (int i = 0; i < 5; i++) {
			String pwdPart = null;
			String number = null;
			do {
				int n = ran.nextInt((max - min) + 1) + min;
				number = String.valueOf(n);
				pwdPart = l.get(number);
			} while (pwdPart == null);
			System.out.println("Number: " + number +  "; Password part: " + pwdPart);
			pwd = new String(pwd + pwdPart);
			char first = Character.toUpperCase(pwdPart.charAt(0));
			pwdPart = first + pwdPart.substring(1);
			pwdUpperCase = pwdUpperCase + pwdPart;			
		}
		
		pwdUpperCaseWithSonderzeichen = new String(pwdUpperCase);
		// add some leetspeak replacements for more security
		if(ran.nextBoolean())
			pwdUpperCaseWithSonderzeichen = pwdUpperCaseWithSonderzeichen.replaceAll("a|A", "@");
		if(ran.nextBoolean())
			pwdUpperCaseWithSonderzeichen = pwdUpperCaseWithSonderzeichen.replaceAll("e|E", "3");
		if(ran.nextBoolean())
			pwdUpperCaseWithSonderzeichen = pwdUpperCaseWithSonderzeichen.replaceAll("i|I", "1");
		if(ran.nextBoolean())
			pwdUpperCaseWithSonderzeichen = pwdUpperCaseWithSonderzeichen.replaceAll("p|P", "9");
		if(ran.nextBoolean())
			pwdUpperCaseWithSonderzeichen = pwdUpperCaseWithSonderzeichen.replaceAll("o|O", "0");

		System.out.println("Password: " + pwd);
		System.out.println("Password with uppercase: " + pwdUpperCase);
		System.out.println("Password with uppercase and leetspeak: " + pwdUpperCaseWithSonderzeichen);

	}
}
