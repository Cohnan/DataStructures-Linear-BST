package model.vo;


import java.time.LocalTime;

import model.data_structures.IQueue;

/**
 * Agrupa las infracciones por un rango de fecha/hora segun sea el caso
 */

public class InfraccionesFranjaHoraria extends EstadisticaInfracciones implements Comparable<InfraccionesFranjaHoraria> {	

	@Override
	public String toString() {
		return "InfraccionesFranjaHoraria [hora_Inicial=" + hora_Inicial + ",\n hora_Final=" + hora_Final
				+ ",\n totalInfracciones=" + totalInfracciones + ",\n porcentajeAccidentes=" + getPorcentajeAccidentes()
				+ ",\n porcentajeNoAccidentes=" + getPorcentajeNoAccidentes() + ",\n valorTotal=" + valorTotal + "]\n\n";
	}

	/** The franja horaria inicial. */
	
	private LocalTime hora_Inicial;
	
	/** The franja horaria final. */
	
	private LocalTime hora_Final;
	
	/*
	 * Constructores
	 */
	/**
	 * Instantiates a new franja horaria
	 */
	
	public InfraccionesFranjaHoraria(LocalTime fInicial, LocalTime fFinal) {
		super();
		this.hora_Inicial = fInicial;
		this.hora_Final = fFinal;		
	}
	
	/**
	 * Instantiates a new franja horaria.
	 * @param lista Cola con las infraciones que pertenecen al rango de horas establecido
	 */
	
	public InfraccionesFranjaHoraria(LocalTime fInicial, LocalTime fFinal, IQueue<VOMovingViolations> lista) {
		super(lista);
		this.hora_Inicial = fInicial;
		this.hora_Final = fFinal;		
	}

	/*
	 * Metodos
	 */
	/**
	 * @return the fechaInicial
	 */
	public LocalTime getFranjaInicial() {
		return hora_Inicial;
	}

	/**
	 * @return the fechaFinal
	 */
	public LocalTime getFranjaFinal() {
		return hora_Final;
	}

	@Override
	public int compareTo(InfraccionesFranjaHoraria otro) {
		return totalInfracciones - otro.getTotalInfracciones();
	}
	
}
