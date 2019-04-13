package model.vo;

import model.data_structures.IQueue;

/**
 * Organiza las infracciones por el el c�digo de la infracci�n y muestra las estadisticas
 * de las respectivas infracciones que poseen el c�digo en menci�n.
 */

public class InfraccionesViolationCode extends EstadisticaInfracciones implements Comparable<InfraccionesViolationCode>{
	
	@Override
	public String toString() {
		return "InfraccionesViolationCode [violationCode=" + violationCode + ",\n totalInfracciones=" + totalInfracciones
				+ ",\n porcentajeAccidentes=" + getPorcentajeAccidentes() + ",\n porcentajeNoAccidentes="
				+ getPorcentajeNoAccidentes() + ",\n valorTotal=" + valorTotal + "]\n\n";
	}

	/**
	 * Codigo de la infracci�n por las que se van a agrupar las infracciones
	 */
	
	private String violationCode;	
		
	/*
	 * Constructores
	 */
	/**
	 * Instantiates a new infracciones violation code.
	 */
	
	public InfraccionesViolationCode(String violationCodeP) {
		super();
		this.violationCode = violationCodeP;
	}
	
	/**
	 * Instantiates a new infracciones violation code.
	 * @param lista Lista de infracciones que poseen el mismo ViolationCode
	 */
	
	public InfraccionesViolationCode(String violationCodeP, IQueue<VOMovingViolations> lista) {
		super(lista);
		this.violationCode = violationCodeP;
	}

	/*
	 * Metodos
	 */
	/**
	 * Gets the violation code.
	 *
	 * @return the violationCode
	 */
	
	public String getViolationCode() {
		return violationCode;
	}

	
	/**
	 * Comparable - By #Infracciones
	 * @return 0,1 or -1
	 */
	@Override
	public int compareTo(InfraccionesViolationCode arg0) {
		if(this.totalInfracciones>arg0.totalInfracciones){return 1;}
		else if(this.totalInfracciones<arg0.totalInfracciones){return -1;}
		else return 0;
	}	
}
