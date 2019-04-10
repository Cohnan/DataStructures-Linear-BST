package model.logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;

import model.data_structures.IArregloDinamico;
import model.data_structures.IColaPrioridad;
import model.data_structures.IQueue;
import model.data_structures.ITablaHash;
import model.data_structures.ITablaSimOrd;
import model.data_structures.MaxHeapCP;
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
	 * Creados en Parte A
	 */
	/**
	 * 1A
	 */
	private IColaPrioridad<InfraccionesFranjaHoraria> cpFranjasHorarias;
	
	/**
	 * 2A
	 */
	private ITablaHash<Coordenadas, InfraccionesLocalizacion> htLocalizaciones;
	
	/**
	 * 3A
	 */
	private ITablaSimOrd<LocalDateTime, InfraccionesFecha> abFechas;
	
	/**
	 * Metodo constructor
	 */
	public MovingViolationsManager()
	{
		
	}
	
	/*
	 * Parte A
	 */
	
	
	/*
	 * Parte B
	 */
	/**
	 * Cargar las infracciones de un semestre de 2018
	 * @param numeroSemestre numero del semestre a cargar (1 o 2)
	 * @return objeto con el resultado de la carga de las infracciones
	 */
	public EstadisticasCargaInfracciones loadMovingViolations(int numeroSemestre) {
		// TODO Realizar la carga de infracciones del semestre
		
		return null;
	}

	/**
	  * Requerimiento 1A: Obtener el ranking de las N franjas horarias
	  * que tengan m�s infracciones. 
	  * @param int N: N�mero de franjas horarias que tienen m�s infracciones
	  * @return Cola con objetos InfraccionesFranjaHoraria
	  */
	public IQueue<InfraccionesFranjaHoraria> rankingNFranjas(int N)
	{
IColaPrioridad<InfraccionesFranjaHoraria> mPrimeras = new MaxHeapCP<InfraccionesFranjaHoraria>();
		
		// Crear estructura con la informacion de las 24 franjas horarias
		if (cpFranjasHorarias == null) {
			cpFranjasHorarias = new MaxHeapCP<InfraccionesFranjaHoraria>();
			
			// Se deben las estadisticas completas para cada franja horaria antes de crear la cola de prioridad
			// pues se necesita saber la prioridad final de cada elemento a agregar
			Sort.ordenarShellSort(movingVOLista, new VOMovingViolations.TicketIssueOrder());
			
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
			mPrimeras.agregar(estadistica);
		}
		
		return mPrimeras;		
	}
	
	/**
	  * Requerimiento 2A: Consultar  las  infracciones  por
	  * Localizaci�n  Geogr�fica  (Xcoord, Ycoord) en Tabla Hash.
	  * @param  double xCoord : Coordenada X de la localizacion de la infracci�n
	  *			double yCoord : Coordenada Y de la localizacion de la infracci�n
	  * @return Objeto InfraccionesLocalizacion
	  */
	public InfraccionesLocalizacion consultarPorLocalizacionHash(double xCoord, double yCoord)
	{
		// TODO completar
		return null;		
	}
	
	/**
	  * Requerimiento 3A: Buscar las infracciones por rango de fechas
	  * @param  LocalDate fechaInicial: Fecha inicial del rango de b�squeda
	  * 		LocalDate fechaFinal: Fecha final del rango de b�squeda
	  * @return Cola con objetos InfraccionesFecha
	  */
	public IQueue<InfraccionesFecha> consultarInfraccionesPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal)
	{
		// TODO completar
		return null;		
	}
	
	/**
	  * Requerimiento 1B: Obtener  el  ranking  de  las  N  tipos  de  infracci�n
	  * (ViolationCode)  que  tengan  m�s infracciones.
	  * @param  int N: Numero de los tipos de ViolationCode con m�s infracciones.
	  * @return Cola con objetos InfraccionesViolationCode con top N infracciones
	  */
	public IQueue<InfraccionesViolationCode> rankingNViolationCodes(int N)
	{
		// TODO completar
		return null;		
	}

	
	/**
	  * Requerimiento 2B: Consultar las  infracciones  por  
	  * Localizaci�n  Geogr�fica  (Xcoord, Ycoord) en Arbol.
	  * @param  double xCoord : Coordenada X de la localizacion de la infracci�n
	  *			double yCoord : Coordenada Y de la localizacion de la infracci�n
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
	  * @param  double valorInicial: Valor m�nimo acumulado de las infracciones
	  * 		double valorFinal: Valor m�ximo acumulado de las infracciones.
	  * @return Cola con objetos InfraccionesFechaHora
	  */
	public IQueue<InfraccionesFechaHora> consultarFranjasAcumuladoEnRango(double valorInicial, double valorFinal)
	{
		// TODO completar
		return null;		
	}
	
	/**
	  * Requerimiento 1C: Obtener  la informaci�n de  una  addressId dada
	  * @param  int addressID: Localizaci�n de la consulta.
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
	  * @param  LocalTime horaInicial: Hora  inicial del rango de b�squeda
	  * 		LocalTime horaFinal: Hora final del rango de b�squeda
	  * @return Objeto InfraccionesFranjaHorariaViolationCode
	  */
	public InfraccionesFranjaHorariaViolationCode consultarPorRangoHoras(LocalTime horaInicial, LocalTime horaFinal)
	{
		// TODO completar
		return null;		
	}
	
	/**
	  * Requerimiento 3C: Obtener  el  ranking  de  las  N localizaciones geogr�ficas
	  * (Xcoord,  Ycoord)  con  la mayor  cantidad  de  infracciones.
	  * @param  int N: Numero de las localizaciones con mayor n�mero de infracciones
	  * @return Cola de objetos InfraccionesLocalizacion
	  */
	public IQueue<InfraccionesLocalizacion> rankingNLocalizaciones(int N)
	{
		// TODO completar
		return null;		
	}
	
	/**
	  * Requerimiento 4C: Obtener la  informaci�n  de  los c�digos (ViolationCode) ordenados por su numero de infracciones.
	  * @return Contenedora de objetos InfraccionesViolationCode.
	  // TODO Definir la estructura Contenedora
	  */
	public Contenedora<InfraccionesViolationCode> ordenarCodigosPorNumeroInfracciones()
	{
		// TODO completar
		// TODO Definir la Estructura Contenedora
		return null;		
	}


}
