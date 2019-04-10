package model.vo;

import java.time.LocalTime;

import model.data_structures.IQueue;
import model.data_structures.Queue;

/**
 * Brinda la informaci�n del requerimiento 2C
 */

public class InfraccionesFranjaHorariaViolationCode extends InfraccionesFranjaHoraria {
	
	@Override
	public String toString() {
		return "InfraccionesFranjaHorariaViolationCode [totalInfracciones="
				+ totalInfracciones + ",\n porcentajeAccidentes=" + getPorcentajeAccidentes() + ",\n porcentajeNoAccidentes="
				+ getPorcentajeNoAccidentes() + ",\n valorTotal=" + valorTotal + "]\n";
	}

	/**
	 * Agrupa las infracciones del rango de hora por ViolationCode y muestra sus estadisticas
	 */
	
	private IQueue<InfraccionesViolationCode> infViolationCode;

	/*
	 * Constructores
	 */
	/**
	 * Instantiates a new object.
	 *
	 */
	public InfraccionesFranjaHorariaViolationCode(LocalTime hInicial, LocalTime hFinal) {		
		super(hInicial, hFinal);
		this.infViolationCode = new Queue<InfraccionesViolationCode>();
	}
	
	/**
	 * Instantiates a new object.
	 *
	 */
	public InfraccionesFranjaHorariaViolationCode(LocalTime hInicial, LocalTime hFinal, IQueue<VOMovingViolations> lista, IQueue<InfraccionesViolationCode> pInfViolationCode) {		
		super(hInicial, hFinal, lista);
		this.infViolationCode = pInfViolationCode;
	}

	/*
	 * Metodos
	 */
	/**
	 * @return the infViolationCode
	 */
	public IQueue<InfraccionesViolationCode> getInfViolationCode() {
		return infViolationCode;
	}

	/**
	 * @param infViolationCode the infViolationCode to set
	 */
	public void setInfViolationCode(IQueue<InfraccionesViolationCode> infViolationCode) {
		this.infViolationCode = infViolationCode;
	}
	
	// TODO no se si tenga sentido, no lo he pensado
	public void agregarVOCode(InfraccionesViolationCode voCode) {
		this.infViolationCode.enqueue(voCode);
	}
}
