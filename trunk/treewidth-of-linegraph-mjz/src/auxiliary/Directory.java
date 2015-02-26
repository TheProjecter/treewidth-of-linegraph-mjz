package auxiliary;

import java.io.File;

public class Directory 
{
	public static boolean makeFolder(String path, String folderName)
	{
		  File theDir = new File(path+folderName);
		  // if the directory does not exist, create it
		  if (!theDir.exists()) 
		  {
		    System.out.println("creating directory: " + folderName);
		    boolean result = false;
	
		    try{
		        theDir.mkdir();
		        result = true;
		     } catch(SecurityException se){
		        return false;
		     }        
		     if(result) {    
		       System.out.println("DIR created");  
		     }
		  }
		  return true;
	}
	public static boolean makeFolder(String fullPath)
	{
		  File theDir = new File(fullPath);
		  // if the directory does not exist, create it
		  if (!theDir.exists()) 
		  {
		    System.out.println("creating directory: " + fullPath);
		    boolean result = false;
	
		    try{
		        theDir.mkdir();
		        result = true;
		     } catch(SecurityException se){
		        return false;
		     }        
		     if(result) {    
		       System.out.println("DIR created");  
		     }
		  }
		  return true;
	}
	public static boolean isExistFolder(String fullPath){
		File theDir = new File(fullPath);
		  // if the directory does not exist, create it
		return theDir.exists();
	}
	
}
