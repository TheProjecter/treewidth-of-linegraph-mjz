package graphTools;

import java.util.ArrayList;

public class TWSeries 
{
	private ArrayList<ArrayList<TWPairNode>> TWs;
	private int n;
	public TWSeries(int n) {
		super();
		this.setN(n);
		this.TWs = new ArrayList<ArrayList<TWPairNode>>(this.getN());
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<TWPairNode> getCopyOfAllTWs() {
		return (ArrayList<TWPairNode>) TWs.clone();
	}
	public void addSet(ArrayList<TWPairNode> TW)
	{
		this.TWs.add(TW);
	}
	public boolean addEmptySet(int i)
	{
		if(i==this.TWs.size())
			return this.TWs.add(new ArrayList<TWPairNode>());
		return false;
	}
	public void addItem(int setIth,TWPairNode pairNode){
		if(setIth<this.TWs.size())
			this.TWs.get(setIth).add(pairNode);
	}
	public ArrayList<TWPairNode> getTWIth(int i)
	{
		if(i<this.TWs.size())
			return this.TWs.get(i);
		return null;
	}
}
