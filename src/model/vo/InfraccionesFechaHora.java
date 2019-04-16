package model.vo;

import java.time.LocalDateTime;
import java.util.Comparator;

import model.data_structures.IQueue;

public class InfraccionesFechaHora extends EstadisticaInfracciones {

	@Override
	public String toString() {
		return "InfraccionesFechaHora [fecha_hora_inicial=" + fecha_hora_inicial + ",\n fecha_hora_final="
				+ fecha_hora_final + ",\n totalInfracciones=" + totalInfracciones + ",\n porcentajeAccidentes="
				+ getPorcentajeAccidentes() + ",\n porcentajeNoAccidentes=" + getPorcentajeNoAccidentes() + ",\n valorTotal="
				+ valorTotal + "]\n\n";
	}

	private LocalDateTime fecha_hora_inicial;
	
	private LocalDateTime fecha_hora_final;
	
	public InfraccionesFechaHora(LocalDateTime pFechaHoraIni, LocalDateTime pFechaHoraFin) {
		super();
		fecha_hora_inicial = pFechaHoraIni; 
		fecha_hora_final = pFechaHoraFin; 
	}
	
	public InfraccionesFechaHora(LocalDateTime pFechaHoraIni, LocalDateTime pFechaHoraFin, IQueue<VOMovingViolations> lista) {
		super(lista);
		fecha_hora_inicial = pFechaHoraIni; 
		fecha_hora_final = pFechaHoraFin; 
	}
	
	public static class NInfraccionesOrder implements Comparator<InfraccionesFechaHora> {

		@Override
		public int compare(InfraccionesFechaHora arg0, InfraccionesFechaHora arg1) {
			return arg0.totalInfracciones - arg1.totalInfracciones;
		}
		
	}
}
