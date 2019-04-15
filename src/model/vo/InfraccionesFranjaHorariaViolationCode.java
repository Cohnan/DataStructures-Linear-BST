package model.vo;

import java.time.LocalTime;

import model.data_structures.IQueue;
import model.data_structures.ITablaHash;
import model.data_structures.LinProbTH;
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
	
	private LinProbTH<String, InfraccionesViolationCode> infViolationCode;

	/*
	 * Constructores
	 */
	/**
	 * Instantiates a new object.
	 *
	 */
	public InfraccionesFranjaHorariaViolationCode(LocalTime hInicial, LocalTime hFinal) {		
		super(hInicial, hFinal);
		this.infViolationCode = new LinProbTH<String, InfraccionesViolationCode>(4);
	}
	
	/**
	 * Instantiates a new object.
	 *
	 */
//	public InfraccionesFranjaHorariaViolationCode(LocalTime hInicial, LocalTime hFinal, IQueue<VOMovingViolations> lista, IQueue<InfraccionesViolationCode> pInfViolationCode) {		
//		super(hInicial, hFinal, lista);
//		this.infViolationCode = pInfViolationCode;
//	}

	/*
	 * Metodos
	 */
	/**
	 * @return the infViolationCode
	 */
	public LinProbTH<String, InfraccionesViolationCode> getInfViolationCode() {
		return infViolationCode;
	}

	/**
	 * @param infViolationCode the infViolationCode to set
	 */
//	public void setInfViolationCode(IQueue<InfraccionesViolationCode> infViolationCode) {
//		this.infViolationCode = infViolationCode;
//	}
	
	// TODO no se si tenga sentido, no lo he pensado
//	public void agregarVOCode(InfraccionesViolationCode voCode) {
//		this.infViolationCode.enqueue(voCode);
//	}
	
	@Override
	public void agregarEstadistica(VOMovingViolations nuevaInfraccion) {
		super.agregarEstadistica(nuevaInfraccion);
		
		InfraccionesViolationCode estadVOCode = infViolationCode.get(nuevaInfraccion.getViolationCode());
		
		if (estadVOCode == null) estadVOCode = new InfraccionesViolationCode(nuevaInfraccion.getViolationCode());
		
		estadVOCode.agregarEstadistica(nuevaInfraccion);
		infViolationCode.put(nuevaInfraccion.getViolationCode(), estadVOCode); //TODO check if this si necessary
		
	}
	
	// A restar tiene que se estrictamente mejor en tiempo
	public InfraccionesFranjaHorariaViolationCode eliminarEstadisticas(InfraccionesFranjaHorariaViolationCode aEliminar) {
		// Asegurarse de que ambas franjas empiezan a media noche
		if (!this.getFranjaInicial().equals(LocalTime.of(0, 0)) || !aEliminar.getFranjaInicial().equals(LocalTime.of(0, 0))) throw new IllegalArgumentException("Solo se pueden restar estadisticas si ambas inician a la misma hora");
		// Asegurarse de que la franja a eliminar termina mas tarde
		if (this.getFranjaFinal().compareTo(aEliminar.getFranjaFinal()) <= 0) throw new IllegalArgumentException("No se puede restar la estadistica de una franja que termina mas tarde");
		// Si las 2 franjas son iguales, entonces simplemente retorne una estadistica vacia
		//if (this.getFranjaFinal().compareTo(aEliminar.getFranjaFinal()) == 0) return new InfraccionesFranjaHorariaViolationCode(LocalTime.of(0, 0), LocalTime.of(0, 0));
		
		LocalTime horaInicial = aEliminar.getFranjaFinal().plusSeconds(1);
		LocalTime horaFinal = this.getFranjaFinal();
		InfraccionesFranjaHorariaViolationCode resultado = new InfraccionesFranjaHorariaViolationCode(horaInicial, horaFinal);
		resultado.totalInfracciones = this.totalInfracciones - resultado.totalInfracciones;
		this.totalConAccidentes = this.totalConAccidentes - resultado.totalConAccidentes;
		this.totalSinAccidentes = this.totalSinAccidentes - resultado.totalSinAccidentes;
		this.valorTotal = this.valorTotal - resultado.valorTotal;
		
		InfraccionesViolationCode aRestar;
		for (String codigo : this.getInfViolationCode()) {
			aRestar = aEliminar.getInfViolationCode().get(codigo);
			
			if(aRestar == null) {
				resultado.infViolationCode.put(codigo, this.infViolationCode.get(codigo));
			} else {
				resultado.infViolationCode.put(codigo, infViolationCode.get(codigo).eliminarEstadisticas(aRestar));
			}
		}
		
		return resultado;
	}
	
	public InfraccionesFranjaHorariaViolationCode incrementarEstadisticas(InfraccionesFranjaHorariaViolationCode aIncrementar) {
		// Asegurarse de que son estadisticas de franjas contiguas
		if (this.getFranjaFinal().plusSeconds(1).equals(aIncrementar.getFranjaInicial())) throw new IllegalArgumentException("Solo se pueden sumar una estadistica que empieze inmediatamente despues");
		
		
		LocalTime horaInicial = this.getFranjaInicial();
		LocalTime horaFinal = aIncrementar.getFranjaFinal();
		InfraccionesFranjaHorariaViolationCode resultado = new InfraccionesFranjaHorariaViolationCode(horaInicial, horaFinal);
		resultado.totalInfracciones = this.totalInfracciones + aIncrementar.totalInfracciones;
		resultado.totalConAccidentes = this.totalConAccidentes + aIncrementar.totalConAccidentes;
		resultado.totalSinAccidentes = this.totalSinAccidentes + aIncrementar.totalSinAccidentes;
		resultado.valorTotal = this.valorTotal + aIncrementar.valorTotal;
		
		// Crear nueva tabla de InfraccionesViolationCode
		InfraccionesViolationCode aSumar;
		for (String codigo : this.getInfViolationCode()) {
			aSumar = aIncrementar.getInfViolationCode().get(codigo);
			
			if(aSumar == null) {
				resultado.infViolationCode.put(codigo, this.infViolationCode.get(codigo));
			} else {
				resultado.infViolationCode.put(codigo, infViolationCode.get(codigo).incrementarEstadisticas(aSumar));
			}
		}
		
		return resultado;
	}
}
