package model.vo;

import model.data_structures.IQueue;

/**
 * Agrupa las infracciones mostrando estad�sticas sobre los datos 
 * como el total de infracciones que se presentan en ese conjunto,
 * el porcentaje de infracciones con y sin accidentes con respecto al total,
 * el valor total de las infracciones que se deben pagar y una lista con 
 * las infracciones. 
 */

public class EstadisticaInfracciones {
	
	@Override
	public String toString() {
		return "EstadisticaInfracciones [totalInfracciones=" + totalInfracciones + ",\n porcentajeAccidentes="
				+ getPorcentajeAccidentes() + ",\n porcentajeNoAccidentes=" + getPorcentajeNoAccidentes() + ",\n valorTotal="
				+ valorTotal + "]\n\n";
	}

	/**	
	 * Numero total de infraciones del conjunto
	 */
	
	protected int totalInfracciones;
	
	/**
	 * Porcentaje de las infracciones con accidentes con respecto al total
	 */
	
	//protected double porcentajeAccidentes;
	protected int totalConAccidentes;
	
	/**
	 * Porcentaje de las infracciones sin accidentes con respecto al total
	 */
	
	//protected double porcentajeNoAccidentes;
	protected int totalSinAccidentes;
	
	/**
	 * Valor total de las infracciones que se debe pagar.
	 */
	
	protected double valorTotal;	
	
	/**
	 * Lista con las infracciones que agrupa el conjunto
	 */
	
	//protected IQueue<VOMovingViolations> listaInfracciones;
	
	/*
	 * Constructor
	 */
	
	public EstadisticaInfracciones() {
		totalInfracciones = 0;
		totalSinAccidentes = 0;
		totalConAccidentes = 0;
		valorTotal = 0;
	}
	
	/**
	 * Crea unas estadisticas a partir de una lista de infracciones
	 * @param listaInfracciones - Lista con las infracciones que cumplen el criterio de agrupamiento
	 */
	
	public EstadisticaInfracciones(IQueue<VOMovingViolations> lista) {
		this();
		this.agregar(lista);
	}
	
	//=========================================================
	//Metodos Getters and Setters
	//=========================================================
	/**
	 * Permite actualizar la estadistica actual con la informacion de la infraccion que llega por parametro
	 * @param nuevaInfraccion
	 */
	public void agregarEstadistica(VOMovingViolations nuevaInfraccion) {
		totalInfracciones += 1;
		
		int conAccidente = nuevaInfraccion.getAccidentIndicator()? 1 : 0;
		totalConAccidentes += conAccidente;
		totalSinAccidentes += (1 - conAccidente);
		
		valorTotal += nuevaInfraccion.getTotalPaid();
	}
	
	/**
	 * Actualiza las estadisticas dada una lista de infracciones
	 * @param listaInfracciones - Lista con las infracciones que cumplen el criterio de agrupamiento
	 */
	
	public void agregar(IQueue<VOMovingViolations> lista) {
		totalInfracciones = lista.size();
		
		int conAccidente;
		for(VOMovingViolations infraccion : lista) {
			conAccidente = infraccion.getAccidentIndicator()? 1 : 0;
			totalConAccidentes += conAccidente;
			totalSinAccidentes += (1 - conAccidente);
		
			valorTotal += infraccion.getTotalPaid();
		}
	}
	
	/**
	 * Gets the total infracciones.
	 * @return the total infracciones
	 */
	
	public int getTotalInfracciones() {
		return totalInfracciones;
	}	
	
	
	/**
	 * Gets the porcentaje accidentes.	 *
	 * @return the porcentaje accidentes
	 */
	
	public double getPorcentajeAccidentes() {
		return totalInfracciones == 0? 0 : (1. * totalConAccidentes) / totalInfracciones;
	}	


	/**
	 * Gets the porcentaje no accidentes.
	 *
	 * @return the porcentaje no accidentes
	 */
	public double getPorcentajeNoAccidentes() {
		return totalInfracciones == 0? 0 : (1. * totalSinAccidentes) / totalInfracciones;
	}

	/**
	 * Gets the valor total.
	 *
	 * @return the valor total
	 */
	public double getValorTotal() {
		//TODO Completar para calcular el valor total de dinero que representan las infracciones
		return valorTotal;
	}	

	/**
	 * Gets the lista infracciones.
	 *
	 * @return the lista infracciones
	 */
	/*
	public IQueue<VOMovingViolations> getListaInfracciones() {
		return listaInfracciones;
	}
	*/
	/**
	 * Sets the lista infracciones.
	 *
	 * @param listaInfracciones the new lista infracciones
	 */
	/*
	public void setListaInfracciones(IQueue<VOMovingViolations> listaInfracciones) {
		this.listaInfracciones = listaInfracciones;
	}*/
}
