package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;

import com.opencsv.CSVReader;

import model.data_structures.*;
import model.util.Sort;
import model.vo.Coordenadas;
import model.vo.EstadisticaInfracciones;
import model.vo.EstadisticasCargaInfracciones;
import model.vo.InfraccionesFecha;
import model.vo.InfraccionesFechaHora;
import model.vo.InfraccionesFranjaHoraria;
import model.vo.InfraccionesFranjaHorariaViolationCode;
import model.vo.InfraccionesLocalizacion;
import model.vo.InfraccionesViolationCode;
import model.vo.VOColeccion;
import model.vo.VOMovingViolations;

public class MovingViolationsManager {

	//TODO Definir atributos necesarios

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
	private static double xMin;
	/**
	 * Y minimo de infraccion
	 */
	private static double yMin;
	/**
	 * X maximo de infraccion
	 */
	private static double xMax;
	/**
	 * Y maximo de infraccion
	 */
	private static double yMax;

	/*
	 * Creados en Parte A
	 */
	/**
	 * 1A
	 */
	private IColaPrioridad<InfraccionesFranjaHoraria> cpFranjasHorarias;

	/**
	 * 2A
	 */
	private ITablaHash<Coordenadas, InfraccionesLocalizacion> thLocalizaciones; // Puede que se cambie a TablaSimOrd, pues Coordenadas tiene un orden que mencionan en el enunciado

	/**
	 * 3A
	 */
	private ITablaSimOrd<LocalDateTime, InfraccionesFecha> abFechas;



	/**
	 * 1B
	 */
	private IColaPrioridad<InfraccionesViolationCode> cpViolationCode;





	/**
	 * Metodo constructor
	 */
	public MovingViolationsManager()
	{

	}



	/**
	 * Carga los datos del semestre dado
	 * @param n Numero del semestre del anio (1 ï¿½ 2)
	 * @return Cola con el numero de datos cargados por mes del semestre
	 */
	public EstadisticasCargaInfracciones loadMovingViolations(int n)
	{
		EstadisticasCargaInfracciones numeroDeCargas;
		if(n == 1)
		{
			numeroDeCargas = loadMovingViolations(new String[] {"Moving_Violations_Issued_in_January_2018.csv", 
					"Moving_Violations_Issued_in_February_2018.csv",
					"Moving_Violations_Issued_in_March_2018.csv",
					"Moving_Violations_Issued_in_April_2018.csv",
					"Moving_Violations_Issued_in_May_2018.csv",
					"Moving_Violations_Issued_in_June_2018.csv"
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
	private EstadisticasCargaInfracciones loadMovingViolations(String[] movingViolationsFilePaths){
		CSVReader reader = null;

		int totalInf = 0;
		int contadorInf; // Cuenta numero de infracciones en cada archivo
		int nMeses = movingViolationsFilePaths.length;
		int[] infPorMes = new int[nMeses];

		try {
			movingVOLista = new ArregloDinamico<VOMovingViolations>(670000);

			int nArchivoActual = 0;
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
				totalInf += contadorInf;
				infPorMes[nArchivoActual++] = contadorInf;
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
		return new EstadisticasCargaInfracciones(totalInf, nMeses, infPorMes, new double[] {xMin, yMin, xMax, yMax});
	}


	/*
	 * Parte A 
	 */
	/**
	 * Requerimiento 1A: Obtener el ranking de las N franjas horarias
	 * que tengan mï¿½s infracciones. 
	 * @param int N: Nï¿½mero de franjas horarias que tienen mï¿½s infracciones
	 * @return Cola con objetos InfraccionesFranjaHoraria
	 */
	public IQueue<InfraccionesFranjaHoraria> rankingNFranjas(int M)
	{
		IQueue<InfraccionesFranjaHoraria> mPrimeras = new Queue<InfraccionesFranjaHoraria>();

		// Crear estructura con la informacion de las 24 franjas horarias
		if (cpFranjasHorarias == null) {
			cpFranjasHorarias = new MaxHeapCP<InfraccionesFranjaHoraria>();

			// Se deben las estadisticas completas para cada franja horaria antes de crear la cola de prioridad
			// pues se necesita saber la prioridad final de cada elemento a agregar
			Sort.ordenarShellSort(movingVOLista, new VOMovingViolations.TimeOrder());

			Iterator<VOMovingViolations> iterador = movingVOLista.iterator();

			// Si no hay datos, entonces retorna una cola vacia
			if (!iterador.hasNext()) return mPrimeras;

			// Como los datos estan ordenados, tomamos una infraccion de referencia para comparar con
			// los datos inmediatamente siguientes
			VOMovingViolations infrRevisar = iterador.next();
			int horaRef = infrRevisar.getTicketIssueDate().getHour() % 24;

			// Datos a actualizar y finalmente agregar
			InfraccionesFranjaHoraria voFranja = new InfraccionesFranjaHoraria(LocalTime.of(horaRef, 0, 0), 
					LocalTime.of(horaRef, 59, 59));
			voFranja.agregarEstadistica(infrRevisar);

			while (iterador.hasNext()) {
				infrRevisar = iterador.next();

				if (horaRef == infrRevisar.getTicketIssueDate().getHour() % 24) {
					// Actualiza datos actuales
					voFranja.agregarEstadistica(infrRevisar);
				} else {
					// Agrega el dato que se estuvo actualizando a la cola
					cpFranjasHorarias.agregar(voFranja);
					// Reestablecer referencias
					horaRef = infrRevisar.getTicketIssueDate().getHour() % 24;
					// Reestablecer datos a actualizar
					voFranja = new InfraccionesFranjaHoraria(LocalTime.of(horaRef, 0, 0), 
							LocalTime.of(horaRef, 59, 59));
					voFranja.agregarEstadistica(infrRevisar);
				}
			}
			// Agregar la ultima referencia
			cpFranjasHorarias.agregar(voFranja);
		}

		int i = 0;
		for (InfraccionesFranjaHoraria estadistica : cpFranjasHorarias) {
			if (++i > M) break;
			mPrimeras.enqueue(estadistica);
		}

		return mPrimeras;		
	}

	/**
	 * Requerimiento 2A: Consultar  las  infracciones  por
	 * Localizaciï¿½n  Geogrï¿½fica  (Xcoord, Ycoord) en Tabla Hash.
	 * @param  double xCoord : Coordenada X de la localizacion de la infracciï¿½n
	 *			double yCoord : Coordenada Y de la localizacion de la infracciï¿½n
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorLocalizacionHash(double xCoord, double yCoord)
	{
		if(thLocalizaciones == null) {
			thLocalizaciones = new LinProbTH<Coordenadas, InfraccionesLocalizacion>(4);

			Coordenadas curCoord;
			InfraccionesLocalizacion locActual;

			for (VOMovingViolations infraccion : movingVOLista) {
				curCoord = new Coordenadas(infraccion.getXCoord(), infraccion.getYCoord());
				locActual = thLocalizaciones.get(curCoord);

				if (locActual == null) locActual = new InfraccionesLocalizacion(infraccion.getXCoord(), infraccion.getYCoord(), infraccion.getLocation(), infraccion.getAddressID(), infraccion.getStreetsegID());

				locActual.agregarEstadistica(infraccion);
				System.out.println("Loc Actual: " + locActual);
				thLocalizaciones.put(curCoord, locActual);
			}
		}

		return thLocalizaciones.get(new Coordenadas(xCoord, yCoord));		
	}

	/**
	 * Requerimiento 3A: Buscar las infracciones por rango de fechas
	 * @param  LocalDate fechaInicial: Fecha inicial del rango de bï¿½squeda
	 * 		LocalDate fechaFinal: Fecha final del rango de bï¿½squeda
	 * @return Cola con objetos InfraccionesFecha
	 */
	public IQueue<InfraccionesFecha> consultarInfraccionesPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 1B: Obtener  el  ranking  de  las  N  tipos  de  infracciï¿½n
	 * (ViolationCode)  que  tengan  mï¿½s infracciones.
	 * @param  int N: Numero de los tipos de ViolationCode con mï¿½s infracciones.
	 * @return Cola con objetos InfraccionesViolationCode con top N infracciones
	 */
	public IQueue<InfraccionesViolationCode> rankingNViolationCodes(int N)
	{

		IQueue<InfraccionesViolationCode> resultado = new Queue<InfraccionesViolationCode>();

		//Verifica si los datos ya fueron cargados anteriormente
		if (cpViolationCode == null) {
			cpViolationCode = new MaxHeapCP<InfraccionesViolationCode>();
			
			//Se ordenan por ViolationCode Order para poder crear las estadísticas
			Sort.ordenarShellSort(movingVOLista, new VOMovingViolations.ViolationCodeOrder());
			Iterator<VOMovingViolations> iterador = movingVOLista.iterator();

			// Si no hay datos, entonces retorna una cola vacia
			if (!iterador.hasNext()) return resultado;

		
			//Se recorren las infracciones tomando la referencia de su violationCode
			VOMovingViolations infrRevisar = iterador.next();
			String violationCodeActual = infrRevisar.getViolationCode();
			
			InfraccionesViolationCode voViolation = new InfraccionesViolationCode(violationCodeActual);
			voViolation.agregarEstadistica(infrRevisar);
			
			while(iterador.hasNext()){
				infrRevisar = iterador.next();
				
				//Si tienen el mismo VOCode, van en la misma estadística
				if(violationCodeActual.equals(infrRevisar.getViolationCode())){
					voViolation.agregarEstadistica(infrRevisar);
				}
				else{
					
					//Si no, se agrega al MAXHEAP y se reincia
					cpViolationCode.agregar(voViolation);
					violationCodeActual = infrRevisar.getViolationCode();
					voViolation = new InfraccionesViolationCode(violationCodeActual);
					voViolation.agregarEstadistica(infrRevisar);
				}
			}
			
			//Para agregar la última referencia
			cpViolationCode.agregar(voViolation);
		}	
		
		
		//Selecciona las N primeras infraccionesViolationCode
		int i = 0;
		for (InfraccionesViolationCode act:cpViolationCode) {
			if(++i>N) break;
			resultado.enqueue(act);
		}

		
		//Devuelve el resultado
		return resultado;

	}


	/**
	 * Requerimiento 2B: Consultar las  infracciones  por  
	 * Localizaciï¿½n  Geogrï¿½fica  (Xcoord, Ycoord) en Arbol.
	 * @param  double xCoord : Coordenada X de la localizacion de la infracciï¿½n
	 *			double yCoord : Coordenada Y de la localizacion de la infracciï¿½n
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorLocalizacionArbol(double xCoord, double yCoord)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 3B: Buscar las franjas de fecha-hora donde se tiene un valor acumulado
	 * de infracciones en un rango dado [US$ valor inicial, US$ valor final]. 
	 * @param  double valorInicial: Valor mï¿½nimo acumulado de las infracciones
	 * 		double valorFinal: Valor mï¿½ximo acumulado de las infracciones.
	 * @return Cola con objetos InfraccionesFechaHora
	 */
	public IQueue<InfraccionesFechaHora> consultarFranjasAcumuladoEnRango(double valorInicial, double valorFinal)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 1C: Obtener  la informaciï¿½n de  una  addressId dada
	 * @param  int addressID: Localizaciï¿½n de la consulta.
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorAddressId(int addressID)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 2C: Obtener  las infracciones  en  un  rango de
	 * horas  [HH:MM:SS  inicial,HH:MM:SS  final]
	 * @param  LocalTime horaInicial: Hora  inicial del rango de bï¿½squeda
	 * 		LocalTime horaFinal: Hora final del rango de bï¿½squeda
	 * @return Objeto InfraccionesFranjaHorariaViolationCode
	 */
	public InfraccionesFranjaHorariaViolationCode consultarPorRangoHoras(LocalTime horaInicial, LocalTime horaFinal)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 3C: Obtener  el  ranking  de  las  N localizaciones geogrï¿½ficas
	 * (Xcoord,  Ycoord)  con  la mayor  cantidad  de  infracciones.
	 * @param  int N: Numero de las localizaciones con mayor nï¿½mero de infracciones
	 * @return Cola de objetos InfraccionesLocalizacion
	 */
	public IQueue<InfraccionesLocalizacion> rankingNLocalizaciones(int N)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 4C: Obtener la  informaciï¿½n  de  los cï¿½digos (ViolationCode) ordenados por su numero de infracciones.
	 * @return Contenedora de objetos InfraccionesViolationCode.
	  // TODO Definir la estructura Contenedora
	 */
	public ITablaSimOrd<Integer, InfraccionesViolationCode> ordenarCodigosPorNumeroInfracciones()
	{
		// TODO completar
		// TODO Definir la Estructura Contenedora
		return null;		
	}


	/*
	 * Metodos ayudantes 
	 */
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
}
