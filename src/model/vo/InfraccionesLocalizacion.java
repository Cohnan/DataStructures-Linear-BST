package model.vo;

import model.data_structures.IQueue;

/**  
 * Agrupa las infracciones por (Xcoord, Ycoord) 
 */

public class InfraccionesLocalizacion extends EstadisticaInfracciones implements Comparable<InfraccionesLocalizacion> {	

	@Override
	public String toString() {
		return "InfraccionesLocalizacion [xcoord=" + getXcoord() + ", ycoord=" + getYcoord() + ",\n location=" + location
				+ ",\n addressID=" + addressID + ",\n streetID=" + streetID + ",\n totalInfracciones=" + totalInfracciones
				+ ",\n porcentajeAccidentes=" + getPorcentajeAccidentes() + ",\n porcentajeNoAccidentes="
				+ getPorcentajeNoAccidentes() + ",\n valorTotal=" + valorTotal + "]\n\n";
	}

	private int xcoord;
	
	private int ycoord;
	
	private String location;
	
	private int addressID;
	
	private int streetID;
	
	
	/*
	 * Constructores
	 */
	
	/**
	 * Instantiates a new infracciones localizacion.
	 */
	public InfraccionesLocalizacion(double xcoor, double ycoor, String locat, int address, int street) {
		super();
		xcoord = (int) (xcoor*100);
		ycoord = (int) (ycoor*100);
		location = locat;
		addressID = address;
		streetID = street;

	}
	
	/**
	 * Instantiates a new infracciones localizacion.
	 * @param lista the lista
	 */
	
	public InfraccionesLocalizacion(double xcoor, double ycoor, String locat, int address, int street, IQueue<VOMovingViolations> lista) {
		super(lista);
		xcoord = (int) (xcoor*100);
		ycoord = (int) (ycoor*100);
		location = locat;
		addressID = address;
		streetID = street;

	}
	
	/*
	 * Metodos
	 */

	/**
	 * Gets the xcoord.
	 *
	 * @return the xcoord
	 */
	public double getXcoord() {
		return xcoord/100.;
	}


	/**
	 * Sets the xcoord.
	 *
	 * @param xcoord the xcoord to set
	 */
	public void setXcoord(double xcoord) {
		this.xcoord = (int) (xcoord*100);
	}


	/**
	 * Gets the ycoord.
	 *
	 * @return the ycoord
	 */
	public double getYcoord() {
		return ycoord/100.;
	}


	/**
	 * Sets the ycoord.
	 *
	 * @param ycoord the ycoord to set
	 */
	public void setYcoord(double ycoord) {
		this.ycoord = (int) (ycoord*100);
	}


	/**
	 * Gets the adress ID.
	 *
	 * @return the adressID
	 */
	public int getAdressID() {
		return addressID;
	}


	/**
	 * Sets the adress ID.
	 *
	 * @param adressID the adressID to set
	 */
	public void setAdressID(int adressID) {
		this.addressID = adressID;
	}


	/**
	 * Gets the street ID.
	 *
	 * @return the streetID
	 */
	public int getStreetID() {
		return streetID;
	}


	/**
	 * Sets the street ID.
	 *
	 * @param streetID the streetID to set
	 */
	public void setStreetID(int pStreetID) {
		this.streetID = pStreetID;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	@Override
	public int compareTo(InfraccionesLocalizacion otro) {
		return totalInfracciones - otro.getTotalInfracciones();
	}

}
