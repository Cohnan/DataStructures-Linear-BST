package view;

import model.data_structures.IQueue;
import model.data_structures.IStack;

import model.vo.VOMovingViolations;
import model.vo.VOViolationCode;

public class MovingViolationsManagerView 
{
	/**
	 * Constante con el número maximo de datos maximo que se deben imprimir en consola
	 */
	public static final int N = 20;
	
	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Taller 3----------------------");
		System.out.println("0. Cargar datos del cuatrimestre");
		System.out.println("1. Verificar que OBJECTID es en realidad un identificador unico");
		System.out.println("2. Consultar infracciones por fecha/hora inicial y fecha/hora final");
		System.out.println("3. Dar FINEAMT promedio con y sin accidente por VIOLATIONCODE");
		System.out.println("4. Consultar infracciones por direccion entre fecha inicial y fecha final");

		
		System.out.println("5. Consultar los tipos de infracciones (VIOLATIONCODE) con su valor (FINEAMT) promedio en un rango dado");
		System.out.println("6. Consultar infracciones donde la cantidad pagada (TOTALPAID) esta en un rango dado. Se ordena por fecha de infracci�n");
		System.out.println("7. Consultar infracciones por hora inicial y hora final, ordenada ascendentemente por VIOLATIONDESC");
		System.out.println("8. Dado un tipo de infraccion (VIOLATIONCODE) informar el (FINEAMT) promedio y su desviacion estandar.");

		System.out.println("9. El numero de infracciones que ocurrieron en un rango de horas del dia. Se define el rango de horas por valores enteros en el rango [0, 24]");
		System.out.println("10. Grafica ASCII con el porcentaje de infracciones que tuvieron accidentes por hora del dia");
		System.out.println("11. La deuda (TOTALPAID � FINEAMT - PENALTY1 – PENALTY2) total por infracciones que se dieron en un rango de fechas.");
		System.out.println("12. Grafica ASCII con la deuda acumulada total por infracciones");

		
		System.out.println("13. Salir");
		System.out.println("Digite el n�mero de opci�n para ejecutar la tarea, luego presione enter: (Ej., 1):");
		
	}
	
	
	/**
	 *Envia un mensaje a consola
	 * @param Mensaje a enviar a consola
	 */
	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}
	
	/**
	 *Imprime la informaci�n sobre la carga de datos
	 * @param Cola con los datos cargados
	 */
	public void printMovingViolationsLoadInfo(IQueue<Integer> resultados0) {
		int totalInfracciones = 0;
		int totalMeses = resultados0.size();
		int infMes;
		System.out.println("  ----------Informaci�n Sobre la Carga------------------  ");
		for (int i = 0; i < totalMeses; i++) {
			infMes = resultados0.dequeue();
			System.out.println("Infracciones Mes " + (i+1)+": " + infMes);
			totalInfracciones += infMes;
		}
		System.out.println("Total Infracciones Cuatrisemetre: " + totalInfracciones);
		
	}
	/**
	 *Imprime el requerimiento 1A - Verifique Unique ID
	 * @param Cola con los ObjectID repetidos
	 */
	public void printMovingViolationsReq1(IQueue<VOMovingViolations> resultados1) {
		if (resultados1.size() == 0) {
			System.out.println("El objectId es �nico");
			return;
		}
		for(VOMovingViolations v: resultados1) {
			System.out.println("ObjectID: " + v.objectId());
		}
	}
	
	/**
	 * Imprime el requerimiento 2A - Consultar Infracciones por rango de horas
	 * @param Una cola con las infracciones en el rango
	 */
	public void printMovingViolationsReq2(IQueue<VOMovingViolations> resultados2) {
		for(VOMovingViolations v: resultados2) {
			System.out.println("ObjectID: " + v.objectId() + ", issued: " + v.getTicketIssueDate());
		}
	}
	
	
	/**
	 *Imprime el requerimiento 4A - Consultar infraciones en una direcci�n en un rango de fecha
	 * @param Las infracciones ordenadas descendentemente por STREETSEGID y fecha
	 */
	public void printMovingViolationsReq4(IStack<VOMovingViolations> resultados4) {
		System.out.println("OBJECTID\t TICKETISSUEDAT\t STREETSEGID\t ADDRESS_ID");

		for(VOMovingViolations v: resultados4) {
			System.out.println( v.objectId() + "\t" + v.getTicketIssueDate() + "\t" + v.getStreetsegID() + "\t" + v.getAddressID());
		}
	}
	/**
	 *Imprime el requerimiento 1B - Consultar tipos de infracciones con su fineAmt promedio
	 * @param Cola con los tipos de infracciones y su respeectivo FINEAMT
	 */
	public void printViolationCodesReq5(IQueue<VOViolationCode> violationCodes) {
		System.out.println("VIOLATIONCODE\t FINEAMT promedio");

		for(VOViolationCode v: violationCodes) {
			System.out.println(v.getViolationCode() + "\t" + v.getAvgFineAmt());
		}
	}
	
	/**
	 *Imprime el requerimiento 2B - Consultar infracciones que TOTALPAID este en un rango
	 * @param Una pila con las infracciones
	 */
	public void printMovingViolationReq6(IStack<VOMovingViolations> resultados6) {
		System.out.println("OBJECTID\t TICKETISSUEDAT\t TOTALPAID");
		for(VOMovingViolations v: resultados6) {
			System.out.println( v.objectId() + "\t" + v.getTicketIssueDate() + "\t" + v.getTotalPaid());
		}
	}
	
	/**
	 *Imprime el requerimiento 3B - Consultar infracciones por hora inicial y hora final
	 * @param Cola con las infracciones en el rango ordenadas ascendentemente por VIOLATIONDESC
	 */
	public void printMovingViolationsReq7(IQueue<VOMovingViolations> resultados7) {
		System.out.println("OBJECTID\t TICKETISSUEDAT\t VIOLATIONDESC");
		for(VOMovingViolations v: resultados7) {
			System.out.println( v.objectId() + "\t" + v.getTicketIssueDate() + "\t" + v.getViolationDescription());
		}
	}
	
	/**
	 *Imprime el requerimiento 2C - Gr�fica de infracciones que tuvieron accidentees por hora
	 * @param Porcentajes de infracciones que tuvieron accidentes en cada hora [0,23]
	 */
	public void printMovingViolationsByHourReq10(double[] resultados10) {
		double pX = 0.1; // Porcentaje que representa cada X
		System.out.println("Porcentaje de infracciones que tuvieron accidentes por hora. 2018");
		System.out.println("Hora| % de accidentes");
		for (int i = 0; i < resultados10.length; i++) {
			
			if(i<10)System.out.print("0"+i+"  |  ");
			else System.out.print(i+"  |  ");
			
				for(double j = pX; j<resultados10[i];j+=pX){
					System.out.print("X");
				}
			System.out.println("");
		}
		System.out.println("Cada X representa " + pX +"%");
	}
	
	
	/**
	 *Imprime el requerimiento 4C  - Gr�fica de la deuda por mes
	 * @param Deuda acumulada por mes de infracciones
	 */
	public void printTotalDebtbyMonthReq12(double[] resultados12) {
		double vX = 600000; 
		
		System.out.println("Deuda acumulada por mes de infracciones. 2018");
		System.out.println("Mes| Dinero");
		for (int m = 1; m <= 4; m++) {
			System.out.printf("%02d|", m);
			for (int k = 0; k < (int) (-resultados12[m-1]/vX); k++) {
				System.out.print("X");
			}
			System.out.println("");
		}
		System.out.println("Cada X representa $" + vX + "USD");
	}
}
