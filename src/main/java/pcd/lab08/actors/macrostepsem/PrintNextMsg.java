package pcd.lab08.actors.macrostepsem;

public final class PrintNextMsg {
	private final int n;
	private final int max;

	public PrintNextMsg(int n, int max){
		this.n = n;
		this.max = max;
	}
	
	public int getNum(){
		return n;
	}
	
	public int getMax(){
		return max;
	}

}
