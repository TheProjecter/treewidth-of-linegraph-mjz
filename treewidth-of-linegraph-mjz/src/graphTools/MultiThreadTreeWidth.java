package graphTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import auxiliary.Result;
import auxiliary.SetOperation;

public class MultiThreadTreeWidth implements Runnable
{
	private Graph G;
	private ArrayList<Integer> L;
	private ArrayList<Integer> S;
	private Result R;
	
	public MultiThreadTreeWidth(Graph G, ArrayList<Integer> L, ArrayList<Integer> S, Result R) {
		super();
		this.G = G;
		this.L = L;
		this.S = S;
		this.R = R;
	}

	private void recursiveTreeWidth()
	{
		if(S.size()==1)
		{
			int v = S.get(0);
			R.setResult(G.queFactor(L, v));
			return;
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
			int v1 = 0;//TreeWidth.recursiveTreeWidth(G, L1, S1);
			int v2 = 0;//TreeWidth.recursiveTreeWidth(G, L2, S2);
			Result R1 = new Result();
			Result R2 = new Result();
			//Graph newG = G.clone();
			Thread tr1 = new Thread(new MultiThreadTreeWidth(G, L1, S1, R1));
			Thread tr2 = new Thread(new MultiThreadTreeWidth(G, L2, S2, R2));
			tr1.start();
			tr2.start();
			try {
	            tr1.join();
	            tr2.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			v1=R1.getResult();
			v2=R2.getResult();
			int max = v1>v2 ? v1 : v2;
			opt = opt<max ? opt : max;
		}
		R.setResult(opt);
		return;
	}

	@Override
	public void run() {
		recursiveTreeWidth();
	}
}
