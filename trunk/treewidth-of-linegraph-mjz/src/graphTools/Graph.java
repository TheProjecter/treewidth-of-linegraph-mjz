package graphTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import auxiliary.Console;
import auxiliary.Result;
import auxiliary.SetOperation;
/**
 * The Graph class is a model for undirected graphs that is efficient optimized as memory usage.
 * It can calculate and visualize LineGraph of a given graph.
 * It can calculate lower and upper bound of TreeWidth of a graph.
 * It can calculate TreeWidth by Dynamic Programming and Recursive algorithm in single and multy thread modes.
 * Future plan is calculate TreeWidth by QuickBB algorithm from Ref2.
 * The functions for calculating TreeWidth and Lower and Upper bound of TW of Graph are from Ref1 and Ref2 that can be found in Ref folder.
 * @author MJZ
 * @version 01.02
 */
public class Graph 
{
	String name;
	private ArrayList<Integer> edgeSide1;
	private ArrayList<Integer> edgeSide2;
	private ArrayList<Integer> degrees;
	private ArrayList<Integer> maps;
	private int verticesNum;
	private int edgesNum;
	public Graph(String name, int verticesNum, int edgesNum) {
		super();
		this.name = name;
		this.verticesNum = verticesNum;
		this.edgesNum = 0;
		this.edgeSide1 = new ArrayList<Integer>(edgesNum);
		this.edgeSide2 = new ArrayList<Integer>(edgesNum);
		this.degrees = new ArrayList<Integer>(verticesNum);
		for(int i=0;i<verticesNum;i++)
			this.degrees.add(0);
		this.maps = new ArrayList<Integer>(verticesNum);
		for(int i=0;i<verticesNum;i++)
			this.maps.add(i);
	}
	/**
	 * This function prints the adjacency matrix of Graph.
	 */
	public void printGraph()
	{
		boolean[] temporalRow;
		Console.out("\n");
		for(int r=0;r<this.getVerticesNum();r++)
		{
			temporalRow = this.getRow(r);
			for(int c=0;c<this.getVerticesNum();c++)
			{
				Console.out(temporalRow[c]?" 1 ":" 0 ");
			}
			Console.out("\n");
		}
	}
	/**
	 * This function makes a black and white PNG photo of adjacency matrix.
	 * @param graphMatricesDir The destination directory
	 * @return  True if the operation is successful, otherwise returns false.
	 */
	public boolean makePhoto(String graphMatricesDir)
	{
		int scale = 3;
		int width = scale*this.getVerticesNum(); // Dimensions of the image
        int height = scale*this.getVerticesNum();
        BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster raster = im.getRaster();
	       
		boolean[] temporalRow;
		for(int r=0;r<this.getVerticesNum();r++)
		{
			temporalRow = this.getRow(r);
			for(int c=0;c<this.getVerticesNum();c++)
			{
				for(int x=r*scale;x<width && x<r*scale+scale;x++)
					for(int y=c*scale;y<height && y<c*scale+scale;y++)
						raster.setSample(x, y, 0, temporalRow[c]?1:0);
			}
		}
	   // Store the image using the PNG format.
       try {
    	   ImageIO.write(im,"PNG",new File(graphMatricesDir+"N("+this.getName()+")_V("+this.getVerticesNum()+")_E("+this.getEdgesNum()+")_S("+height+"x"+width+").png"));
    	   return true;
       } 
       catch (IOException e) {
    	   e.printStackTrace();
       }
       return false;
	}
	/**
	 * This function makes a black and white PNG photo of adjacency matrix and also prints the matrix.
	 * @param graphMatricesDir The destination directory path.
	 */
	public void makePhotoAndPrint(String graphMatricesDir)
	{
		int scale = 3;
		int width = scale*this.getVerticesNum(); // Dimensions of the image
        int height = scale*this.getVerticesNum();
        BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster raster = im.getRaster();
	       
		boolean[] temporalRow;
		Console.out("\n");
		for(int r=0;r<this.getVerticesNum();r++)
		{
			temporalRow = this.getRow(r);
			for(int c=0;c<this.getVerticesNum();c++)
			{
				Console.out(temporalRow[c]?" 1 ":" 0 ");
				for(int x=r*scale;x<width && x<r*scale+scale;x++)
					for(int y=c*scale;y<height && y<c*scale+scale;y++)
						raster.setSample(x, y, 0, temporalRow[c]?1:0);
			}
			Console.out("\n");
		}
	   // Store the image using the PNG format.
       try {
    	   ImageIO.write(im,"PNG",new File(graphMatricesDir+"N("+this.getName()+")_V("+this.getVerticesNum()+")_E("+this.getEdgesNum()+")_S("+height+"x"+width+").png"));
       } 
       catch (IOException e) {
    	   e.printStackTrace();
       }
	}
	/**
	 * This function returns a specific name for the graph.
	 * @return String
	 */
	public String getSpecificName(){
		return "N("+this.getName()+")_V("+this.getVerticesNum()+")_E("+this.getEdgesNum()+")";
	}
	/**
	 * This function returns the name,vertices numbers, and edges numbers of graph.
	 * @return String
	 */
	public String getGraphInfo(){
		return ("# "+this.getName()+" "+this.getVerticesNum()+" "+this.getEdgesNum());
	}
	/**
	 * This function returns a list of edges of graph. Each edge is separated with `;` with others, and each side is separated by `,` from each other.
	 * The first number is vertex of edge, and the second number after `,` is the second vertex of edge.
	 * @return String
	 */
	public String getGraphEdges()
	{
		String edgesList="";
		for(int i=0;i<this.edgeSide1.size() && i<this.edgeSide2.size();i++)
			edgesList += this.edgeSide1.get(i).intValue()+","+this.edgeSide2.get(i).intValue()+";";
		return edgesList;
	}
	/**
	 * This function returns a String that can be used as input for GraphViz program, to make a diagram from graph.
	 * @return
	 */
	public String getGraphGraphVizDotString()
	{
		String graphViz="graph g {\n";
		for(int i=0;i<this.edgeSide1.size() && i<this.edgeSide2.size();i++)
			graphViz += (this.edgeSide1.get(i).intValue()+1)+" -- "+(this.edgeSide2.get(i).intValue()+1)+";\n";
		graphViz+="}\n";
		return graphViz;
	}
	/**
	 * This function returns number of vertices of the graph.
	 * @return int
	 */
	public int getVerticesNum() {
		return verticesNum;
	}
	/**
	 * This function returns number of edges of the graph.
	 * @return int
	 */
	public int getEdgesNum() {
		return edgesNum;
	}
	/**
	 * This function returns name of the graph that user passed in the time of creation of the graph.
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * This function receive an integer as a row number of adjacency matrix and returns a boolean array as that row of adjecency matrix of graph.
	 * @param row the row number of adjacency matrix
	 * @return boolean[]
	 */
	public boolean[] getRow(int row)
	{
		boolean[] temporalRow = new boolean[this.getVerticesNum()];
		for(int i=0;i<this.edgeSide1.size() && i<this.edgeSide1.size();i++)
		{
			int side1 = this.edgeSide1.get(i).intValue();
			int side2 = this.edgeSide2.get(i).intValue();
			if(side1==row && side2<this.getVerticesNum())
				temporalRow[side2]=true;
			else if (side2==row && side1<this.getVerticesNum())
				temporalRow[side1]=true;
		}
		return temporalRow;
	}
	/**
	 * Returns the situation of adjacency between vertices `from` and `to`
	 * If there is any edge between them then it returns the index of edge in edges vectors otherwise -1;
	 * @param from
	 * @param to
	 * @return index of edge in vertices list, otherwise -1
	 */
	public int isEdge(int from,int to)
	{
		if(from == to)
			return -1;
		else if(from>to)
		{
			int temp = from;
			from = to;
			to = temp;
		}
		for(int i=0;i<this.edgeSide1.size() && i<this.edgeSide2.size();i++)
		{
			int side1 = this.edgeSide1.get(i).intValue();
			int side2 = this.edgeSide2.get(i).intValue();
			if(side1==from && side2==to)
				return i;
			else if(side1>from)
				break;
		}
		return -1;
	}
	/**
	 * This function receive 2 integer number and if there is no edge between them make an edge between them.
	 * @param from
	 * @param to
	 */
	public void addEdge(int from,int to)
	{
		if(from == to)
			return;
		else if(from>to)
		{
			int temp = from;
			from = to;
			to = temp;
		}
		if(this.isEdge(from, to)<0)
		{
			if(this.edgeSide1.size()==0)
			{
				this.edgeSide1.add(from);
				this.edgeSide2.add(to);
			}
			else
			{
				int startIndex = this.edgeSide1.indexOf(from);
				int lastIndex = this.edgeSide1.lastIndexOf(from);
				if(startIndex>-1)
				{
					int secondIndex = startIndex;
					while(secondIndex<=lastIndex)
							if(this.edgeSide2.get(secondIndex)<to)
								secondIndex++;
							else
								break;
					this.edgeSide1.add(secondIndex, from);
					this.edgeSide2.add(secondIndex, to);
				}
				else
				{
					int s = this.edgeSide1.size();
					int i = s-1;					
					for(;i>-1;i--)
						if(from>this.edgeSide1.get(i))
							break;
					this.edgeSide1.add(i+1,from);
					this.edgeSide2.add(i+1,to);
				}
			}
			this.degrees.set(from, this.degrees.get(from).intValue()+1);
			this.degrees.set(to, this.degrees.get(to).intValue()+1);
			this.edgesNum++;
		}
	}
	/**
	 * Receive 2 integer numbers as two vertices of an edge and remove that edge from graph.
	 * @param from
	 * @param to
	 */
	public void removeEdge(int from,int to)
	{
		int index=-1;
		if((index = this.isEdge(from, to))>=0)
		{
			this.edgeSide1.remove(index);
			this.edgeSide2.remove(index);
			this.degrees.set(from, this.degrees.get(from).intValue()-1);
			this.degrees.set(to, this.degrees.get(to).intValue()-1);
			this.edgesNum--;
		}
	}
	/**
	 * Returns a Graph as LineGraph of current graph.
	 * @return
	 */
	public Graph getLineGraph()
	{
		Graph lg=new Graph("LG("+this.getName()+")", this.getEdgesNum(), this.getLineGraphEdgesNum());
		for(int i=0; i<this.edgeSide1.size() && i<this.edgeSide2.size();i++)
			for(int j=i+1; j<this.edgeSide1.size() && j<this.edgeSide2.size();j++)
			{
				if(this.areAdjacent(i, j))
					lg.addEdge(i, j);
			  //int x1 = this.edgeSide1.get(i); //do not need to use this
				int y1 = this.edgeSide2.get(i);
				int x2 = this.edgeSide1.get(j);
			  //int y2 = this.edgeSide2.get(j); //do not need to use this
				if(y1<x2)
					break;
			}
		return lg;
	}
	/**
	 * This function receives 2 integer numbers as number of edges of the graph and tells they are adjacent or not.
	 * @param edge1
	 * @param edge2
	 * @return
	 */
	private boolean areAdjacent(int edge1,int edge2)
	{
		int size1 = this.edgeSide1.size();
		int size2 = this.edgeSide2.size();
		if(edge1<size1 && 
				edge1<size2 && 
					edge2<size1 && 
						edge2<size2)
		{
			int x1 = this.edgeSide1.get(edge1);
			int y1 = this.edgeSide2.get(edge1);
			int x2 = this.edgeSide1.get(edge2);
			int y2 = this.edgeSide2.get(edge2);
			if(x1==x2 || x1==y2 || y1==y2 || y1==x2)
				return true;
		}
		return false;
	}
	/**
	 * This function returns maximum degree of the graph.
	 * @return
	 */
	public int getMaxDegree()
	{
		int max = 0;
		for(int i=0;i<this.degrees.size();i++)
			if(max<this.degrees.get(i).intValue())
				max = this.degrees.get(i).intValue();
		return max;
	}
	/**
	 * This function returns minimum degree of the graph.
	 * @return
	 */
	public int getMinDegree()
	{
		int min = Integer.MAX_VALUE;
		for(int i=0;i<this.degrees.size();i++)
			if(min>this.degrees.get(i).intValue())
				min = this.degrees.get(i).intValue();
		return min;
	}
	/**
	 * This function returns the index of the vertex which has maximum degree.
	 * @return
	 */
	public int getMaxDegreeVertex()
	{
		int max = 0;
		int j=0;
		for(int i=0;i<this.degrees.size();i++)
			if(max<this.degrees.get(i).intValue())
				{
					max = this.degrees.get(i).intValue();
					j=i;
				}
		return j;
	}
	/**
	 * This function returns the index of the vertex which has minimum degree.
	 * @return
	 */
	public int getMinDegreeVertex()
	{
		int min = Integer.MAX_VALUE;
		int j=0;
		for(int i=0;i<this.degrees.size();i++)
			if(min>this.degrees.get(i).intValue())
			{
				min = this.degrees.get(i).intValue();
				j=i;
			}
		return j;
	}
	/**
	 * returns a list of indices based on their degree ordering
	 * @return
	 */
	protected ArrayList<Integer> getVerticesBasedOnDegree()
	{
		int[][] vertices = new int[this.getVerticesNum()][2];
		for(int i=0;i<this.verticesNum;i++)
		{
			vertices[i][0] = i; //Indices
			vertices[i][1] = this.degrees.get(i);
		}
		//sort
		for(int i=0; i<this.verticesNum-1; i++)
		{
			for(int j=i+1;j<this.verticesNum;j++)
			{
				if(vertices[i][1]<vertices[j][1])
				{
					//swap
					int t1 = vertices[i][0];
					int t2 = vertices[i][1];
					//
					vertices[i][0] = vertices[j][0];
					vertices[i][1] = vertices[j][1];
					//
					vertices[j][0] = t1;
					vertices[j][1] = t2;
				}
			}
		}
		ArrayList<Integer> vList = new ArrayList<Integer>(this.verticesNum);
		for(int i=0; i<this.verticesNum;i++)
			vList.add(vertices[i][0]);
		return vList;
	}
	/**
	 * This function returns degree of a received vertex.
	 * @param v
	 * @return
	 */
	public int getDegree(int v)
	{
		int j=-1;
		if(v<this.degrees.size())
			j=this.degrees.get(v);
		return j;
	}
	/**
	 * This function returns average degree of a graph.
	 * @return
	 */
	public double getAverageDegree(){
		return 2*this.getEdgesNum()/this.getVerticesNum();
	}
	//Clone functions start
	/**
	 * This function makes a copy from graph.
	 */
	@SuppressWarnings("unchecked")
	public Graph clone()
	{
		Graph g = new Graph(this.getName(), this.getVerticesNum(), this.getEdgesNum());
		ArrayList<Integer> edgeSide1 = (ArrayList<Integer>) this.edgeSide1.clone();
		g.setEdgeSide1(edgeSide1);
		ArrayList<Integer> edgeSide2 = (ArrayList<Integer>) this.edgeSide2.clone();
		g.setEdgeSide2(edgeSide2);
		ArrayList<Integer> degrees = (ArrayList<Integer>) this.degrees.clone();
		g.setDegrees(degrees);
		return g;
	}
	/**
	 * This function used for making a copy of graph inside the clone function.
	 * @param edgeSide1
	 */
	private void setEdgeSide1(ArrayList<Integer> edgeSide1) {
		this.edgeSide1 = edgeSide1;
	}
	/**
	 * This function used for making a copy of graph inside the clone function.
	 * @param edgeSide1
	 */
	private void setEdgeSide2(ArrayList<Integer> edgeSide2) {
		this.edgeSide2 = edgeSide2;
	}
	/**
	 * This function used for making a copy of graph inside the clone function.
	 * @param edgeSide1
	 */
	private void setDegrees(ArrayList<Integer> degrees) {
		this.degrees = degrees;
	}
	//Clone end
	/**
	 * This function returns number of edges of the LineGraph of the graph.
	 * @return
	 */
	public int getLineGraphEdgesNum()
	{
		int sigma = 0;
		for(int i=0;i<this.degrees.size();i++)
				sigma += Math.pow(this.degrees.get(i).intValue(),2);
		return sigma/2-this.getEdgesNum();
	}	
	/**
	 * This function is an implemented for the Q factor of a vertex in a graph that is presented on page 5 of Ref1.
	 * http://www.cs.uu.nl/research/techreps/repo/CS-2006/2006-032.pdf
	 * @param S   list of vertices as search space.
	 * @param v   The intended vertex
	 * @return int Qs of v in Graph
	 */
	protected int queFactor(ArrayList<Integer> S, int v)
	{
		ArrayList<Integer> qSet = new ArrayList<Integer>();
		//add all possible w to searchSpace
		for(int w=0;w<this.getVerticesNum();w++)
			if(S.indexOf(w)<0 && w!=v)//Then w is inside the search space!
				if(pathExist(v, w, S))
						qSet.add(w);
		//return size of Q set!
		int cardinality = qSet.size();
		qSet.clear();
		return cardinality;
	}
	/**
	 * This function is used inside the queFactor function from Ref1.
	 * @param fromV
	 * @param toW
	 * @param throughS
	 * @return
	 */
	private boolean pathExist(int fromV, int toW, ArrayList<Integer> throughS)
	{
		if(this.isEdge(fromV, toW)>=0)
			return true;
		boolean result = false;
		int size = throughS.size();
		for(int i=0;i<size && !result;i++)
		{
			int nextV = throughS.get(i);
			if(this.isEdge(fromV, nextV)>=0)
			{
				//We must make a new copy from ArrayList because we need to remove some elements and call recursively!
				//ArrayList<Integer> newS = new ArrayList<Integer>(size);
				//for(int j=0;j<size;j++)
				//	newS.add(throughS.get(j));
				@SuppressWarnings("unchecked")
				ArrayList<Integer> newS = (ArrayList<Integer>) throughS.clone();
				newS.remove(i);
				result = pathExist(nextV, toW, newS);
				newS.clear();
			}
		}
		return result;
	}
	/**
	 * This function is an implementation from Ref1 page 7, and calculate treewidth of graph by dynamic programming.
	 * @param upBound If the user returns -1 as upper bound of tw it uses (n-1) as original paper, otherwise if user have any better upper bound can be used that has huge effect on efficiency.
	 * @return
	 */
	public int getTreeWithByDynamicProgramming(int upBound)
	{
		int n = this.getVerticesNum();
		int upperBound = upBound!=-1 ? upBound : n-1;//initial upper bound
		TWPairNode tw0Node = new TWPairNode();//(nullSet,-infinity)
		TWSeries TWs = new TWSeries(n+1);
		TWs.addEmptySet(0);
		TWs.addItem(0, tw0Node);
		
		for(int i=1;i<=n;i++)
		{//Console.out("i="+i+",");
			if(TWs.addEmptySet(i))
			{
				ArrayList<TWPairNode> preTW = TWs.getTWIth(i-1);
				for(int j=0;j<preTW.size();j++) //for each pair (S, r) in TWi−1 do
				{//Console.outln("j="+j);
					TWPairNode pair = preTW.get(j);
					for(int x=0;x<this.getVerticesNum();x++)//x member of V
					{//Console.outln("x="+x);
						if(!pair.hasS(x))// x member of V-S
						{
							int q = this.queFactor(pair.getS(), x);
							int r = pair.getR();
							int rPrime = r>q ? r : q; //Set r' = max{r, q}.
							if(rPrime < upperBound)
							{
								ArrayList<Integer> sUnionX = pair.getS_Union_X(x);
								ArrayList<TWPairNode> curTW = TWs.getTWIth(i);
								boolean thereIsAPair = false;
								for(int k=0;k<curTW.size() && !thereIsAPair;k++)
								{//Console.outln("k="+k);
									TWPairNode curPair = curTW.get(k);
									int t = curPair.getR();
									if(curPair.equalitySwith(sUnionX))
									{
										thereIsAPair = true;
										curPair.setR(rPrime<t?rPrime:t);
										curPair.replaceS(sUnionX);										 
									}
								}
								if(!thereIsAPair)
								{
									curTW.add(new TWPairNode(sUnionX, rPrime));
								}
							}
						}
					}
				}
			}
		}
		ArrayList<Integer> V = new ArrayList<Integer>(n);
		for(int i=0;i<n;i++)
			V.add(i);
		ArrayList<TWPairNode> TWn = TWs.getTWIth(n);
		for(int k=0;k<TWn.size(); k++)
		{
			TWPairNode curPair = TWn.get(k);
			int r = curPair.getR();
			if(curPair.equalitySwith(V))
			{
				return r;										 
			}
		}
		return upperBound;
	}
	/**
	 * This function returns treewidth of current graph and is based on recursive algorithm of Ref1 page 9, but with multithread approach.
	 * This function for calculation needs MultiThreadTreeWidth class
	 * @return
	 */
	public int getTreeWithByRecursiveMultiThread()
	{
		if(this.getVerticesNum() > this.getEdgesNum()+1)
			return 0;
		else if(this.getVerticesNum() == this.getEdgesNum()+1)
			return 1;
		int n = this.getVerticesNum();
		ArrayList<Integer> L = new ArrayList<Integer>();
		ArrayList<Integer> S = new ArrayList<Integer>(n);
		for(int i=0;i<n;i++)
			S.add(i); //Put V in S
		Result R = new Result();
		Thread tr = new Thread(new MultiThreadTreeWidth(this, L, S,R));
		tr.start();
		try {
            tr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return R.getResult();
		
	}
	/**
	 * This function returns treewidth of current graph and is based on recursive algorithm of Ref1 page 9, but with single thread approach.
	 * @return
	 */
	public int getTreeWithByRecursiveSingleThread()
	{
		if(this.getVerticesNum() > this.getEdgesNum())
			return 1;
		int n = this.getVerticesNum();
		ArrayList<Integer> L = new ArrayList<Integer>();
		ArrayList<Integer> S = new ArrayList<Integer>(n);
		for(int i=0;i<n;i++)
			S.add(i); //Put V in S
		return singleThreadRecursiveTreeWidth(L,S);
		
	}
	/**
	 * This function calculate treewidth of current graph and is based on recursive algorithm of Ref1 page 9, but with single thread approach.
	 * @return
	 */
	private int singleThreadRecursiveTreeWidth(ArrayList<Integer> L, ArrayList<Integer> S)
	{
		if(S.size()==1)
		{
			int v = S.get(0);
			return this.queFactor(L, v);
		}
		int opt = Integer.MAX_VALUE;
		int SPrimeSize = (int) Math.floor(S.size()/2);
		List<Set<Integer>> allSubSets = SetOperation.getSubsets(S, SPrimeSize);
		for(int i=0;i<allSubSets.size();i++)
		{
			Set<Integer> Sprime =  allSubSets.get(i);
			ArrayList<Integer> L1 = new ArrayList<Integer>(L.size());
			ArrayList<Integer> L2 = new ArrayList<Integer>(L.size()+Sprime.size());
			ArrayList<Integer> S1 = new ArrayList<Integer>(Sprime.size());
			@SuppressWarnings("unchecked")
			ArrayList<Integer> S2 = (ArrayList<Integer>) S.clone();
			for(int j=0;j<L.size();j++){
				L1.add(L.get(j));
				L2.add(L.get(j));
			}
			for (Integer s : Sprime){ 
				L2.add(s);//L U S'
				S1.add(s);
				S2.remove(s);
			}
			int v1 = this.singleThreadRecursiveTreeWidth(L1, S1);
			int v2 = this.singleThreadRecursiveTreeWidth(L2, S2);
			int max = v1>v2 ? v1 : v2;
			opt = opt<max ? opt : max;
		}
		return opt;
	}
	/**
	 * This function is elim(G,v) in Ref2 page 2 (202) lemma 2.9 and is used for calculating upper bound of TW(G).
	 * @param G
	 * @param v
	 * @return
	 */	
	@SuppressWarnings("unchecked")
	private Graph elim(Graph G, int v)
	{
		ArrayList<Integer> edgeSide1     = new ArrayList<Integer>();
		ArrayList<Integer> edgeSide2     = new ArrayList<Integer>();
		ArrayList<Integer> neighborhoods = new ArrayList<Integer>();
		int s1 = G.edgeSide1.size();
		int s2 = G.edgeSide2.size();
		for(int i=0;i<s1 && i<s2;i++)
		{
			int x=G.edgeSide1.get(i);
			int y=G.edgeSide2.get(i);
			if(x<v && y<v)
			{
				edgeSide1.add(x);
				edgeSide2.add(y);
			}
			else if(x<v && y>v)
			{
				edgeSide1.add(x);
				edgeSide2.add(y-1);
			}
			else if(x>v && y<v)
			{
				edgeSide1.add(x-1);
				edgeSide2.add(y);
			}
			else if(x>v && y>v)
			{
				edgeSide1.add(x-1);
				edgeSide2.add(y-1);
			}
			else if(x==v && y!=v)
			{
				if(y<v)
					neighborhoods.add(y);
				else
					neighborhoods.add(y-1);
			}
			else if(x!=v && y==v)
			{
				if(x<v)
					neighborhoods.add(x);
				else
					neighborhoods.add(x-1);
			}
		}
		//
		s1 = neighborhoods.size();
		for(int i=0;i<s1;i++)
			for(int j=i+1;j<s1;j++)
			{
				int x=neighborhoods.get(i);
				int y=neighborhoods.get(j);
				if(x<y)
				{
					edgeSide1.add(x);
					edgeSide2.add(y);
				}
				else if(x>y)
				{
					edgeSide1.add(y);
					edgeSide2.add(x);
				}
			}
		s1 = edgeSide1.size();
		Graph g = new Graph(G.getName(), G.getVerticesNum()-1, s1);
		for(int i=0;i<s1;i++)
		{
			g.addEdge(edgeSide1.get(i), edgeSide2.get(i));
		}
		g.maps = (ArrayList<Integer>) G.maps.clone();
		g.maps.remove(v);
		return g;
	}
	/**
	 * This function calculate minor of the Graph based on Ref2 page 2 lemma 2.9 and is used for calculating upper bound of TW(G).
	 * @param G
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected Graph minor(Graph G, int from,int to) throws Exception
	{
		//be sure v<u
		int v,u;
		if(from>to){
			v=to;
			u=from;
		}
		else if(from<to){
			v=from;
			u=to;
		}
		else
			throw new Exception("In minor function `from` must be less than `to`.");
		
		ArrayList<Integer> edgeSide1     = new ArrayList<Integer>();
		ArrayList<Integer> edgeSide2     = new ArrayList<Integer>();
		int s1 = G.edgeSide1.size();
		int s2 = G.edgeSide2.size();
		for(int i=0;i<s1 && i<s2;i++)
		{
			int x=G.edgeSide1.get(i);
			int y=G.edgeSide2.get(i);
			if(x>=y)
			{
				//do not do anything! provides the condition that x<y!
			}
			else if(x<v && y<v)
			{
				edgeSide1.add(x);
				edgeSide2.add(y);
			}
			else if(x<v && y==v)
			{
				edgeSide1.add(x);
				edgeSide2.add(u-1);
			}
			else if(x<v && y>v)
			{
				edgeSide1.add(x);
				edgeSide2.add(y-1);
			}
			else if(x==v && y<u)//v<y<u
			{
				edgeSide1.add(y-1);
				edgeSide2.add(u-1);
			}
			//else if(x==v && y==u) 
			//{
				//do not add 
			//} 
			else if(x==v && u<y)//v<u<y
			{
				edgeSide1.add(u-1);
				edgeSide2.add(y-1);
			}
			else if(x>v)//  v<x<y
			{
				edgeSide1.add(x-1);
				edgeSide2.add(y-1);
			}
		}
		s1 = edgeSide1.size();
		Graph g = new Graph(G.getName(), G.getVerticesNum()-1, s1);
		for(int i=0;i<s1;i++)
		{
			g.addEdge(edgeSide1.get(i), edgeSide2.get(i));
		}
		g.maps = (ArrayList<Integer>) G.maps.clone();
		g.maps.remove(v);
		return g;
	}
	/**
	 * This function calculate a lower bound for treewidth of the graph based on MMW (minor-min-width) algorithm in Ref1 page 3(204).
	 * @param G
	 * @return
	 * @throws Exception
	 */
	private int getTreeWidthLowerBoundMMW(Graph G) throws Exception
	{
		int lb =0;
		Graph g = G.clone();
		while(g.getVerticesNum()>1)
		{
			int v = g.getMinDegreeVertex();
			int degreeVinG = g.getDegree(v);
			if(degreeVinG>lb)
				lb = degreeVinG;
			int u = g.getMinDegreeNeighborhood(g, v);
			if(u<0)
				return -1; //Unconnected graphs has u equals to -1
			else
				g = g.minor(g, v, u);
		}
		return lb;
	}
	/**
	 * This function returns treewidth lower bound.
	 * @return
	 * @throws Exception
	 */
	public int getTreeWidthLowerBound() throws Exception
	{
		return this.getTreeWidthLowerBoundMMW(this);
	}
	/**
	 * This function returns a list of neighborhoods of vertex v in graph G
	 * @param G
	 * @param v
	 * @return
	 */
	public ArrayList<Integer> getNeighborhoodsList(Graph G, int v)
	{
		ArrayList<Integer> neighborhoods = new ArrayList<Integer>();
		int s1 = G.edgeSide1.size();
		int s2 = G.edgeSide2.size();
		
		for(int i=0;i<s1 && i<s2;i++)
		{
			int x=G.edgeSide1.get(i);
			int y=G.edgeSide2.get(i);
			
			if(x==v && y!=v)
			{
				neighborhoods.add(y);
			}
			else if(x!=v && y==v)
			{
				neighborhoods.add(x);
			}
			else if(x>v)
				break;
		}		
		return neighborhoods;
	}
	/**
	 * This function returns that neighborhood of vertex v in graph G which has minimum degree.
	 * @param G
	 * @param v
	 * @return
	 */
	private int getMinDegreeNeighborhood(Graph G, int v)
	{
		int minNeighborhood = -1;
		int minNeighborhoodDegree = Integer.MAX_VALUE;
		int s1 = G.edgeSide1.size();
		int s2 = G.edgeSide2.size();
		
		for(int i=0;i<s1 && i<s2;i++)
		{
			int x=G.edgeSide1.get(i);
			int y=G.edgeSide2.get(i);
			
			if(x==v && y!=v)
			{
				int minND = G.getDegree(y);
				if(minND < minNeighborhoodDegree)
				{
					minNeighborhood = y;
					minNeighborhoodDegree=minND;
				}
			}
			else if(x!=v && y==v)
			{
				int minND = G.getDegree(x);
				if(minND < minNeighborhoodDegree)
				{
					minNeighborhood = x;
					minNeighborhoodDegree=minND;
				}
			}
			else if(x>v)
				break;
		}		
//		if(minNeighborhood==-1){
//			Console.outln("Unconnected graph.");
//			G.printGraph();
//		}
		return minNeighborhood;
	}
	/**
	 * This function returns the number of edges that must be added to the graph G for making the vertex v Simplicial. 
	 * Simpilical of a graph is defined in Definition 2.3 in Ref 2 page 2(202).
	 * @param G
	 * @param v
	 * @return
	 */
	private int getSimplicialNum(Graph G, int v)
	{
		ArrayList<Integer> neighborhoods = G.getNeighborhoodsList(G, v);
		//
		int num=0;
		int s = neighborhoods.size();
		for(int i=0;i<s;i++)
			for(int j=i+1;j<s;j++)
			{
				int x=neighborhoods.get(i);
				int y=neighborhoods.get(j);
				if(x!=y && G.isEdge(x, y)<0)
					num++;
			}
		//int[] arr = {num,s};
		return num;
	}
	/**
	 * If vertex v in G is Almost Simplicial then it says how many edges it needs otherwise it returns -1
	 * This function is based on Algorithm 1: Enhanced Minimum Fill-in (EMF): in Ref3 page 9.
	 * @param G
	 * @param v
	 * @return
	 */
	private int isAlmostSimplicial(Graph G, int v)
	{
		@SuppressWarnings("unused")
		int problematicNeighbor = -1;
		int numberOfNeededEdges = -1;
		ArrayList<Integer> neighborhoods = G.getNeighborhoodsList(G, v);
		ArrayList<Integer> edgeSide1     = new ArrayList<Integer>();
		ArrayList<Integer> edgeSide2     = new ArrayList<Integer>();
		//make a list from edges that needs to be add
		int s = neighborhoods.size();
		for(int i=0;i<s;i++)
			for(int j=i+1;j<s;j++)
			{
				int x=neighborhoods.get(i);
				int y=neighborhoods.get(j);
				if(x!=y && G.isEdge(x, y)<0)
				{
					if(x<y){
						edgeSide1.add(x);
						edgeSide2.add(y);
					}else{
						edgeSide1.add(y);
						edgeSide2.add(x);
					}
				}
			}
		//if all vertices in edgeSide1 or edgeSide2 are same then it is a Almost Simplicial
		boolean flag = true;
		s = edgeSide1.size();
		for(int i=0;i<s-1 && flag;i++)
		{
			if(edgeSide1.get(i)!=edgeSide1.get(i+1))
				flag = false;
		}
		if(flag && s>0)
		{
			problematicNeighbor = edgeSide1.get(0);
			numberOfNeededEdges = s;
		}
		else{
			flag = true;
			s = edgeSide2.size();
			for(int i=0;i<s-1 && flag;i++)
			{
				if(edgeSide2.get(i)!=edgeSide2.get(i+1))
					flag = false;
			}
			if(flag && s>0)
			{
				problematicNeighbor = edgeSide2.get(0);
				numberOfNeededEdges = s;
			}
		}
		return numberOfNeededEdges;
	}
	/**
	 * Tells which vertex inside a graph has least simpilicial number.
	 * This function is based on The min-fill heuristic algorithm in Ref2 page 3(203).
	 * @param G
	 * @return
	 */
	private int whichVertex(Graph G)
	{
		int size = G.getVerticesNum();
		int minSimplicalNum = Integer.MAX_VALUE;
		int minSimplicalVertex = 0;
		for(int i=0;i<size;i++)
		{
			int simpNum = G.getSimplicialNum(G,i);
			if(simpNum<minSimplicalNum)
			{
				minSimplicalNum = simpNum;
				minSimplicalVertex = i;
			}
		}
		return minSimplicalVertex;
	}
	/**
	 * This function returns a Perfect Elimination Ordering Based on Definition 2.3. of Ref2.
	 * @param G
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArrayList<Integer> getMinFillOrder(Graph G)
	{
		int size = G.getVerticesNum();
		ArrayList<Integer> minFillList = new ArrayList<Integer>(size);
		Graph g = G.clone();
		while(minFillList.size()<size && g.getVerticesNum()>0)
		{
			int nextVertex = g.whichVertex(g);
			minFillList.add(g.maps.get(nextVertex));
			g = g.elim(g, nextVertex);
		}
		return minFillList;
	}
	/**
	 * This function is based on Algorithm 1: Enhanced Minimum Fill-in (EMF): Ref3 page 9.
	 * @param G
	 * @return
	 */
	private int hasSimplicialVertex(Graph G)
	{
		int simp = -1;
		int size = G.getVerticesNum();
		for(int i=0;i<size;i++)
		{
			int simplicialNum = G.getSimplicialNum(G,i);
			if(simplicialNum == 0)
			{
				simp = i;
				break;
			}
		}
		return simp;
	}
	/**
	 * This function is based on Algorithm 1: Enhanced Minimum Fill-in (EMF): Ref3 page 9.
	 * If the graph has a vertex that is almost simplicial then returns its index, otherwise returns -1.
	 * @param G
	 * @return
	 */
	private int hasAlmostSimplicialVertex(Graph G, int lbTW)
	{
		int almostSimp = -1;
		int size = G.getVerticesNum();
		for(int i=0;i<size;i++)
		{
			int almostSimplicialNum = G.isAlmostSimplicial(G, i);
			if(almostSimplicialNum > -1)
			{
				int degreeI = G.getDegree(i);
				if(degreeI<=lbTW)
				{
					almostSimp = i;
					break;
				}
			}
		}
		return almostSimp;
	}
	/**
	 * This function calculate an upper bound for TW(G) based on page Algorithm 1: Enhanced Minimum Fill-in (EMF): Ref3 page 9.
	 * @param G
	 * @return
	 * @throws Exception
	 */
	private int getTreeWidthUpperBound(Graph G) throws Exception
	{
		int upperBound = 0;
		int lowerBound = G.getTreeWidthLowerBound();
		Graph H = G.clone();
		while(H.getVerticesNum()>0)
		{
			int w = -1;;
			if((w = H.hasSimplicialVertex(H)) > -1)
			{
				int digreeW = H.getDegree(w);
				upperBound = digreeW>upperBound ? digreeW : upperBound;
			}
			else if((w = H.hasAlmostSimplicialVertex(H, lowerBound))>-1)
			{
				int digreeW = H.getDegree(w);
				upperBound = digreeW>upperBound ? digreeW : upperBound;
			}
			else{
				w = H.whichVertex(H);
				int digreeW = H.getDegree(w);
				upperBound = digreeW>upperBound ? digreeW : upperBound;
			}
			H = H.elim(H, w);
		}
		return upperBound;
	}
	/**
	 * This function returns graph treewidth upper bound.
	 * @return
	 * @throws Exception
	 */
	public int getTreeWidthUpperBound() throws Exception
	{
		return this.getTreeWidthUpperBound(this);
	}
	/**
	 * This function returns best lower bound for TW(L(G)) base on H&W paper Ref5
	 * @return
	 */
	public int getBestHarveyAndWoodTWLGLowerBound(){
		double lb = this.getMaxDegree() -1;
		double avgDeg = this.getAverageDegree();
		double temp = (avgDeg*avgDeg/8d)+(3d*avgDeg/4d)-2d;
		if(lb<temp)
			lb = temp;
		int minDeg = this.getMinDegree();
		if(minDeg%2==0)
			temp = minDeg*minDeg/4d+minDeg-1;
		else
			temp = minDeg*minDeg/4d+minDeg-(5d/4d);
		if(lb<temp)
			lb = temp;	
		return (int) (lb<0? 0 : lb);
	}
	/**
	 * This function returns best upper bound for TW(L(G)) base on H&W paper Ref5.
	 * In that paper they used tw(G) for Upper bound of TW((LG)) but I used upper bound of TW(G) instead of TW(G)
	 * @return
	 */
	public int getBestHarveyAndWoodTWLGUpperBound()
	{
		int n = this.getVerticesNum();
		double twup = n-1;
		try {
			n = this.getTreeWidthUpperBound();
			twup = n<twup ? n : twup;
		} catch (Exception e) {
		}
		double maxDeg = this.getMaxDegree();
		double ub = (twup+1)*maxDeg-1;
		double temp = 2d*(twup+1)*maxDeg/3d+(twup*twup)/3d+maxDeg/3d-1d;
		if(ub>temp)
			ub = temp;	
		return (int) ub;
	}
	/**
	 * This function returns treewidth of current graph, based on Branch And Bound algorithm of Ref2 page 4.
	 * @return
	 * @throws Exception 
	 */
	public int getTreeWidthByBranchAndBound() throws Exception
	{
		State s = new State(this.clone());
		s.set_g(0);
		s.set_h(this.getTreeWidthLowerBoundMMW(this));
		s.set_f(s.get_h());
		
		int ub = this.getTreeWidthUpperBound();
		
		if(s.get_f()<ub)
			ub = this.BranchAndBound(s,ub);
		return ub;
		
	}
	/**
	 * The BB sub procedure of Reference 2 for calculating treewidth
	 * @param s
	 * @param upperBound
	 * @return
	 * @throws Exception
	 */
	private int BranchAndBound(State s, int upperBound) throws Exception {
		int ub = upperBound;
		Graph G = s.getG();
		int verticesNum = G.getVerticesNum();
		if(verticesNum<2)
		{
			if(s.get_f()<ub) //ub = MIN(ub; f(s))
				ub = s.get_f();
		}
		else
		{
			//ArrayList <Integer> vList = G.getVerticesBasedOnDegree();
			for(int v=0; v<verticesNum;v++)
			{
				//int v = vList.get(i);
				//line 'a' of algorithm
				Graph Gprime = G.elim(G, v);
				//lines 'b' to 'd' of algorithm
				int gprime = s.get_g() > G.getDegree(v) ? s.get_g() : G.getDegree(v);
				int hprime = Gprime.getTreeWidthLowerBoundMMW(Gprime);
				int fprime = gprime > hprime ? gprime : hprime;
				// line 'a' of algorithm
				State Sprime = new State(Gprime, gprime, hprime, fprime);
				// line 'e' of algorithm
				if(fprime<ub)
					ub = this.BranchAndBound(Sprime,ub);
			}
		}
		return ub;
	}
}
