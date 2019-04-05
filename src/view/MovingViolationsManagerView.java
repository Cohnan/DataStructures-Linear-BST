package view;

import model.data_structures.IArregloDinamico;
import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.MaxHeapCP;
import model.vo.VOColeccion;
import model.vo.VOMovingViolations;
import model.vo.VOViolationCode;

public class MovingViolationsManagerView 
{
	/**
	 * Constante con el nÃºmero maximo de datos maximo que se deben imprimir en consola
	 */
	public static final int N = 20;

	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Proyecto 2----------------------");
		System.out.println("0. Cargar datos de un Semestre");

		System.out.println("1. R1A");
		System.out.println("2. R2A");
		System.out.println("3. R3A");

		System.out.println("4. R1B");
		System.out.println("5. R2B");
		System.out.println("6. R3B");


		System.out.println("7. R1C");
		System.out.println("8. R2C");
		System.out.println("9. R3C");
		System.out.println("10. R4C");


		System.out.println("11. Salir");
		System.out.println("Digite el nï¿½mero de opciï¿½n para ejecutar la tarea, luego presione enter: (Ej., 1):");

	}


	/**
	 *Envia un mensaje a consola
	 * @param Mensaje a enviar a consola
	 */
	public void printMessage(String mensaje) {
		System.out.println(mensaje);
	}

	/**
	 * Imprime la informaciï¿½n sobre la carga de datos
	 * @param Cola con los datos cargados
	 */
	public void printMovingViolationsLoadInfo(IArregloDinamico<Integer> resultados0, float xmin, float ymin, float xmax, float ymax, int numSem ) {
		int totalInfracciones = 0;
		int totalMeses = resultados0.darTamano();
		int infMes;
		System.out.println("  ----------Informacion Sobre la Carga------------------  ");
		for (int i = 0; i < totalMeses; i++) {
			infMes = resultados0.darObjeto(i);
			System.out.println("Infracciones en " + ObtenerMes(i + 6*(numSem-1) +1)+": " + infMes);
			totalInfracciones += infMes;
		}
		System.out.println("Total Infracciones Semestre: " + totalInfracciones);
		System.out.println(" ----------Zona Geogrï¿½fica----------------------");
		System.out.println("Las coordenadas (Xmin,Ymin) son: ("+ xmin +", " +ymin+")"  );
		System.out.println("Las coordenadas (Xmax,Ymax) son: ("+ xmax +", " +ymax+")"  );
	}


	public void requerimiento1B(MaxHeapCP<VOColeccion> datos, int n){
		
		if(n>datos.darNumElementos()){
			System.out.println("No hay suficientes tipos de infracciones intente con un valor menor a: " +datos.darNumElementos());
			return;
		}
		
		
		System.out.println(datos.darNumElementos());
		System.out.println("Código"+"\t"+"\t"+"Infracciones"+"\t"+"\t"+"%conAccidente"+"\t"+"\t"+"%SinAccidente"+"\t"+"\t"+"A Pagar");

		for (int i = 0; i < n; i++) {
			VOColeccion s = datos.delMax();
			System.out.println(s.darCodigo()+"\t"+"\t"+s.darTotalInfracciones()+"\t"+"\t"+"\t"+"\t"+s.darPorcentajeConInfracciones()+"\t"+"\t"+s.darPorcentajeSinInfracciones()+"\t"+"\t"+"\t"+s.darTotalPagar()+"$");
		}
		
	}
	//	/**
	//	 *Imprime el requerimiento 1A - Verifique Unique ID
	//	 * @param Cola con los ObjectID repetidos
	//	 */
	//	public void printMovingViolationsReq1(IQueue<VOMovingViolations> resultados1) {
	//		if (resultados1.size() == 0) {
	//			System.out.println("El objectId es ï¿½nico");
	//			return;
	//		}
	//		for(VOMovingViolations v: resultados1) {
	//			System.out.println("ObjectID: " + v.objectId());
	//		}
	//	}
	//	
	//	/**
	//	 * Imprime el requerimiento 2A - Consultar Infracciones por rango de horas
	//	 * @param Una cola con las infracciones en el rango
	//	 */
	//	public void printMovingViolationsReq2(IQueue<VOMovingViolations> resultados2) {
	//		for(VOMovingViolations v: resultados2) {
	//			System.out.println("ObjectID: " + v.objectId() + ", issued: " + v.getTicketIssueDate());
	//		}
	//	}
	//	
	//	
	//	/**
	//	 *Imprime el requerimiento 4A - Consultar infraciones en una direcciï¿½n en un rango de fecha
	//	 * @param Las infracciones ordenadas descendentemente por STREETSEGID y fecha
	//	 */
	//	public void printMovingViolationsReq4(IStack<VOMovingViolations> resultados4) {
	//		System.out.println("OBJECTID\t TICKETISSUEDAT\t STREETSEGID\t ADDRESS_ID");
	//
	//		for(VOMovingViolations v: resultados4) {
	//			System.out.println( v.objectId() + "\t" + v.getTicketIssueDate() + "\t" + v.getStreetsegID() + "\t" + v.getAddressID());
	//		}
	//	}
	//	/**
	//	 *Imprime el requerimiento 1B - Consultar tipos de infracciones con su fineAmt promedio
	//	 * @param Cola con los tipos de infracciones y su respeectivo FINEAMT
	//	 */
	//	public void printViolationCodesReq5(IQueue<VOViolationCode> violationCodes) {
	//		System.out.println("VIOLATIONCODE\t FINEAMT promedio");
	//
	//		for(VOViolationCode v: violationCodes) {
	//			System.out.println(v.getViolationCode() + "\t" + v.getAvgFineAmt());
	//		}
	//	}
	//	
	//	/**
	//	 *Imprime el requerimiento 2B - Consultar infracciones que TOTALPAID este en un rango
	//	 * @param Una pila con las infracciones
	//	 */
	//	public void printMovingViolationReq6(IStack<VOMovingViolations> resultados6) {
	//		System.out.println("OBJECTID\t TICKETISSUEDAT\t TOTALPAID");
	//		for(VOMovingViolations v: resultados6) {
	//			System.out.println( v.objectId() + "\t" + v.getTicketIssueDate() + "\t" + v.getTotalPaid());
	//		}
	//	}
	//	
	//	/**
	//	 *Imprime el requerimiento 3B - Consultar infracciones por hora inicial y hora final
	//	 * @param Cola con las infracciones en el rango ordenadas ascendentemente por VIOLATIONDESC
	//	 */
	//	public void printMovingViolationsReq7(IQueue<VOMovingViolations> resultados7) {
	//		System.out.println("OBJECTID\t TICKETISSUEDAT\t VIOLATIONDESC");
	//		for(VOMovingViolations v: resultados7) {
	//			System.out.println( v.objectId() + "\t" + v.getTicketIssueDate() + "\t" + v.getViolationDescription());
	//		}
	//	}
	//	
	//	/**
	//	 *Imprime el requerimiento 2C - Grï¿½fica de infracciones que tuvieron accidentees por hora
	//	 * @param Porcentajes de infracciones que tuvieron accidentes en cada hora [0,23]
	//	 */
	//	public void printMovingViolationsByHourReq10(double[] resultados10) {
	//		double pX = 0.1; // Porcentaje que representa cada X
	//		System.out.println("Porcentaje de infracciones que tuvieron accidentes por hora. 2018");
	//		System.out.println("Hora| % de accidentes");
	//		for (int i = 0; i < resultados10.length; i++) {
	//			
	//			if(i<10)System.out.print("0"+i+"  |  ");
	//			else System.out.print(i+"  |  ");
	//			
	//				for(double j = pX; j<resultados10[i];j+=pX){
	//					System.out.print("X");
	//				}
	//			System.out.println("");
	//		}
	//		System.out.println("Cada X representa " + pX +"%");
	//	}
	//	
	//	
	//	/**
	//	 *Imprime el requerimiento 4C  - Grï¿½fica de la deuda por mes
	//	 * @param Deuda acumulada por mes de infracciones
	//	 */
	//	public void printTotalDebtbyMonthReq12(double[] resultados12) {
	//		double vX = 600000; 
	//		
	//		System.out.println("Deuda acumulada por mes de infracciones. 2018");
	//		System.out.println("Mes| Dinero");
	//		for (int m = 1; m <= 4; m++) {
	//			System.out.printf("%02d|", m);
	//			for (int k = 0; k < (int) (-resultados12[m-1]/vX); k++) {
	//				System.out.print("X");
	//			}
	//			System.out.println("");
	//		}
	//		System.out.println("Cada X representa $" + vX + "USD");
	//	}

	public String ObtenerMes(int pMes){

		if(pMes == 1){ return "Enero";}
		else if(pMes == 2){ return "Febrero";}
		else if(pMes == 3){ return "Marzo";}
		else if(pMes == 4){ return "Abril";}
		else if(pMes == 5){ return "Mayo";}
		else if(pMes == 6){ return "Junio";}
		else if(pMes == 7){ return "Julio";}
		else if(pMes == 8){ return "Agosto";}
		else if(pMes == 9){ return "Septiembre";}
		else if(pMes == 10){ return "Octubre";}
		else if(pMes == 11){ return "Noviembre";}
		else{ return "Diciembre";}
	}

}
