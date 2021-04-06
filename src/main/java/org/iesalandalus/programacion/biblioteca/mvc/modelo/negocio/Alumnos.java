package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;

public class Alumnos {
	// Variables
	private int capacidad;
	private int tamano;
	private Alumno[] coleccionAlumnos;

	// Constructor con parámetros
	public Alumnos(int capacidad) {
		if (capacidad < 1)
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");

		this.tamano = 0;
		this.capacidad = capacidad;
		coleccionAlumnos = new Alumno[capacidad];
	}

	// Método para obtener todos los alumnos.
	public Alumno[] get() {
		return copiaProfundaAlumnos();
	}

	// Método para realizar copia profunda con referencias distintas de los alumnos
	private Alumno[] copiaProfundaAlumnos() {
		Alumno[] alumnosCopia = new Alumno[capacidad];

		int i = 0;
		while (!tamanoSuperado(i)) {
			alumnosCopia[i] = new Alumno(coleccionAlumnos[i]);
			i++;
		}

		return alumnosCopia;
	}

	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null)
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");

		if (capacidadSuperada(capacidad))
			throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
		
		if (buscar(alumno) != null)
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese correo.");

		
		coleccionAlumnos[tamano] = new Alumno(alumno);
		tamano++;		
		
	}

	private int buscarIndice(Alumno alumno) {
		int indice = 0;
		// Recorro el array coleccionAlumnos para compararlo con los alumnos y obtener su indice
		for (Alumno alumnoDeColeccion : coleccionAlumnos) {
			if (alumno.equals(alumnoDeColeccion)) {
				return indice;
			}

			indice++;
		}



        return indice;
	}

	private boolean tamanoSuperado(int indice) {
		return (tamano <= indice);
	}

	private boolean capacidadSuperada(int capacidad) {
		return (tamano >= capacidad);
	}

	public Alumno buscar(Alumno alumno) {
		if (alumno == null)
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");

		if (!tamanoSuperado(buscarIndice(alumno))) {
			return new Alumno(coleccionAlumnos[buscarIndice(alumno)]);
		} else {
			return null;
		}

	}

	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null)
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");

		if (tamanoSuperado(buscarIndice(alumno)))
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese correo.");

		desplazarUnaPosicionHaciaIzquierda(buscarIndice(alumno));
	}

	public void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionAlumnos[i] = coleccionAlumnos[i + 1];
			if (tamanoSuperado(i + 1))
				coleccionAlumnos[i] = null;
		}

		tamano--;
	}

	// ----------------Inicio de getters y setters------------------------------
	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}
	// ---------------Fin de getters y setters--------------------------------

}
