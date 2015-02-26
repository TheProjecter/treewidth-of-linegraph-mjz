package graphTools;

public class State {
	private Graph G;
	private int g;
	private int h;
	private int f;
	public Graph getG() {
		return G;
	}
	public void setG(Graph g) {
		G = g;
	}
	public int get_g() {
		return g;
	}
	public void set_g(int g) {
		this.g = g;
	}
	public int get_h() {
		return h;
	}
	public void set_h(int h) {
		this.h = h;
	}
	public int get_f() {
		return f;
	}
	public void set_f(int f) {
		this.f = f;
	}
	public State(Graph G, int g, int h, int f) {
		super();
		this.G = G;
		this.g = g;
		this.h = h;
		this.f = f;
	}
	public State(Graph G) {
		super();
		this.G = G;
	}
	
}
