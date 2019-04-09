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

import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.ITablaSimOrd;
import model.data_structures.MaxHeapCP;
import model.data_structures.IArregloDinamico;
import model.data_structures.Queue;
import model.data_structures.Stack;
import model.data_structures.ArregloDinamico;
import model.data_structures.BlancoRojoBST;
import model.vo.VOMovingViolations;
import model.vo.Coordenadas;
import model.vo.VOColeccion;
import model.vo.VODaylyStatistic;
import model.vo.VOViolationCode;

import view.MovingViolationsManagerView;

public class Controller {
	/*
	 * Atributos
	 */
	/**
	 * Objeto de la Vista
	 */
	private MovingViolationsManagerView view;

	/**
	 * Lista donde se van a cargar los datos de los archivos
	 */
	private static IArregloDinamico<VOMovingViolations> movingVOLista;
	/**
	 * Numero actual del cuatrimestre cargado
	 */
	private static int semestreCargado = -1;

	/**
	 * X minimo de infraccion
	 */
	private static float xMin;
	/**
	 * Y minimo de infraccion
	 */
	private static float yMin;
	/**
	 * X maximo de infraccion
	 */
	private static float xMax;
	/**
	 * Y maximo de infraccion
	 */
	private static float yMax;

	/*
	 * Constructor
	 */
	public Controller() {
		view = new MovingViolationsManagerView();
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
					IArregloDinamico<Integer> resultados0 = loadMovingViolations(numeroSemestre);
					view.printMovingViolationsLoadInfo(resultados0, xMin,yMin,xMax,yMax,numeroSemestre );
					break;
				case 1:
					view.printMessage("1A. Consultar las N franjas horarias con mas infracciones que desea ver. Ingresar valor de N: ");
					int numeroFranjas = sc.nextInt();

					//TODO Completar para la invocación del metodo 1A
					//model.rankingNFranjas(int N)
					
					//TODO Mostrar resultado de tipo Cola con N InfraccionesFranjaHoraria
					//view.printReq1A( ...);
					break;

				case 2:
					view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 1234,56): ");
					double xcoord = sc.nextDouble();
					view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 5678,23): ");
					double ycoord = sc.nextDouble();

					//TODO Completar para la invocación del metodo 2A
					//model.consultarPorLocalizacionHash(double xCoord, double yCoord)

					//TODO Mostrar resultado de tipo InfraccionesLocalizacion 
					//view.printReq2A( ... )
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
					view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 1234,56): ");
					xcoord = sc.nextDouble();
					view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 5678,23): ");
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

	/**
	 * Carga los datos del semestre dado
	 * @param n Numero del semestre del anio (1 � 2)
	 * @return Cola con el numero de datos cargados por mes del semestre
	 */
	public IArregloDinamico<Integer> loadMovingViolations(int n)
	{
		IArregloDinamico<Integer> numeroDeCargas = new ArregloDinamico<>();
		if(n == 1)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_January_2018.csv", 
					//					"Moving_Violations_Issued_in_February_2018.csv",
					//					"Moving_Violations_Issued_in_March_2018.csv",
					//					"Moving_Violations_Issued_in_April_2018.csv",
					//					"Moving_Violations_Issued_in_May_2018.csv",
					//					"Moving_Violations_Issued_in_June_2018.csv"
			});
			semestreCargado = 1;
		}
		else if(n == 2)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_July_2018.csv",
					"Moving_Violations_Issued_in_August_2018.csv",
					"Moving_Violations_Issued_in_September_2018.csv", 
					"Moving_Violations_Issued_in_October_2018.csv",
					"Moving_Violations_Issued_in_November_2018.csv",
					"Moving_Violations_Issued_in_December_2018.csv"
			});
			semestreCargado = 2;
		}
		else
		{
			throw new IllegalArgumentException("No existe ese semestre en un annio.");
		}
		return numeroDeCargas;
	}

	/**
	 * Metodo ayudante
	 * Carga la informacion sobre infracciones de los archivos a una pila y una cola ordenadas por fecha.
	 * Dado un arreglo con los nombres de los archivos a cargar
	 * @returns Cola con el numero de datos cargados por mes del cuatrimestre
	 */
	private IArregloDinamico<Integer> loadMovingViolations(String[] movingViolationsFilePaths){
		CSVReader reader = null;
		IArregloDinamico<Integer> numeroDeCargas = new ArregloDinamico<>();

		int contadorInf; // Cuenta numero de infracciones en cada archivo
		try {
			movingVOLista = new ArregloDinamico<VOMovingViolations>(670000);

			for (String filePath : movingViolationsFilePaths) {
				reader = new CSVReader(new FileReader("data/"+filePath));

				contadorInf = 0;
				// Deduce las posiciones de las columnas que se reconocen de acuerdo al header
				String[] headers = reader.readNext();
				int[] posiciones = new int[VOMovingViolations.EXPECTEDHEADERS.length];
				for (int i = 0; i < VOMovingViolations.EXPECTEDHEADERS.length; i++) {
					posiciones[i] = buscarArray(headers, VOMovingViolations.EXPECTEDHEADERS[i]);
				}

				// Lee linea a linea el archivo para crear las infracciones y cargarlas a la lista
				VOMovingViolations infraccion;
				for (String[] row : reader) {
					infraccion = new VOMovingViolations(posiciones, row);
					movingVOLista.agregar(infraccion);
					contadorInf += 1;
					if(xMin<=0 || yMin<=0){
						xMin= infraccion.getXCoord();
						yMin = infraccion.getYCoord();
					}

					// Se actualizan las coordenadas extremas
					xMin = Math.min(xMin, infraccion.getXCoord());
					xMax = Math.max(xMax, infraccion.getXCoord());
					yMin = Math.min(yMin, infraccion.getYCoord());
					yMax = Math.max(yMax, infraccion.getYCoord());			
				}
				// Se agrega el numero de infracciones cargadas en este archivo al resultado 
				numeroDeCargas.agregar(contadorInf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return numeroDeCargas;
	}

	/*
	 * ************************************************************************************
	 * Metodos para los requerimientos del proyecto 2
	 * ************************************************************************************
	 */
	
	/*
	 * Parte A
	 */
	
	
	
	/*
	 * Parte B
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



	/*
	 * Parte C
	 */



	//
	/**
	 * Metodo para buscar strings en un array de strings, usado para deducir la posicion
	 * de las columnas esperadas en cada archivo.
	 * @param array
	 * @param string
	 * @return
	 */
	private int buscarArray(String[] array, String string) {
		int i = 0;

		while (i < array.length) {
			if (array[i].equalsIgnoreCase(string)) return i;
			i += 1;
		}
		return -1;
	}
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

