package auxiliary;

import java.io.IOException;

public class PascalStdConvertor {
	private FileReader fr;
	private FileWritter fw;
	private String indir;
	private String outdir;
	private String inFileName;
	private String outFileName;
	private boolean status;
	public PascalStdConvertor(String indir, String outdir, String inputFileName, String outFileName) throws IOException 
	{
		super();
		this.status = false;
		this.indir = indir;
		this.outdir = outdir;
		this.inFileName = inputFileName;
		this.fr = new FileReader(this.indir, inFileName);
		if(!this.fr.getSituation())
			throw new IOException("The passed file is not exist.");
		this.outFileName = outFileName;
		this.fw = new FileWritter(this.outdir+this.outFileName);
		if(!this.fw.getSituation())
			throw new IOException("The passed directory is not exist.");
		this.status = true;
	}
	public boolean getStatus() {
		return status;
	}
	public void finilize(){
		this.fr.close();
		this.fw.close();		
	}
	public void convert() {
		if(this.getStatus())
		{
			String line1 = null;
			@SuppressWarnings("unused")
			String line2 = null;
			String line3 = null;
			int lineNum = 0;
			while((line1=this.fr.readNextLine())!=null && 
				  (line2=this.fr.readNextLine())!=null && 
				  (line3=this.fr.readNextLine())!=null)
			{
					if(line1.compareTo("$ ")!=0)
					{
						String delimiter = "[ ]{1}";
						String[] info = line1.split(delimiter);
						if(info.length == 5)
						{
							int verticeNums = Integer.parseInt(info[3]);
							int edgeNums = Integer.parseInt(info[4]);
							String[] edgesInfo = line3.split(delimiter);
							if((edgesInfo.length/3) == edgeNums)
							{
								this.fw.appendNextLine("# "+info[1]+" "+verticeNums+" "+edgeNums);
								for(int i=0;i<edgesInfo.length;i+=3)
								{
									int firstIndex = Integer.parseInt(edgesInfo[i])-1;
									int secondIndex = Integer.parseInt(edgesInfo[i+1])-1;
									this.fw.appendNextData(firstIndex+","+secondIndex+";");
								}
								this.fw.appendNextData("\n");
							}
							else
							{
								Console.outln("There is an error in converting data of graph number "+lineNum+1);
								Console.outln(line1);
							}
						}
						
					}
					else
						break;//end of file reading!
				lineNum +=3;
			}
		}
	}
	
	
}
