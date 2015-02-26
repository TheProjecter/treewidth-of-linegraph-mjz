package auxiliary;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader 
{
	private String path;
	private Path file;
	private BufferedReader reader;
	private boolean situation;
	public FileReader(String dir, String fileName)
	{
		this.situation =false;
		this.path = dir+fileName;
		this.file = Paths.get(this.path);
		try {
			this.reader = Files.newBufferedReader(this.file, Charset.defaultCharset());
			this.situation = true; //file existence OK
		} catch (IOException e) {
			System.out.println("This file is not exist: "+this.path);
		}
	}
	public String readNextLine()
	{
		String line = null;
		try {
			line = reader.readLine();
		} catch (Exception e) {
			System.out.println("Accessing Error!");
			this.situation = false;
			this.close();
		}
		return line;//returns null if fail to read
	}
	public boolean getSituation() {
		return situation;
	}
	public void close()
	{
		try {
			reader.close();
			this.situation = false;
		} catch (IOException e) {
			System.out.println("Connection close operation error!");
			e.printStackTrace();
		}
	}
}
