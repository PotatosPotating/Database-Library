import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Verifier {

	public static void main(String[] args) {
		Scanner s =null;
		Scanner leftReader;
		Scanner rightReader;
		HashMap<String, String> hmap = new HashMap<String, String>();
		boolean flag=true;


		//Read data from the URL into a scanner
		System.out.println("Welcome to Funtional DependencyVerifier by Pratik Hingornai");
		System.out.println("This programs reads 'http://rasinsrv07.cstcis.cti.depaul.edu/CSC553/data/dwdate.tbl'");
		try {
			URL url = new URL("http://rasinsrv07.cstcis.cti.depaul.edu/CSC553/data/dwdate.tbl");
			s = new Scanner(url.openStream());
		}catch(IOException ex)
		{
			System.out.println("URL is unavailable");
		}



		//Enter columns on left hamd side of the equation
		System.out.println("Enter the column numbers on the left hand side of with spaces between the column number =>");
		leftReader = new Scanner(System.in);
		String leftLine = leftReader.nextLine();    
		String[] leftLines = leftLine.trim().split("\\s+");
		ArrayList<Integer> leftInts = new ArrayList<Integer>();
		for (int i = 0; i <= leftLines.length-1; i++) {
			int columnCheck= Integer.parseInt(leftLines[i]);
			if(columnCheck > 17 || columnCheck < 1)
			{
				System.out.println("Column "+columnCheck+" is not in the table. Please enter columns betwen 1 to 17 as there are only 17 columns in the table");
				continue;
			}
			leftInts.add(Integer.parseInt(leftLines[i])-1);
		}




		//Enter columns on right hand side of equation
		System.out.println("Enter the column numbers on the right hand side of with spaves between the column number =>");
		rightReader = new Scanner(System.in);
		String rightLine = rightReader.nextLine();    
		String[] rightLines = rightLine.trim().split("\\s+");
		ArrayList<Integer> rightInts = new ArrayList<Integer>();
		for (int i = 0; i <= rightLines.length-1; i++) {
			int columnCheck= Integer.parseInt(rightLines[i]);
			if(columnCheck > 17 || columnCheck < 1)
			{
				System.out.println("Column "+columnCheck+" is not in the table. Please enter columns betwen 1 to 17 as there are only 17 columns in the table");
				continue;
			}
			rightInts.add(Integer.parseInt(rightLines[i])-1);
		}



		//Divide and store every element in record in the form of a hash map. If a colusion occures then there is no FD.
		while(s.hasNext())
		{
			String line =s.nextLine();
			String[] lines = line.split("\\|");
			String left = "";
			String right = "";
			for(int l=0;l<lines.length;l++)
			{
				if(leftInts.contains(l))
				{
					left = ""+left+lines[l];
				}
				if(rightInts.contains(l))
				{
					right = ""+right+lines[l];
				}
			}
			if(hmap.containsKey(left))
			{
				if(!hmap.get(left).equals(right))
				{
					System.out.println("The relationship is not functionally dependant.");
					flag = false;
					break;
				}
			}
			hmap.put(left, right);
			//**please don't run this. It is just bad code. Needs a super computer.
			//
			//			for (Map.Entry<String, String> entry : hmap.entrySet()) {
			//			    String key = entry.getKey().toString();
			//			    String value = entry.getValue().toString();
			//			    System.out.println(key + " ===== " + value);
			//			}			
		}
		if(flag==true)
			System.out.println("The relationship is functionally dependant");
	}
}
