package model.vo;

import model.data_structures.Queue;

/**
 * Objeto que guarda la informacion relevante sobre las infracciones cometidas en un dia particular:
 * fecha del día, número de	accidentes,	número de infracciones y suma total de FINEAMT de las infracciones ese día.
 */
public class VODaylyStatistic {

	/**
	 * Guarda el d�a
	 */
	private String dia;

	/**
	 * Atributo del n�mero de accidentes cometidos en el d�a
	 */
	private int numeroAccidentes;


	/**
	 * Atributo del n�mero de infracciones cometidos en el d�a
	 */
	private int numeroInfracciones;


	/**
	 * Atributo del total fine Amount en el d�a
	 */
	private int totalFineAmt;


	/**
	 * Constructor
	 */
	public VODaylyStatistic(Queue<VOMovingViolations> cola, String fecha){

		dia = fecha;			
		numeroInfracciones = cola.size();

		int contador = 0;
		int suma = 0;
		for (VOMovingViolations actual:cola) {
			if(actual.getAccidentIndicator()){
				contador ++;
			}

			suma = suma + actual.getFineAmount();

		}

		numeroAccidentes = contador;
		totalFineAmt = suma;

	}

	/**
	 * @return El d�a de las estad�sticas
	 */
	public String darDia(){
		return dia;
	}

	/**
	 * @return el n�mero de accidentes
	 */
	public int darNumeroAccidentes(){
		return numeroAccidentes;
	}

	/**
	 * @return El n�mero de infracciones
	 */
	public int numeroInfracciones(){
		return numeroInfracciones;
	}

	/**
	 * @return El total monto de las multas
	 */
	public int totalFineAmount(){
		return totalFineAmt;
	}

}
