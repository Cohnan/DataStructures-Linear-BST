package model.vo;

public class Coordenadas implements Comparable<Coordenadas> {

	
	private int xCoord;
	private int yCoord;
	
	
	public Coordenadas(double x, double y) {
		xCoord = (int) (x*100);
		yCoord = (int) (y*100);
	}
	
	
	
	@Override
	public int compareTo(Coordenadas o) {
		
		if(this.xCoord>o.xCoord) return 1;
		else if (this.xCoord<o.xCoord) return -1;
		else if(this.yCoord>o.yCoord) return 1;
		else if(this.yCoord<o.yCoord) return -1;
		else return 0;
	}

	
	public double darX(){
		return xCoord/100.;
	}
	
	public double darY(){
		return yCoord/100.;
	}
	
	
}
