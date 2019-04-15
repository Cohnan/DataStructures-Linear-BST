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
		//Controller controller = new Controller();
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
					view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 393185.81): ");
					double xCoord = Double.parseDouble(sc.next());
					view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 138316.9): ");
					double yCoord = Double.parseDouble(sc.next());

					view.printReq2A(model.consultarPorLocalizacionHash(xCoord, yCoord));
					break;

				case 3:
					view.printMessage("Ingrese la fecha inicial del rango. Formato año-mes-dia (ej. 2008-01-21)");
					String fechaInicialStr = sc.next();
					LocalDate fechaInicial = ManejoFechaHora.convertirFecha_LD( fechaInicialStr );

					view.printMessage("Ingrese la fecha final del rango. Formato año-mes-dia (ej. 2018-02-20)");
					String fechaFinalStr = sc.next();
					LocalDate fechaFinal = ManejoFechaHora.convertirFecha_LD( fechaFinalStr );

					view.printReq3A(model.consultarInfraccionesPorRangoFechas(fechaInicial, fechaFinal));
					break;


				case 4:
					view.printMessage("1B. Consultar los N Tipos con mas infracciones. Ingrese el valor de N: ");
					int numeroTipos = sc.nextInt();
					//TODO Completar para la invocación del metodo 1B				
					view.printReq1B(model.rankingNViolationCodes(numeroTipos));
					//TODO Mostrar resultado de tipo Cola con N InfraccionesViolationCode
					//view.printReq1B( ... )
					break;

				case 5:						
					view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 393185.81): ");
					double xCoord2 = Double.parseDouble(sc.next());
					view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 138316.9): ");
					double yCoord2 = Double.parseDouble(sc.next());
					view.printReq2B(model.consultarPorLocalizacionArbol(xCoord2, yCoord2));

					//TODO Completar para la invocación del metodo 2B
					//model.consultarPorLocalizacionArbol(double xCoord, double yCoord)
					//TODO Mostrar resultado de tipo InfraccionesLocalizacion 
					//view.printReq2B( ... )
					break;

				case 6:
					view.printMessage("Ingrese la cantidad minima de dinero que deben acumular las infracciones en sus rangos de fecha  (Ej. 1234,56)");
					double cantidadMinima = Double.parseDouble(sc.next());

					view.printMessage("Ingrese la cantidad maxima de dinero que deben acumular las infracciones en sus rangos de fecha (Ej. 5678,23)");
					double cantidadMaxima = Double.parseDouble(sc.next());
					view.printReq3B(model.consultarFranjasAcumuladoEnRango(cantidadMinima, cantidadMaxima));
					break;
				case 7:
					view.printMessage("1C. Consultar las infracciones con Address_Id. Ingresar el valor de Address_Id: ");
					int addressID = sc.nextInt();

					startTime = System.currentTimeMillis();
					InfraccionesLocalizacion resultados7 = model.consultarPorAddressId(addressID);
					endTime = System.currentTimeMillis();

					duration = endTime - startTime;

					view.printMessage("Tiempo requerimiento 1C: " + duration + " milisegundos");

					view.printReq1C(resultados7);
					break;

				case 8:
					view.printMessage("Ingrese la hora inicial del rango. Formato HH:MM:SS (ej. 09:30:00)");
					String horaInicialStr = sc.next();
					LocalTime horaInicial = ManejoFechaHora.convertirHora_LT(horaInicialStr);

					view.printMessage("Ingrese la hora final del rango. Formato HH:MM:SS (ej. 16:00:00)");
					String horaFinalStr = sc.next();
					LocalTime horaFinal = ManejoFechaHora.convertirHora_LT(horaFinalStr);

					startTime = System.currentTimeMillis();
					
					InfraccionesFranjaHorariaViolationCode resultados8= model.consultarPorRangoHoras(horaInicial, horaFinal);

					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 2C: " + duration + " milisegundos");
					
					view.printReq2C(resultados8);
					break;

				case 9:
					view.printMessage("Consultar las N localizaciones geograficas con mas infracciones. Ingrese el valor de N: ");
					int numeroLocalizaciones = sc.nextInt();

					startTime = System.currentTimeMillis();
					IQueue<InfraccionesLocalizacion> resultados9 =  model.rankingNLocalizaciones(numeroLocalizaciones);

					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 3C: " + duration + " milisegundos");

					view.printReq3C(resultados9);
					break;

				case 10:

					System.out.println("Grafica ASCII con la informacion de las infracciones por ViolationCode");

					startTime = System.currentTimeMillis();

					view.printReq4C(model.ordenarCodigosPorNumeroInfracciones(), model.darNumeroSemestre(), model.darNumeroInfraccionesCargadas());
					endTime = System.currentTimeMillis();

					duration = endTime - startTime;
					view.printMessage("Tiempo requerimiento 4C: " + duration + " milisegundos");
					break;
				case 11:
					fin=true;
					sc.close();
					break;
				}
			} catch(Exception e) { // Si ocurrio un error al ejecutar algun metodo
				e.printStackTrace(); System.out.println("Ocurrio un error. Se recomienda reiniciar el programa.");}
		}
	}

}

