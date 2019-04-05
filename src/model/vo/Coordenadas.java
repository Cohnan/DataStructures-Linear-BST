package model.vo;

public class Coordenadas implements Comparable<Coordenadas> {

	
	private float xCoord;
	private float yCoord;
	
	
	public Coordenadas(float x, float y) {
		xCoord = x;
		yCoord = y;
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public int compareTo(Coordenadas o) {
		
		if(this.xCoord>o.xCoord) return 1;
		else if (this.xCoord<o.xCoord) return -1;
		else if(this.yCoord>o.yCoord) return 1;
		else if(this.yCoord<o.yCoord) return -1;
		else return 0;
	}

	
	public float darX(){
		return xCoord;
	}
	
	public float darY(){
		return yCoord;
	}
	
	
}
