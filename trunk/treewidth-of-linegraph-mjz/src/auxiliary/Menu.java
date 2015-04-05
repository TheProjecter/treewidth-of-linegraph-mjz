package auxiliary;


public class Menu {

	private String inputDirectory = "./";
	private String outputDirectory = "./";
	private String pascalStdInputFile = "graphs.txt";
	private String mjzStdFile = "graphs.dat";
	private String sortedMjzStdFile = "sGraphs.dat";
	private String lineGraphFile = "lGraphs.dat";
	private String sortedLineGraphFile = "slGraphs.dat";
	private String boundariesFile = "boundsTWLG.txt";
	private String twdpFile = "twdp.txt";
	private String twrsFile = "twrs.txt";
	private String twrmFile = "twrm.txt";
	private String twbbFile = "twbb.txt";
	private String originalGraphMatricesDir = "";
	private String lineGraphMatricesDir = "";
	private final int maxMenu = 15;//must be 1 more than biggest menu number
	private boolean isLinux;
	
	public Menu(String inputDirectory, String outputDirectory, String pascalStdInputFile, String mjzStdFile,
			    String sortedMjzStdFile, String lineGraphFile, String sortedLineGraphFile,
			    String originalGraphMatricesDir, String lineGraphMatricesDir, String boundariesFile, boolean isLinux) 
	{
		super();
		this.inputDirectory = inputDirectory;
		this.outputDirectory = outputDirectory;
		this.pascalStdInputFile = pascalStdInputFile;
		this.mjzStdFile = mjzStdFile;
		this.sortedMjzStdFile = sortedMjzStdFile;
		this.lineGraphFile = lineGraphFile;
		this.sortedLineGraphFile = sortedLineGraphFile;
		this.boundariesFile = boundariesFile;
		this.originalGraphMatricesDir = originalGraphMatricesDir;
		this.lineGraphMatricesDir = lineGraphMatricesDir;
		this.isLinux = isLinux;
		Directory.makeFolder(this.outputDirectory);
		Directory.makeFolder(this.originalGraphMatricesDir);
		Directory.makeFolder(this.lineGraphMatricesDir);
	}
	private void showMenu()
	{
		Console.outln("\n#Please select one of the menu and enter the number.");
		Console.outln(" 1-Change Pascal Std. To MJZ Std.");
		Console.outln(" 2-Create Visualized Graph Diagram From source File");
		Console.outln(" 3-Create Visualized Matrix Diagram From source File");
		Console.outln(" 4-Create Sorted Graph Data");
		Console.outln(" 5-Create LineGraph File");
		Console.outln(" 6-Create Visualized Graph Diagram From LineGraphs");
		Console.outln(" 7-Create Visualized Matrix Diagram From LineGraphs");
		Console.outln(" 8-Create Sorted LineGraph Data");
		Console.outln(" 9-Create Bounds list for TW(L(G)) List from original graphs");
		Console.outln("10-Create TW(L(G)) list with Dynamic Programming");
		Console.outln("11-Create TW(L(G)) list with Recursive with SingleThread approach");
		Console.outln("12-Create TW(L(G)) list with Recursive with MultiThread approach");
		Console.outln("13-Create TW(L(G)) list with Branch And Bound approach");
		Console.outln("14-Exit");
		Console.out("Your choice (Just a Number): ");
	}
	public void start(){
		
		while(true){
			this.showMenu();
			int ans = this.readAns();
			if(ans==1)//Change Pascal Std. To MJZ Std.
			{
				ConvertPascalStdToMJZStd pscStd2MjzStd = new ConvertPascalStdToMJZStd(inputDirectory, outputDirectory, pascalStdInputFile, mjzStdFile);
				pscStd2MjzStd.convert();
			}
			else if(ans==2)//Create Visualized Graph Diagram From source File
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, mjzStdFile);
				Console.outln("\n >>> Check the folder '"+originalGraphMatricesDir+"' for pictures.");
				vog.saveVisualized(originalGraphMatricesDir, false, isLinux);
			}
			else if(ans==3)//Create Visualized Matrix Diagram From source File
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, mjzStdFile);
				Console.outln("\n >>> Check the folder '"+originalGraphMatricesDir+"' for pictures.");
				vog.saveVisualized(originalGraphMatricesDir, true, isLinux);
			}
			else if(ans==4)//Create Sorted Graph Data
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, mjzStdFile);
				Console.outln("\n >>> Check the folder '"+outputDirectory+"' for : "+sortedMjzStdFile);
				vog.saveSorted(sortedMjzStdFile, true);
			}
			else if(ans==5)//Create LineGraph File
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, mjzStdFile);
				Console.outln("\n >>> Check the folder '"+outputDirectory+"' for : "+lineGraphFile);
				vog.createLineGraphFile(lineGraphFile);
			}
			else if(ans==6)//Create Visualized Graph Diagram From LineGraphs
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, lineGraphFile);
				Console.outln("\n >>> Check the folder '"+lineGraphMatricesDir+"' for pictures.");
				vog.saveVisualized(lineGraphMatricesDir, false, isLinux);
			}
			else if(ans==7)//Create Visualized Matrix Diagram From LineGraphs
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, lineGraphFile);
				Console.outln("\n >>> Check the folder '"+lineGraphMatricesDir+"' for pictures.");
				vog.saveVisualized(lineGraphMatricesDir, true, isLinux);
			}
			else if(ans==8)//Create Sorted Graph Data
			{
				VisualizeAndSortGraphs vog = new VisualizeAndSortGraphs(outputDirectory, lineGraphFile);
				Console.outln("\n >>> Check the folder '"+outputDirectory+"' for : "+sortedLineGraphFile);
				vog.saveSorted(sortedLineGraphFile, true);
			}
			else if(ans==9)
			{
				TreeWidthPerformer twp = new TreeWidthPerformer(outputDirectory, sortedMjzStdFile);
				Console.outln("\n >>> Check the file '"+outputDirectory+boundariesFile+"' for boundaries.");
				twp.calculateBoundaries(outputDirectory+boundariesFile);
			}
			else if(ans==10)
			{
				TreeWidthPerformer twp = new TreeWidthPerformer(outputDirectory, sortedLineGraphFile);
				Console.outln("\n >>> Check the file '"+outputDirectory+twdpFile+"' for results.");
				twp.calculateTW("DP",outputDirectory+twdpFile);
			}
			else if(ans==11)
			{
				TreeWidthPerformer twp = new TreeWidthPerformer(outputDirectory, sortedLineGraphFile);
				Console.outln("\n >>> Check the file '"+outputDirectory+twrsFile+"' for results.");
				twp.calculateTW("RS",outputDirectory+twrsFile);
			}
			else if(ans==12)
			{
				TreeWidthPerformer twp = new TreeWidthPerformer(outputDirectory, sortedLineGraphFile);
				Console.outln("\n >>> Check the file '"+outputDirectory+twrmFile+"' for results.");
				twp.calculateTW("RM",outputDirectory+twrmFile);
			}
			else if(ans==13)
			{
				TreeWidthPerformer twp = new TreeWidthPerformer(outputDirectory, sortedLineGraphFile);
				Console.outln("\n >>> Check the file '"+outputDirectory+twbbFile+"' for results.");
				twp.calculateTW("BB",outputDirectory+twbbFile);
			}
			else
				break;
		}
	}
	private int readAns() {
		int ans = Console.nextInt(0, maxMenu);
		return ans;
	}
	
}
