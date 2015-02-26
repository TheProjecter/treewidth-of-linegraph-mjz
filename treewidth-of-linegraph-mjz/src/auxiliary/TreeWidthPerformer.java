package auxiliary;

import graphTools.Graph;
import graphTools.GraphReader;

import java.io.IOException;


public class TreeWidthPerformer 
{
	
	private String inputDirectory = "";
	private String mjzStdFile = "";
	public TreeWidthPerformer(String inputDirectory, String mjzStdFile)
	{
		super();
		this.inputDirectory = inputDirectory;
		this.mjzStdFile = mjzStdFile;
	}
	
	public void calculateTW(String algorithm)
	{
		//change addresses
		Console.outln("The defualt address for input MJZ Std. data file is : "+this.inputDirectory+this.mjzStdFile);
		Console.out("Do you want to change it? (Y as Yes/Other characters as No!) : ");
		char ans = Console.nextChar();
		if(ans == 'Y' || ans == 'y')
		{
			Console.outln("\n-Please enter JUST the file name with extension at the end, e.g : "+this.mjzStdFile);
			Console.out("File name (Without path): ");
			this.mjzStdFile = Console.nextStr();
			Console.outln("\n-Please enter the full directory path with \\ or / at the end, e.g : "+inputDirectory);
			Console.out("Path (Without file name): ");
			this.inputDirectory = Console.nextStr();
		}
		
		try {
			
			GraphReader graphReader = new GraphReader(this.inputDirectory, this.mjzStdFile);
			long startTime,stopTime,elapsedTime;
			Graph g = null;
			int c =0;
			while((g = graphReader.nextGraph())!=null)
			{
				int ub = g.getTreeWidthUpperBound();
				int lb = g.getTreeWidthLowerBound();
				Console.out(c+"-\t"+g.getSpecificName() + " , Lb= "+lb+ " , Ub= "+ub+" --> TW"+algorithm+"= " );
				startTime = System.currentTimeMillis();
				int tw = 0;
				if(algorithm.compareTo("DP")==0)
					tw = g.getTreeWithByDynamicProgramming(ub);
				else if(algorithm.compareTo("RS")==0)
					tw = g.getTreeWithByRecursiveSingleThread();
				else if(algorithm.compareTo("RM")==0)
					tw = g.getTreeWithByRecursiveMultiThread();
				else if(algorithm.compareTo("BB")==0)
				{
					if(lb>0)
						tw = g.getTreeWidthByBranchAndBound();
					else
						Console.out(" Unconnected Graph!");
				}
				Console.out(""+tw);
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				Console.outln(" , exTime= "+elapsedTime);
				c++;				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calculateBoundaries(String OutputFilePath)
	{
		FileWritter fwS = new FileWritter(OutputFilePath);
		//change addresses
		Console.outln("The defualt address for input MJZ Std. data file is : "+this.inputDirectory+this.mjzStdFile);
		Console.out("Do you want to change it? (Y as Yes/Other characters as No!) : ");
		char ans = Console.nextChar();
		if(ans == 'Y' || ans == 'y')
		{
			Console.outln("\n-Please enter JUST the file name with extension at the end, e.g : "+this.mjzStdFile);
			Console.out("File name (Without path): ");
			this.mjzStdFile = Console.nextStr();
			Console.outln("\n-Please enter the full directory path with \\ or / at the end, e.g : "+inputDirectory);
			Console.out("Path (Without file name): ");
			this.inputDirectory = Console.nextStr();
		}
		Console.out("Do you need print results in console also? (Y as Yes/Other characters as No!) : ");
		ans = Console.nextChar();
		boolean print = (ans == 'Y' || ans == 'y') ? true : false;
		try {
			GraphReader graphReader = new GraphReader(this.inputDirectory, this.mjzStdFile);
			long startTime,stopTime,elapsedTime;
			Graph g = null;
			Graph lg = null;
			while((g = graphReader.nextGraph())!=null)
			{
				lg = g.getLineGraph();
				startTime = System.currentTimeMillis();
				int lgTwUb = lg.getTreeWidthUpperBound();
				int lgTwLb = lg.getTreeWidthLowerBound();
				int lgTwUbHAndW = g.getBestHarveyAndWoodTWLGUpperBound();
				int lgTwLbHAndW = g.getBestHarveyAndWoodTWLGLowerBound();
				String line = g.getSpecificName() + " , H&W[ "+lgTwLbHAndW+" <= Tw(L(G)) <= "+lgTwUbHAndW+ "] , B&B[ "+lgTwLb+" <= Tw(L(G)) <= "+lgTwUb+ "]";
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				if(lgTwLb<0)
					line +=(" , exTime= "+elapsedTime+" , Unconnected Graph.");
				else
					line +=(" , exTime= "+elapsedTime+" .");
				fwS.appendNextLine(line);
				if(print)
					Console.outln(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fwS.close();
		}
	}
	
}
