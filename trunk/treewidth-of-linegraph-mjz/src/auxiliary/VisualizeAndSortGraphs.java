package auxiliary;

import graphTools.Graph;
import graphTools.GraphReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class VisualizeAndSortGraphs {
	
	private String inputDirectory = "";
	private String mjzStdFile = "";
	public VisualizeAndSortGraphs(String inputDirectory, String mjzStdFile)
	{
		super();
		this.inputDirectory = inputDirectory;
		this.mjzStdFile = mjzStdFile;
	}
	
	public void saveVisualized(String outputDirectory, boolean matrixDiagram, boolean isLinux)
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
		//output type
        String type = "gif";
//      String type = "dot";
//      String type = "fig";    // open with xfig
//      String type = "pdf";
//      String type = "ps";
//      String type = "svg";    // open with inkscape
//      String type = "png";
//      String type = "plain";
		//
		try {
			
			GraphReader graphReader = new GraphReader(this.inputDirectory, this.mjzStdFile);
			Graph g = null;
			GraphViz gv = new GraphViz(isLinux, null);
			while((g = graphReader.nextGraph())!=null)
			{
				if(matrixDiagram)
					g.makePhoto(outputDirectory);
				else
				{
					gv.clear();
					gv.add(g.getGraphGraphVizDotString());
					File out = new File(outputDirectory+g.getSpecificName()+"."+type);
					gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSorted(String outputFile,boolean sortOverlay)
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
		//
		try {
			
			GraphReader graphReader = new GraphReader(this.inputDirectory, this.mjzStdFile);
			Graph g = null;
			
			FileWritter fwS = new FileWritter(this.inputDirectory+outputFile);
			if(sortOverlay)
			{
				List<Graph> graphList = new ArrayList<Graph>();
				while((g = graphReader.nextGraph())!=null)
					graphList.add(g);
				Collections.sort(graphList, new Comparator<Graph>() 
								{
							        @Override
							        public int compare(Graph  x, Graph  y)
							        {
							        	if(x.getVerticesNum()<y.getVerticesNum())
							        		return -1;
							        	else if(x.getVerticesNum()>y.getVerticesNum())
							        		return +1;
							        	else if(x.getVerticesNum()==y.getVerticesNum() && x.getEdgesNum() == y.getEdgesNum())
							        		return 0;
							        	else if(x.getVerticesNum()==y.getVerticesNum() && x.getEdgesNum() < y.getEdgesNum())
							        		return -1;
							        	else 
							        		return  +1;
							        }
							    }
				);
				
				for(int i=0;i<graphList.size();i++)
				{
					g = graphList.get(i);
					fwS.appendNextLine(g.getGraphInfo());
					fwS.appendNextLine(g.getGraphEdges());				
				}
				fwS.close();
			}else
			{
				while((g = graphReader.nextGraph())!=null)
				{
					fwS.appendNextLine(g.getGraphInfo());
					fwS.appendNextLine(g.getGraphEdges());				
				}
				fwS.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//
	public void createLineGraphFile(String lineGraphFile)
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
		//
		try {
			
			GraphReader graphReader = new GraphReader(this.inputDirectory, this.mjzStdFile);
			FileWritter fwS = new FileWritter(this.inputDirectory+lineGraphFile);
			Graph g = null;
			while((g = graphReader.nextGraph())!=null)
			{
				Graph lg = g.getLineGraph();
				fwS.appendNextLine(lg.getGraphInfo());
				fwS.appendNextLine(lg.getGraphEdges());				
			}
			fwS.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
