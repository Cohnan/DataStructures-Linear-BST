package controller;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
//import com.sun.xml.internal.ws.api.server.ContainerResolver;

//import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.*;
import java.time.format.*;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import model.util.Sort;

import model.data_structures.*;
import model.logic.*;
import model.vo.*;

import view.MovingViolationsManagerView;

public class Controller {
	/*
	 * Atributos
	 */
	/**
	 * Objeto de la Vista
	 */
	private MovingViolationsManagerView view;

	private MovingViolationsManager model;
	
	/*
	 * Constructor
	 */
	public Controller()
	{
		view = new MovingViolationsManagerView();
		model = new MovingViolationsManager();
	}

	/*
	 * Metodos
	 */
	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		Controller controller = new Controller();
		int option = -1;
		boolean numeroEncontrado = false;
		
		long startTime;
		long endTime;
		long duration;

		while(!fin)
		{
			view.printMenu();
			// Para tener que reiniciar el programa si no se da una opcion valida
			while (!numeroEncontrado){
				try {
					option = sc.nextInt();
					numeroEncontrado = true;
				} catch (InputMismatchException e) {
					System.out.println("Esa no es una opcion valida");
					view.printMenu();
					sc = new Scanner(System.in);
				}
			} numeroEncontrado = false;

			try { // Este try se usa para no tener que reiniciar el programa en caso de que 
				// ocurra un error pequenio al ejecutar como ingresar mal la fecha  

				switch(option)
				{
				case 0:
					view.printMessage("Ingrese el Semestre (1 -[Enero - Junio], 2[Julio - Diciembre])");
					int numeroSemestre = sc.nextInt();
					EstadisticasCargaInfracciones resultados0 = model.loadMovingViolations(numeroSemestre);
					view.printResumenLoadMovingViolations(resultados0);
					break;
				case 1:
					view.printMessage("1A. Consultar las N franjas horarias con mas infracciones que desea ver. Ingresar valor de N: ");
					int numeroFranjas = sc.nextInt();

					IQueue<InfraccionesFranjaHoraria> primeros = model.rankingNFranjas(numeroFranjas);
					view.printReq1A(primeros);
					break;

				case 2:
					view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 402915.72): ");
					double xCoord = sc.nextDouble();
					view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 138864.78): ");
					double yCoord = sc.nextDouble();
 
					view.printReq2A(model.consultarPorLocalizacionHash(xCoord, yCoord));
					break;

				case 3:
					view.printMessage("Ingrese la fecha inicial del rango. Formato año-mes-dia (ej. 2008-06-21)");
					String fechaInicialStr = sc.next();
					LocalDate fechaInicial = ManejoFechaHora.convertirFecha_LD( fechaInicialStr );

					view.printMessage("Ingrese la fecha final del rango. Formato año-mes-dia (ej. 2008-06-30)");
					String fechaFinalStr = sc.next();
					LocalDate fechaFinal = ManejoFechaHora.convertirFecha_LD( fechaFinalStr );

					//TODO Completar para la invocacion del metodo 3A
					//model.consultarInfraccionesPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal)

					//TODO Mostrar resultado de tipo Cola de InfraccionesFecha
					//view.printReq3A( ... )
					break;

/*
				case 4:
					view.printMessage("1B. Consultar los N Tipos con mas infracciones. Ingrese el valor de N: ");
					int numeroTipos = sc.nextInt();

					//TODO Completar para la invocación del metodo 1B				
					//model.rankingNViolationCodes(int N)
					
					//TODO Mostrar resultado de tipo Cola con N InfraccionesViolationCode
					//view.printReq1B( ... )
					break;

				case 5:						
					view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 402915.72): ");
					xcoord = sc.nextDouble();
					view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 138864.78): ");
					ycoord = sc.nextDouble();

					//TODO Completar para la invocación del metodo 2B
					//model.consultarPorLocalizacionArbol(double xCoord, double yCoord)

					//TODO Mostrar resultado de tipo InfraccionesLocalizacion 
					//view.printReq2B( ... )
					break;

				case 6:
					view.printMessage("Ingrese la cantidad minima de dinero que deben acumular las infracciones en sus rangos de fecha  (Ej. 1234,56)");
					double cantidadMinima = sc.nextDouble();

					view.printMessage("Ingrese la cantidad maxima de dinero que deben acumular las infracciones en sus rangos de fecha (Ej. 5678,23)");
					double cantidadMaxima = sc.nextDouble();

					//TODO Completar para la invocación del metodo 3B
					//model.consultarFranjasAcumuladoEnRango(double valorInicial, double valorFinal)

					//TODO Mostrar resultado de tipo Cola con InfraccionesFechaHora 
					//view.printReq3B( ... )
					break;
*/
				case 7:
					view.printMessage("1C. Consultar las infracciones con Address_Id. Ingresar el valor de Address_Id: ");
					int addressID = sc.nextInt();

					startTime = System.currentTimeMillis();
					//TODO Completar para la invocación del metodo 1C
					//model.consultarPorAddressId(int addressID)

					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 1C: " + duration + " milisegundos");

					//TODO Mostrar resultado de tipo InfraccionesLocalizacion 	
					//view.printReq1C( ... )
					break;

				case 8:
					view.printMessage("Ingrese la hora inicial del rango. Formato HH:MM:SS (ej. 09:30:00)");
					String horaInicialStr = sc.next();
					LocalTime horaInicial = ManejoFechaHora.convertirHora_LT(horaInicialStr);

					view.printMessage("Ingrese la hora final del rango. Formato HH:MM:SS (ej. 16:00:00)");
					String horaFinalStr = sc.next();
					LocalTime horaFinal = ManejoFechaHora.convertirHora_LT(horaFinalStr);

					startTime = System.currentTimeMillis();
					//TODO Completar para la invocacion del metodo 2C
					//model.consultarPorRangoHoras(LocalTime horaInicial, LocalTime horaFinal)

					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 2C: " + duration + " milisegundos");
					//TODO Mostrar resultado de tipo InfraccionesFranjaHorarioViolationCode
					//view.printReq2C( ... )
					break;

				case 9:
					view.printMessage("Consultar las N localizaciones geograficas con mas infracciones. Ingrese el valor de N: ");
					int numeroLocalizaciones = sc.nextInt();

					startTime = System.currentTimeMillis();
					//TODO Completar para la invocación del metodo 3C
					//model.rankingNLocalizaciones(int N)

					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 3C: " + duration + " milisegundos");
					//TODO Mostrar resultado de tipo Cola con InfraccionesLocalizacion
					//view.printReq3C( ... )
					break;

				case 10:

					System.out.println("Grafica ASCII con la informacion de las infracciones por ViolationCode");

					startTime = System.currentTimeMillis();
					//TODO Completar para la invocacion del metodo 4C
					//model.ordenarCodigosPorNumeroInfracciones()

					//TODO Mostrar grafica a partir del resultado del metodo anterior
					//view.printReq4C( ... )
					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 4C: " + duration + " milisegundos");
					break;

				case 4:
					MaxHeapCP<VOColeccion> resultador7 = controller.cargarInfraccionVOViolationCode();
					view.printMessage("Ingrese el n�mero de tipos de infracci�n que desea ver (N):");
					int n = sc.nextInt();
					view.requerimiento1B(resultador7, n);
					break;

				case 5:


					BlancoRojoBST<Coordenadas, VOColeccion> respuesta8 = controller.cargarInfraccionesCoordenadas();
					view.printMessage("Ingrese la coordenda X   (Ej. 398103.16)");
					float cordX = Float.parseFloat(sc.next());
					view.printMessage("Ingrese la coordenda Y   (Ej. 143451.72)");
					float cordY = Float.parseFloat(sc.next());
					Coordenadas aBuscar = new Coordenadas(cordX, cordY);
					if(!respuesta8.contains(aBuscar)){
						System.out.println("La coordenada ingresada no est� registrada en la base de datos");
						break;
					}
					else{
						VOColeccion respuesta = respuesta8.get(aBuscar);
						System.out.println("Informaci�n sobre la coordenada: (" +cordX +" , " + cordY+" )");
						System.out.println("Total Infracciones: "+ respuesta.darTotalInfracciones());
						System.out.println("Porcentaje Sin Accidente: " + respuesta.darPorcentajeSinInfracciones());
						System.out.println("Porcentaje Con Accidente: " + respuesta.darPorcentajeConInfracciones());
						System.out.println("Valor total a pagar: " + respuesta.darTotalPagar() +"$");
						//FALTAN COSAS PERO PREGUNTAR LABORATORIO

						break;
					}

				case 6:

					BlancoRojoBST<Integer, VOColeccion> resultado9 = controller.cargarInfraccionesValorAcumulado();
					System.out.println(resultado9.min());
					System.out.println(resultado9.max());
					
					view.printMessage("Valor Acumulado Inferior (US$):  (MIN = 78.810$)");
					int valorMin = sc.nextInt();

					view.printMessage("Valor Acumulado Superior (US$):  (MAX = 962.265$) ");
					int valorMax = sc.nextInt();
					
					Iterable aux = resultado9.valuesInRange(valorMin, valorMax);
					view.requerimiento3B(aux);
					break;

//				case 10:
//					double[] resultados10 = controller.percentWithAccidentsByHour();
//					view.printMovingViolationsByHourReq10(resultados10);
//					break;

				case 11:
					fin=true;
					sc.close();
					break;
				}
			} catch(Exception e) { // Si ocurrio un error al ejecutar algun metodo
				e.printStackTrace(); System.out.println("Ocurrio un error. Se recomienda reiniciar el programa.");}
		}
	}

	/*
	 * ************************************************************************************
	 * Metodos para los requerimientos del proyecto 2
	 * ************************************************************************************
	 */
	
	private MaxHeapCP<VOColeccion> cargarInfraccionVOViolationCode(){
		IArregloDinamico<VOMovingViolations> aux = movingVOLista;
		MaxHeapCP<VOColeccion> aux2 =  new MaxHeapCP<>();
		Sort.ordenarShellSort(aux, new VOMovingViolations.ViolationCodeOrder());
		String codigo = null;
		double inCon = 0;
		int total = 0;
		double inSin = 0;
		int totalDeuda = 0;

		for(VOMovingViolations s: aux){

			if(s.getViolationCode().equals(codigo)){
				if(s.getAccidentIndicator()){inCon++;}
				else{inSin++;}
				totalDeuda +=s.getFineAmount();
				total++;
			}else{

				if(total>0){
					double por1  = inSin/total;
					double por2 = inCon/total;
					VOColeccion nuevo = new VOColeccion(codigo,total, por1, por2, totalDeuda);
					aux2.agregar(nuevo);
				}
				if(s.getAccidentIndicator()){inCon = 1; inSin = 0;}
				else{inSin = 1;inCon = 0;}
				total = 1;
				totalDeuda = s.getFineAmount();
				codigo = s.getViolationCode();
			}
		}

		if(total>0){
			double por1  = inSin/total;
			double por2 = inCon/total;
			VOColeccion nuevo = new VOColeccion(codigo,total, por1, por2, totalDeuda);
			aux2.agregar(nuevo);
		}

		return aux2;
		// TODO completar
		return null;	
	}

	private BlancoRojoBST<Coordenadas, VOColeccion>  cargarInfraccionesCoordenadas(){

		BlancoRojoBST<Coordenadas, VOColeccion> aux2 = new BlancoRojoBST<>(); 
		IArregloDinamico<VOMovingViolations> aux = movingVOLista;
		Sort.ordenarShellSort(aux, new VOMovingViolations.XYCoordOrder());

		Coordenadas actual = new Coordenadas(0, 0);
		double inCon = 0;
		int total = 0;
		double inSin = 0;
		int totalDeuda = 0;

		for(VOMovingViolations s: aux){

			if(s.darCoordenadas().equals(actual)){
				if(s.getAccidentIndicator()){inCon++;}
				else{inSin++;}
				totalDeuda +=s.getFineAmount();
				total++;
			}else{

				if(total>0){
					double por1  = inSin/total;
					double por2 = inCon/total;
					VOColeccion nuevo = new VOColeccion(actual,total, por1, por2, totalDeuda);
					aux2.put(actual, nuevo);
				}
				if(s.getAccidentIndicator()){inCon = 1; inSin = 0;}
				else{inSin = 1;inCon = 0;}
				total = 1;
				totalDeuda = s.getFineAmount();
				actual= s.darCoordenadas();
			}
		}

		if(total>0){
			double por1  = inSin/total;
			double por2 = inCon/total;
			VOColeccion nuevo = new VOColeccion(actual,total, por1, por2, totalDeuda);
			aux2.put(actual, nuevo);
		}

		return aux2;
	}

	private BlancoRojoBST<Integer, VOColeccion>  cargarInfraccionesValorAcumulado(){

		BlancoRojoBST<Integer, VOColeccion> aux2 = new BlancoRojoBST<>(); 
		IArregloDinamico<VOMovingViolations> aux = movingVOLista;
		Sort.ordenarShellSort(aux, new VOMovingViolations.TimeOrder());

		double inCon = 0;
		int total = 0;
		double inSin = 0;
		int totalDeuda = 0;
		int horaMax = 1;

		for(VOMovingViolations s: aux){

			
			if(s.getTicketIssueDate().getHour()< horaMax){
				if(s.getAccidentIndicator()){inCon++;}
				else{inSin++;}
				totalDeuda +=s.getFineAmount();
				total++;
			}else{
				if(total>0){
					double por1  = inSin/total;
					double por2 = inCon/total;
					VOColeccion nuevo = new VOColeccion(horaMax,total, por1, por2, totalDeuda);
					aux2.put(totalDeuda, nuevo);
				}
				if(s.getAccidentIndicator()){inCon = 1; inSin = 0;}
				else{inSin = 1;inCon = 0;}
				total = 1;
				totalDeuda = s.getFineAmount();
				horaMax++;
			}
		}

		if(total>0){
			double por1  = inSin/total;
			double por2 = inCon/total;
			VOColeccion nuevo = new VOColeccion(horaMax,total, por1, por2, totalDeuda);
			aux2.put(totalDeuda, nuevo);
		}

		return aux2;
	}

	//

	//
	//
	//	/**
	//	 * Convertir fecha a un objeto LocalDate
	//	 * @param fecha fecha en formato dd/mm/aaaa con dd para dia, mm para mes y aaaa para agno
	//	 * @return objeto LD con fecha
	//	 */
	//	private static LocalDate convertirFecha(String fecha)
	//	{
	//		return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	//	}
	//
	//
	//	/**
	//	 * Convertir fecha y hora a un objeto LocalDateTime
	//	 * @param fecha fecha en formato yyyy-MM-dd'T'HH:mm:ss'.000Z' con dd para dia, mm para mes y yyy para agno, HH para hora, mm para minutos y ss para segundos
	//	 * @return objeto LDT con fecha y hora integrados
	//	 */
	//	private static LocalDateTime convertirFecha_Hora_LDT(String fechaHora)
	//	{
	//		return LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss"));
	//
	//	}

}

