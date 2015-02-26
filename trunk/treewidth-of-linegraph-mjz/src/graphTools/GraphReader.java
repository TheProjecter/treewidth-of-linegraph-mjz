package graphTools;

import auxiliary.Console;
import auxiliary.FileReader;

public class GraphReader 
{
	private FileReader fr;
	private String dir;
	private String inFileName;
	private boolean status;
	public GraphReader(String dir, String inFileName) 
	{
		super();
		this.dir = dir;
		this.inFileName = inFileName;
		this.status = false;
		this.fr = new FileReader(this.dir, this.inFileName);
		String str = fr.readNextLine();
		if(str != null && str.compareTo("%MJZ Graph Standard")==0)
			this.status = true;
		else
			Console.outln("Cruppted File, can not read this file.");
	}
	
	public boolean getStatus() {
		return status;
	}

	public Graph nextGraph() throws Exception
	{
		Graph graph=null;
		if(this.getStatus())
		{
			String buf = this.fr.readNextLine();
			if(buf!=null && buf.length()>0 && buf.charAt(0)=='#')
			{
				String delim1 = "[ ]{1}";
				String delim2 = "[;]{1}";
				String delim3 = "[,]{1}";
				String[] info = buf.split(delim1);
				if(info.length==4)
				{
					String name = info[1];
					int verticesNums = Integer.parseInt(info[2]);
					int edgeNums = Integer.parseInt(info[3]);
					graph = new Graph(name, verticesNums, edgeNums);
					buf = this.fr.readNextLine();
					if(buf!=null && buf.length()>0)
					{
						String[] edges=buf.split(delim2);
						if(edges.length == edgeNums)
						{
							for(int i=0; i<edges.length;i++)
							{
								String[] elements = edges[i].split(delim3);
								if(elements.length==2)
								{
									int node1 = Integer.parseInt(elements[0]);
									int node2 = Integer.parseInt(elements[1]);
									graph.addEdge(node1, node2);
								}
							}
						}
						else
							throw new Exception("Corrupted file.");
					}
				}
			}
		}
		return graph;
	}
}
