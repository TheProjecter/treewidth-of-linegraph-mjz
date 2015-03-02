package auxiliary;

import java.io.IOException;


public class ConvertPascalStdToMJZStd {
	
	PascalStdConvertor psc;
	String inputDirectory = "";
	String outputDirectory = "";
	String pascalStdInputFile = "";
	String mjzStdOutputFile = "";
	
	public ConvertPascalStdToMJZStd(String inputDirectory, String outputDirectory, String pascalStdInputFile, String mjzStdOutputFile)
	{
		this.inputDirectory = inputDirectory;
		this.outputDirectory = outputDirectory;
		this.pascalStdInputFile = pascalStdInputFile;
		this.mjzStdOutputFile = mjzStdOutputFile;
	}
	public void convert(){
		//change addresses
		Console.outln("The default address for input Pascal Std. data file is : "+inputDirectory+pascalStdInputFile);
		Console.out("Do you want to change it? (Y as Yes/Other characters as No!) : ");
		char ans = Console.nextChar();
		if(ans == 'Y' || ans == 'y')
		{
			Console.outln("Please enter JUST the file name with extension at the end, e.g : "+pascalStdInputFile);
			Console.out("File name (Without path): ");
			this.pascalStdInputFile = Console.nextStr();
			Console.outln("Please enter the full directory path with \\ or / at the end, e.g : "+inputDirectory);
			Console.out("Path (Without file name): ");
			this.inputDirectory = Console.nextStr();
		}
		//
		try{
			Console.outln("\nRead data from : "+this.inputDirectory+this.pascalStdInputFile);
			Console.outln("Please wait, we are converting the Pascal Standard to MJZ Standard.");
			psc = new PascalStdConvertor(this.inputDirectory, this.outputDirectory, this.pascalStdInputFile, this.mjzStdOutputFile);
			psc.convert();
			Console.outln("The MJZ standard output file was created successfully.");
			psc.finilize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
