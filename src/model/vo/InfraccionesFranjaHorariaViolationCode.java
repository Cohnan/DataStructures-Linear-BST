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
	 * Metodo que permite actualizar la estadistica con la informacion de una nueva infraccion
	 * @param nuevaInfraccion informacion con informacion relevante a contabilizar en la estadistica
	 */
	@Override
	public void agregarEstadistica(VOMovingViolations nuevaInfraccion) {
		// Actualizar datos comunes a todas las estadisticas
		super.agregarEstadistica(nuevaInfraccion);
		
		// Actualizacion de la tabla de hash con los violation codes encontrados en la franja de esta estadistica
		InfraccionesViolationCode estadVOCode = infViolationCode.get(nuevaInfraccion.getViolationCode());
		
		if (estadVOCode == null) {
			estadVOCode = new InfraccionesViolationCode(nuevaInfraccion.getViolationCode());
			infViolationCode.put(nuevaInfraccion.getViolationCode(), estadVOCode);
		}
		
		estadVOCode.agregarEstadistica(nuevaInfraccion);
		//infViolationCode.put(nuevaInfraccion.getViolationCode(), estadVOCode); // Es necesario por el new TODO
		
	}
	
	/**
	 * Metodo que devuelve la estadistica resultado de restar estadisticas con franjas horarias que empiezan a media noche
	 * @param aEliminar Franja a restar del inicio de la estadistica actual
	 * @return Estadistica restada
	 */
	public InfraccionesFranjaHorariaViolationCode eliminarEstadisticas(InfraccionesFranjaHorariaViolationCode aEliminar) {
		// Asegurarse de que ambas franjas empiezan a media noche
		if (!this.getFranjaInicial().equals(LocalTime.of(0, 0)) || !aEliminar.getFranjaInicial().equals(LocalTime.of(0, 0))) throw new IllegalArgumentException("Solo se pueden restar estadisticas si ambas inician a la misma hora");
		// Asegurarse de que la franja a eliminar termina mas temprano
		if (this.getFranjaFinal().compareTo(aEliminar.getFranjaFinal()) <= 0) throw new IllegalArgumentException("No se puede restar la estadistica de una franja que termina mas tarde");
		
		// Datos basicos de la nueva estadistica
		LocalTime horaInicial = aEliminar.getFranjaFinal().plusSeconds(1);
		LocalTime horaFinal = this.getFranjaFinal();
		InfraccionesFranjaHorariaViolationCode resultado = new InfraccionesFranjaHorariaViolationCode(horaInicial, horaFinal);
		resultado.totalInfracciones = this.totalInfracciones - aEliminar.totalInfracciones;
		resultado.totalConAccidentes = this.totalConAccidentes - aEliminar.totalConAccidentes;
		resultado.totalSinAccidentes = this.totalSinAccidentes - aEliminar.totalSinAccidentes;
		resultado.valorTotal = this.valorTotal - aEliminar.valorTotal;
		
		// Crear tabla de hash de la nueva estadistica
		InfraccionesViolationCode aRestar;
		InfraccionesViolationCode estResultante;
		for (String codigo : this.getInfViolationCode()) { // Restar del conjunto mas grande
			aRestar = aEliminar.getInfViolationCode().get(codigo);
			
			if(aRestar == null) { // Si el codigo actual surgio por primera vez luego de terminada la primera estadistica
				resultado.infViolationCode.put(codigo, this.infViolationCode.get(codigo));
			} else {
				estResultante = (this.infViolationCode.get(codigo)).eliminarEstadisticas(aRestar); 
				if (estResultante.getTotalInfracciones() != 0) resultado.infViolationCode.put(codigo, estResultante); // No poner codigos que no contengan infracciones
			}
		}
		
		return resultado;
	}
	
	/**
	 * Metodo que devuelve la estadistica resultado de sumar estadisticas con franjas horarias contiguas
	 * @param aIncrementar Franja a sumar al final de la estadistica actual
	 * @return Estadistica suma
	 */
	public InfraccionesFranjaHorariaViolationCode incrementarEstadisticas(InfraccionesFranjaHorariaViolationCode aIncrementar) {
		// Asegurarse de que son estadisticas de franjas contiguas
		if (!this.getFranjaFinal().plusSeconds(1).equals(aIncrementar.getFranjaInicial())) throw new IllegalArgumentException("Solo se pueden sumar una estadistica que empieze inmediatamente despues");
		
		// Crear estadistica con datos generales
		LocalTime horaInicial = this.getFranjaInicial();
		LocalTime horaFinal = aIncrementar.getFranjaFinal();
		InfraccionesFranjaHorariaViolationCode resultado = new InfraccionesFranjaHorariaViolationCode(horaInicial, horaFinal);
		resultado.totalInfracciones = this.totalInfracciones + aIncrementar.totalInfracciones;
		resultado.totalConAccidentes = this.totalConAccidentes + aIncrementar.totalConAccidentes;
		resultado.totalSinAccidentes = this.totalSinAccidentes + aIncrementar.totalSinAccidentes;
		resultado.valorTotal = this.valorTotal + aIncrementar.valorTotal;
		
		// Crear tabla de InfraccionesViolationCode para la nueva estadistica
		// Agregar primero los valores finales para las estadisticas de los codigos de la estadistica actual
		InfraccionesViolationCode aSumar;
		for (String codigo : this.getInfViolationCode()) {
			aSumar = aIncrementar.getInfViolationCode().get(codigo);
			
			if(aSumar == null) {
				resultado.infViolationCode.put(codigo, this.infViolationCode.get(codigo));//.incrementarEstadisticas(new InfraccionesViolationCode(codigo))); // Agregar una estadistica vacia permite que a lo agregado a la nueva tabla sea solo una copia
			} else {
				resultado.infViolationCode.put(codigo, this.infViolationCode.get(codigo).incrementarEstadisticas(aSumar));
			}
		}
		// Agregar las del segundo que no estan en el primero
		for (String codigo : aIncrementar.getInfViolationCode()) {			
			if(this.infViolationCode.get(codigo) == null) {
				resultado.infViolationCode.put(codigo, aIncrementar.infViolationCode.get(codigo));//.incrementarEstadisticas(new InfraccionesViolationCode(codigo))); // Copia
			} 
		}
		
		return resultado;
	}
}
