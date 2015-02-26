package auxiliary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console 
{
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void out(String str)
	{
		System.out.print(str);
	}
	public static void outln(String str)
	{
		System.out.println(str);
	}
	public static int nextInt()
	{
		
        String buf = null;
        int intNum = 0;
        while(buf == null)
        {
        	try 
        	{
				buf = br.readLine();
				if(buf!=null)
				{
					try{
						intNum= Integer.parseInt(buf);
						break;
					}
					catch(NumberFormatException e)
					{
						buf = null;
						System.out.print("Please enter a true integer number : ");
					}
					
				}
			} 
        	catch (IOException e) 
			{
				buf = null;
				e.printStackTrace();
			}
             
        }
        return intNum;
	}
	/**
	 *Read an integer between min and max and return if successful
	 *@param min	the lower band for entered number
	 *@param max	the upper band for entered number
	 **/
	public static int nextInt(int min, int max)
	{
		
        String buf = null;
        int intNum = 0;
        while(buf == null)
        {
        	try 
        	{
				buf = br.readLine();
				if(buf!=null)
				{
					try{
						intNum= Integer.parseInt(buf);
						if(intNum<=min || intNum>=max)
						{
							buf = null;
							intNum = 0;
							System.out.print("Please enter a true integer number with respect to this condition ("+min+"<n<"+max+") : ");
						}
						else
						break;
					}
					catch(NumberFormatException e)
					{
						buf = null;
						System.out.print("Please enter a true integer number : ");
					}
					
				}
			} 
        	catch (IOException e) 
			{
				buf = null;
				e.printStackTrace();
			}
             
        }
        return intNum;
	}
	public static char nextChar()
	{
        String buf = null;
        char ch = 0;
        while(ch == 0)
        {
	        try {
				buf = br.readLine();
				ch = buf.charAt(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return ch;
	}
	public static String nextStr()
	{
        String buf = null;
        while(buf == null)
        {
	        try {
				buf = br.readLine();
			} catch (IOException e) {
				System.err.println("Please enter a string and press enter at the end.");
				e.printStackTrace();
			}
        }
        return buf;
	}
	public static double nextDouble()
	{
		
        String buf = null;
        double doubleNum = 0d;
        while(buf == null)
        {
        	try 
        	{
				buf = br.readLine();
				if(buf!=null)
				{
					try{
						doubleNum= Double.parseDouble(buf);
						break;
					}
					catch(NumberFormatException e)
					{
						buf = null;
						System.out.print("Please enter a true floating point number : ");
					}
					
				}
			} 
        	catch (IOException e) 
			{
				buf = null;
				e.printStackTrace();
			}
             
        }
        return doubleNum;
	}
}
