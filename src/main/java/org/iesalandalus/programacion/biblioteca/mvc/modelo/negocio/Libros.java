package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;

public class Libros {
	// Variables
	private int capacidad;
	private int tamano;
	private Libro[] coleccionLibros;

	/** Constructor clase Libros */
	public Libros(int capacidad) {
		// Primero comprobamos que la capacidad sea mayor que 0
		if (capacidad < 1)
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		// Inicializamos tamaño a 0 (porque aún no hay libros insertados), y creamos un
		// array de libros de el tamaño de la capacidad pasada por parámetro.
		tamano = 0;
		this.capacidad = capacidad;
		coleccionLibros = new Libro[capacidad];
	}

	/** Método para devolver una copia profunda de Libros */
	public Libro[] get() {
		return copiaProfundaLibros();
	}

	/** Método para realizar una copia profunda de Libros */
	private Libro[] copiaProfundaLibros() {
		Libro[] librosCopia = new Libro[capacidad];

		int i = 0;
		while (!tamanoSuperado(i)) {
			librosCopia[i] = new Libro(coleccionLibros[i]);
			i++;
		}

		return librosCopia;
	}

	/** Devuelve el máximo de libros que puede contener */
	public int getCapacidad() {
		return capacidad;
	}

	/** Devuelve el tamaño de los libros ya insertados */
	public int getTamano() {
		return tamano;
	}

	/** Método para insertar libros nuevos */
	public void insertar(Libro libro) throws OperationNotSupportedException {
		if (libro == null)
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");

		if (capacidadSuperada(capacidad))
			throw new OperationNotSupportedException("ERROR: No se aceptan más libros.");

		if (buscar(libro) != null)
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese título y autor.");

		coleccionLibros[tamano] = new Libro(libro);
		tamano++;

	}

	/** Método para buscar el índice de un libro */
	private int buscarIndice(Libro libro) {
		int indice = 0;
		// For each para recorrer libros y compararlos para obtener su indice.
		for (Libro libroDeColeccion : coleccionLibros) {
			if (libro.equals(libroDeColeccion)) {
				return indice;
			}

			indice++;
		}

		return indice;
	}

	/**
	 * Método para comprobar si el tamaño (la cantidad de libros ya insertados), se
	 * ha superado
	 */
	private boolean tamanoSuperado(int indice) {
		return (tamano <= indice);
	}

	/**
	 * Método para comprobar si la capacidad (la cantidad máxima de libros que se
	 * pueden insertar), se ha superado.
	 */
	private boolean capacidadSuperada(int capacidad) {
		return (tamano >= capacidad);
	}

	/** Método para buscar un libro */
	public Libro buscar(Libro libro) {
		if (libro == null)
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");

		if (!tamanoSuperado(buscarIndice(libro))) {
			return new Libro(coleccionLibros[buscarIndice(libro)]);
		} else {
			return null;
		}
	}

	/** Método para borrar un libro. */
	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null)
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");

		if (tamanoSuperado(buscarIndice(libro)))
			throw new OperationNotSupportedException("ERROR: No existe ningún libro con ese título y autor.");

		desplazarUnaPosicionHaciaIzquierda(buscarIndice(libro));
	}

	/**
	 * Método para desplazar una posicion de el array a la izquierda a partir de un
	 * indice, para compactar el array al realizar borrados.
	 */
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionLibros[i] = coleccionLibros[i + 1];
			if (tamanoSuperado(i + 1))
				coleccionLibros[i] = null;
		}

		tamano--;
	}

}
