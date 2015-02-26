package auxiliary;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileWritter 
{
	private String path;
	private File file;
	private boolean situation;
	private FileWriter fileWritter;
	private BufferedWriter bufferWritter;
	public FileWritter(String path)
	{
		this.situation =false;
		this.path = path;
		this.file = new File(this.path);
		if(!file.exists())
		{
			try {
				file.createNewFile();
				this.fileWritter = new FileWriter(file.getPath(),true);
				this.bufferWritter = new BufferedWriter(this.fileWritter);
				this.bufferWritter.write("%MJZ Graph Standard\n");
				this.situation = true;
			} catch (IOException e) {
				System.out.println("Problem in creating output file.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			//make the file empty			
			try {
				this.fileWritter = new FileWriter(file.getPath());
				this.bufferWritter = new BufferedWriter(this.fileWritter);
		        this.bufferWritter.write("%MJZ Graph Standard\n");
		        this.situation = true;
			} catch (IOException e) {
				System.out.println("Problem in writing in output file.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	        
		}
	}
	public boolean appendNextData(String data)
	{
		try
		{
	        this.bufferWritter.write(data);
	        return true;
		} catch (IOException e) {
			System.out.println("Problem in writing in output file.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			this.situation = false;
			this.close();
		}
		return false;
	}
	public boolean appendNextLine(String data)
	{
		try
		{
	        this.bufferWritter.write(data+"\n");
	        return true;
		} catch (IOException e) {
			System.out.println("Problem in writing in output file.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			this.situation = false;
			this.close();
		}
		return false;
	}
	public boolean getSituation() {
		return situation;
	}
	public boolean close(){
		try {
			this.bufferWritter.close();
			this.fileWritter.close();
			this.situation = false;
			return true;
		} catch (IOException e) {
			System.out.println("Problem in closing output file.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
