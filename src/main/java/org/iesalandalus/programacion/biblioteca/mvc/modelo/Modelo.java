package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.Libros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.Prestamos;

public class Modelo {

	private Alumnos alumnos;
	private Prestamos prestamos;
	private Libros libros;

	/** Constructor por defecto */
	public Modelo() {
		alumnos = new Alumnos();
		prestamos = new Prestamos();
		libros = new Libros();
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
		// Para cada préstamo de este alumno, borrarlo.
		List<Prestamo> prestamosAlumno = prestamos.get(alumno);
		for (Prestamo prestamo : prestamosAlumno)
			prestamos.borrar(prestamo);
		
		// Una vez borrados los préstamos de ese alumno, borrar el alumno.
		alumnos.borrar(alumno);
	}
	
	/** Método para borrar un libro */
	public void borrar(Libro libro) throws OperationNotSupportedException {
		// Para cada préstamo de este libro, borrarlo.
		List<Prestamo> prestamosLibro = prestamos.get(libro);
		for (Prestamo prestamo : prestamosLibro)
			prestamos.borrar(prestamo);
		
		// Una vez borrados los préstamos, borrar libro
		libros.borrar(libro);
	}
	
	/** Método para borrar un préstamo */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		prestamos.borrar(prestamo);
	}
	
	/** Método para obtener una copia profunda de los alumnos */
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}
	
	/** Método para obtener una copia profunda de los libros */
	public List<Libro> getLibros() {
		return libros.get();
	}
	
	/** Método para obtener una copia profunda de los préstamos */
	public List<Prestamo> getPrestamos() {
		return prestamos.get();
	}
	
	/** Método para obtener una copia profunda de los préstamos de un alumno */
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return prestamos.get(alumno);
	}
	
	/** Método para obtener una copia profunda de los préstamos de un libro */
	public List<Prestamo> getPrestamos(Libro libro) {
		return prestamos.get(libro);
	}
	
	/** Método para obtener una copia profunda de los préstamos realizados en una fecha concreta */
	public List<Prestamo> getPrestamos(LocalDate fecha) {
		return prestamos.get(fecha);
	}
	
	/** Método para obtener una copia profunda de los préstamos realizados en un mes ordenados por curso */
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate mes) {
		return prestamos.getEstadisticaMensualPorCurso(mes);
	}
}