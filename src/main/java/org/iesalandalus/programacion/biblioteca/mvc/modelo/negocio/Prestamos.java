package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public class Prestamos {
	// Variables
	private int capacidad;
	private int tamano;
	private Prestamo[] coleccionPrestamos;

	/** Constructor de clase Prestamos */
	public Prestamos(int capacidad) {
		if (capacidad < 1)
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");

		tamano = 0;
		this.capacidad = capacidad;
		coleccionPrestamos = new Prestamo[capacidad];
	}

	/** Método para obtener una copia profunda de los préstamos */
	public Prestamo[] get() {
		return copiaProfundaPrestamos();
	}

	/** Método para generar una copia profunda de los préstamos */
	private Prestamo[] copiaProfundaPrestamos() {
		Prestamo[] prestamosCopia = new Prestamo[capacidad];

		int i = 0;
		while (!tamanoSuperado(i)) {
			prestamosCopia[i] = new Prestamo(coleccionPrestamos[i]);
			i++;
		}

		return prestamosCopia;
	}

	/**
	 * Método para obtener una copia de todos los préstamos que ha realizado un
	 * alumno.
	 */
	public Prestamo[] get(Alumno alumno) {
		if (alumno == null)
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		
		// Array para guardar salida auxiliar de prestamos
		Prestamo[] auxiliarPrestamo = new Prestamo[capacidad];
		// Variable para controlar el array auxiliar creado anteriormente
		int i = 0;
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo != null && prestamo.getAlumno().equals(alumno)) {
				auxiliarPrestamo[i] = prestamo;
				i++;
			}
		}
		return auxiliarPrestamo;
	}

	/**
	 * Método para obtener una copia de todos los préstamos que se han realizado de
	 * un libro en concreto.
	 */
	public Prestamo[] get(Libro libro) {
		if (libro == null)
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		
		// Array para guardar salida auxiliar de prestamos
		Prestamo[] auxiliarPrestamo = new Prestamo[capacidad];
		// Variable para controlar el array auxiliar creado anteriormente
		int i = 0;
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo != null && prestamo.getLibro().equals(libro)) {
				auxiliarPrestamo[i] = prestamo;
				i++;
			}
		}
		return auxiliarPrestamo;
	}

	/**
	 * Método para obtener una copia de todos los préstamos realizados en una fecha
	 * concreta
	 */
	public Prestamo[] get(LocalDate fecha) {
		if (fecha == null)
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		
		// Array para guardar salida auxiliar de prestamos
		Prestamo[] auxiliarPrestamo = new Prestamo[capacidad];
		// Variable para controlar el array auxiliar creado anteriormente
		int i = 0;
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo != null && prestamo.getFechaPrestamo().equals(fecha)) {
				auxiliarPrestamo[i] = prestamo;
				i++;
			}
		}
		return auxiliarPrestamo;
	}

	/**
	 * Método para comprobar si dos fechas pasadas por parámetro, pertenecen al
	 * mismo mes y al mismo año
	 */
	private boolean mismoMes(LocalDate fecha1, LocalDate fecha2) {

		return fecha1.getMonth().equals(fecha2.getMonth()) && (fecha1.getYear() == fecha2.getYear());

	}

	/** Método para obtener la capacidad */
	public int getCapacidad() {
		return capacidad;
	}

	/** Método para obtener el tamaño */
	public int getTamano() {
		return tamano;
	}

	/**
	 * Método para generar un nuevo préstamo
	 * 
	 * @throws OperationNotSupportedException
	 */
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null)
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");

		// Utilizo capacidadSuperada para usar todas los métodos de la clase, pero con
		// igualar tamano y capacidad en este if seria suficiente (comprobado).
		if (capacidadSuperada(tamano))
			throw new OperationNotSupportedException("ERROR: No se aceptan más préstamos.");

		if (!tamanoSuperado(buscarIndice(prestamo)))
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");

		coleccionPrestamos[tamano] = new Prestamo(prestamo);
		tamano++;

	}

	/** Método para buscar el índice de un prestamo */
	private int buscarIndice(Prestamo prestamo) {
		int indice = 0;
		// For each para recorrer libros y compararlos para obtener su indice.
		for (Prestamo prestamoDeColeccion : coleccionPrestamos) {
			if (prestamo.equals(prestamoDeColeccion)) {
				return indice;
			}

			indice++;
		}

		return indice;
	}

	/**
	 * Método para comprobar si se supero el tamaño (elementos ya insertados) de el
	 * array
	 */
	private boolean tamanoSuperado(int indice) {
		return (tamano <= indice);
	}

	/**
	 * Método para comprobar si se supero la capacidad total de el array (ya no se
	 * podrían insertar mas elementos)
	 */
	private boolean capacidadSuperada(int tamano) {
		return (tamano >= capacidad);
	}

	/**
	 * Método para realizar una devolución de un prestamo en una fecha concreta
	 * pasados ambos por parámetro
	 * 
	 * @throws OperationNotSupportedException
	 */
	public void devolver(Prestamo prestamo, LocalDate fecha) throws OperationNotSupportedException {
		
		if (prestamo == null)
			throw new NullPointerException("ERROR: No se puede devolver un préstamo nulo.");

		int indice = buscarIndice(prestamo);
		
		if (tamanoSuperado(indice))
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");

		prestamo.devolver(fecha);
		coleccionPrestamos[indice] = prestamo;
	}

	/** Método para buscar un préstamo */
	public Prestamo buscar(Prestamo prestamo) {
		if (prestamo == null)
			throw new IllegalArgumentException("ERROR: No se puede buscar un préstamo nulo.");

		if (!tamanoSuperado(buscarIndice(prestamo)))
			return new Prestamo(coleccionPrestamos[buscarIndice(prestamo)]);

		return null;
	}

	/**
	 * Método para borrar un préstamo
	 * 
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
				
		if (prestamo == null)
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");

		int indice = buscarIndice(prestamo);
		
		if (tamanoSuperado(indice))
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		
		desplazarUnaPosicionHaciaIzquierda(indice);
	}

	/** Método para compactar el array despues de un borrado */
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionPrestamos[i] = coleccionPrestamos[i + 1];
			if (tamanoSuperado(i + 1))
				coleccionPrestamos[i] = null;
		}

		tamano--;
	}
}
