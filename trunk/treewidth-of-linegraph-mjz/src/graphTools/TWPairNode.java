package graphTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TWPairNode 
{
	private ArrayList<Integer> S;//an ArrayList with zero items means null set
	private int r;
	public TWPairNode() 
	{
		super();
		this.S = new ArrayList<Integer>();
		this.r = Integer.MIN_VALUE;
	}
	@SuppressWarnings("unchecked")
	public TWPairNode(ArrayList<Integer> S, int r) {
		super();
		this.S = (ArrayList<Integer>) S.clone();
		this.r = r;
	}
	public ArrayList<Integer> getS() {
		return this.S;
	}
	@SuppressWarnings("unchecked")
	public void replaceS(ArrayList<Integer> S) {
		this.S = (ArrayList<Integer>) S.clone();
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public boolean hasS(int item)
	{
		return this.S.contains(item);
	}
	public ArrayList<Integer> getS_Union_X(int x)
	{
		@SuppressWarnings("unchecked")
		ArrayList<Integer> tempS = (ArrayList<Integer>) this.S.clone();
		tempS.add(x);
		return tempS;
	}
	public boolean equalitySwith(ArrayList<Integer> Sprime)
	{
		if(Sprime.size()!=this.S.size())
			return false;
		Collections.sort(this.S, new Comparator<Integer>() 
										{
									        @Override
									        public int compare(Integer  x, Integer  y)
									        {
									        	if(x==y)
									        		return 0;
									            return  x<y ? -1 : +1;
									        }
									    }
						);
		Collections.sort(Sprime, new Comparator<Integer>() 
										{
									        @Override
									        public int compare(Integer  x, Integer  y)
									        {
									        	if(x==y)
									        		return 0;
									            return  x<y ? -1 : +1;
									        }
									    }
						);
		boolean result = true;
		for(int i=0;i<this.S.size() && result;i++)
			result = (this.S.get(i)==Sprime.get(i));
		return result;
	}
	
	
}
