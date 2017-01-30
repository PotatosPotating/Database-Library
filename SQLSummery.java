import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.*;

public class SQLSummery {

	public static void main(String[] args) {
		String fileName="default";
		String line = "";
		System.out.println("Welcome to SQL Summary Parser by Pratik Hingornai");
		System.out.println("Please make sure the SQL file is in the same Directory and enter its name below.");
		Scanner fileNameScanner = new Scanner(System.in);
		fileName = fileNameScanner.next();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader buffer = new BufferedReader(fileReader);
		int queryCount = 0;
		try {
			while((line=buffer.readLine())!=null){

				String subject = "";
				String values = "";
				try{
					subject = line.substring(0, line.indexOf(" "));
					values = line.substring(line.indexOf(' ')+1);				
				}catch(IndexOutOfBoundsException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				values = values.replaceAll("AND", ",");
				values = values.replaceAll("OR", ",");
				values = values.replaceAll(";", "");
				values = values.trim();
				String[] valuesArray = values.split(",");
				int valuesCount = valuesArray.length;
				valuesArray=valueFix(valuesArray);
				System.out.println("");
				if(subject.equals("SELECT")) {
					queryCount++;
					System.out.println("");
					System.out.println("Query#"+queryCount);
				}
				System.out.print(subject+" ("+valuesCount+"): ");
				for(int i=0;i<valuesArray.length;i++)
				{
					System.out.print(valuesArray[i]);
					if(i == valuesArray.length-1)
					{}
					else
					{
						System.out.print(",");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String[] valueFix(String[] valuesArray) {
		// TODO Auto-generated method stub
		for(int i=0;i<valuesArray.length;i++) {
			String value = valuesArray[i].trim();
			//sorry got a little lazy here
			if(value.charAt(0)=='0'||value.charAt(0)=='1'||value.charAt(0)=='2'||value.charAt(0)=='3'||value.charAt(0)=='4'||value.charAt(0)=='5'||value.charAt(0)=='6'||value.charAt(0)=='7'||value.charAt(0)=='8'||value.charAt(0)=='9')
			{
				String[] temp = value.split("["+Pattern.quote("+-*/<>!")+"]");
				String operator = null;
				if(value.contains("+"))
					operator = "+";
				if(value.contains("-"))
					operator = "-";
				if(value.contains("*"))
					operator = "*";
				if(value.contains("/"))
					operator = "/";
				if(value.contains("%"))
					operator = "%";
				if(value.contains("="))
					operator = "=";
				if(value.contains("!="))
					operator = "!=";
				if(value.contains("<>"))
					operator = "<>";
				if(value.contains(">"))
					operator = "<";
				if(value.contains("<"))
					operator = ">";
				if(value.contains(">="))
					operator = "<=";
				if(value.contains("<="))
					operator = ">=";
				if(value.contains("!<"))
					operator = "!>";
				if(value.contains("!>"))
					operator = "!<";
				valuesArray[i] = temp[1]+operator+temp[0];
			}
		}
		return valuesArray;
	}

}