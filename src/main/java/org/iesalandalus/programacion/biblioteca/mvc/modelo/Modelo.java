package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.Libros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.Prestamos;

public class Modelo {
	// Declaración de constantes y variables
	private static final int CAPACIDAD = 30;

	private Alumnos alumnos;
	private Prestamos prestamos;
	private Libros libros;

	/** Constructor por defecto */
	public Modelo() {
		alumnos = new Alumnos(CAPACIDAD);
		prestamos = new Prestamos(CAPACIDAD);
		libros = new Libros(CAPACIDAD);
	}

	/** Método para insertar un alumno */
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.insertar(alumno);
	}

	/** Método para insertar un libro */
	public void insertar(Libro libro) throws OperationNotSupportedException {
		libros.insertar(libro);
	}

	/** Método para realizar un préstamo */
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null)
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");

		if (alumnos.buscar(prestamo.getAlumno()) == null)
			throw new OperationNotSupportedException("ERROR: No existe el alumno del préstamo.");

		if (libros.buscar(prestamo.getLibro()) == null)
			throw new OperationNotSupportedException("ERROR: No existe el libro del préstamo.");

		prestamos.prestar(prestamo);
	}
	
	/** Método para realizar una devolución */
	public void devolver(Prestamo prestamo, LocalDate fecha) throws OperationNotSupportedException {
		if (prestamos.buscar(prestamo) == null)
			throw new OperationNotSupportedException("ERROR: No se puede devolver un préstamo no prestado.");

		prestamos.devolver(prestamo, fecha);
	}
	
	/** Método para buscar un alumno */
	public Alumno buscar(Alumno alumno) {
		return alumnos.buscar(alumno);
	}
	
	/** Método para buscar un libro */
	public Libro buscar(Libro libro) {
		return libros.buscar(libro);
	}
	
	/** Método para buscar un préstamo */
	public Prestamo buscar(Prestamo prestamo) {
		return prestamos.buscar(prestamo);
	}
	
	/** Método para borrar un alumno */
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.borrar(alumno);
	}
	
	/** Método para borrar un libro */
	public void borrar(Libro libro) throws OperationNotSupportedException {
		libros.borrar(libro);
	}
	
	/** Método para borrar un préstamo */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		prestamos.borrar(prestamo);
	}
	
	/** Método para obtener una copia profunda de los alumnos */
	public Alumno[] getAlumnos() {
		return alumnos.get();
	}
	
	/** Método para obtener una copia profunda de los libros */
	public Libro[] getLibros() {
		return libros.get();
	}
	
	/** Método para obtener una copia profunda de los préstamos */
	public Prestamo[] getPrestamos() {
		return prestamos.get();
	}
	
	/** Método para obtener una copia profunda de los préstamos de un alumno */
	public Prestamo[] getPrestamos(Alumno alumno) {
		return prestamos.get(alumno);
	}
	
	/** Método para obtener una copia profunda de los préstamos de un libro */
	public Prestamo[] getPrestamos(Libro libro) {
		return prestamos.get(libro);
	}
	
	/** Método para obtener una copia profunda de los préstamos realizados en una fecha concreta */
	public Prestamo[] getPrestamos(LocalDate fecha) {
		return prestamos.get(fecha);
	}
}