package main;

import auxiliary.Console;
import auxiliary.Directory;
import auxiliary.Menu;

/**
 * The TreeWidthLineGraph class is the main class that program starts from it.
 * @version   01.02   13.01.2015
 * @author    Mahdi Jaberzadeh Ansari
 * @since	  October 2014
 * 
 */
public class TreeWidthLineGraph 
{
	private static String inputDirectory = "./datain/";
	private static String outputDirectory = "./dataout/";
	private static String pascalStdFile = "graphs.txt";
	private static String mjzStdFile = "graphs.dat";
	private static String sortedMjzStdFile = "sGraphs.dat";
	private static String lineGraphFile = "lGraphs.dat";
	private static String sortedLineGraphFile = "slGraphs.dat";
	private static String boundariesFile = "boundsTWLG.txt";
	private static String originalGraphMatricesDir;
	private static String lineGraphMatricesDir;
	/**
	 * The main function that starts the program.
	 * It makes a menu and then calls its start to handle user requests.
	 * It can execute on Windows OS or Linux OS.
	 * @param args We don't use args in this program.
	 */
	public static void main(String[] args) 
	{
		Console.outln("		<<<Uni-Bonn TreeWidth of LineGraph!>>>");
		String userHome = System.getProperty( "user.home" );
		boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux") ? true : false;
		if(isLinux)
		{
			Console.outln("It seems you are using Linux base OS.");
			Console.outln("This program needs GraphViz2.38 please use the following instruction to install it.");
			Console.outln("~$ sudo apt-get install graphviz");
			originalGraphMatricesDir = userHome+"/twlg/OriginalGraphMatrices/";
			lineGraphMatricesDir = userHome+"/twlg/LineGraphMatrices/";
			Directory.makeFolder(userHome+"/twlg/");
		}
		else{
			Console.outln("It seems you are using Windows base OS.");
			Console.outln("This program needs GraphViz2.38 please download it from http://www.graphviz.org/");
			Console.outln("and install it in the following path : C:\\Program Files (x86)\\Graphviz2.38\\");
			Console.outln("Or if you can install other versions and not 2.38 then \nbecome sure `dot` can be executed through console,\notherwise you must put it in path.");
			originalGraphMatricesDir = userHome+"\\twlg\\OriginalGraphMatrices\\";
			lineGraphMatricesDir = userHome+"\\twlg\\LineGraphMatrices\\";
			Directory.makeFolder(userHome+"\\twlg\\");
		}
		
		Menu menu = new Menu(inputDirectory, outputDirectory, pascalStdFile, mjzStdFile, 
							 sortedMjzStdFile, lineGraphFile, sortedLineGraphFile, 
							 originalGraphMatricesDir, lineGraphMatricesDir, boundariesFile, isLinux);
		menu.start();
	}

}
