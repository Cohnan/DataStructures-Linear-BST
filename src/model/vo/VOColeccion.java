package model.vo;


import java.time.LocalDateTime;
import java.util.Comparator;

public class VOColeccion implements Comparable<VOColeccion> {
	

	
	private int totalInf;
	private double SinAcc;
	private double ConAcc;
	private int  totalPagar;
	
	private LocalDateTime horaInicial;
	private LocalDateTime horaFinal;
	
	private int addressID;
	private int StreetSegId;
	private int xCoord;
	private int yCoord;
	
	private String voCode;
	private String vDesc;
	
	
	
	public VOColeccion(String pCodigo,int ptotalInf, double pSinAcc, double pConAcc, int ptotalPagar, String pVOCode){
		
		voCode = pCodigo;
		totalInf = ptotalInf;
		SinAcc = pSinAcc;
		ConAcc = pConAcc;
		totalPagar = ptotalPagar;
		voCode = pVOCode;
		
	}
	
	
	@Override
	public int compareTo(VOColeccion o) {
		// TODO Auto-generated method stub
		if(this.darTotalInfracciones()>o.darTotalInfracciones()){return 1;}
		else if(this.darTotalInfracciones()<o.darTotalInfracciones()){return -1;}
		else return 0;
	}
	
	public String darCodigo(){
		return voCode;
	}
	
	public int darTotalInfracciones(){
		return totalInf;
	}
	
	public int darTotalPagar(){
		return totalPagar;
	}
	public double darPorcentajeSinInfracciones(){
		return SinAcc;
	}
	public double darPorcentajeConInfracciones(){
		return ConAcc;
	}
	

}
