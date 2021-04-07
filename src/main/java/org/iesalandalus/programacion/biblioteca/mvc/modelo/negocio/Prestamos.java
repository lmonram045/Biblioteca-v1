package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public class Prestamos {

	private List<Prestamo> coleccionPrestamos;

	/** Constructor por defecto de clase Prestamos */
	public Prestamos() {
		coleccionPrestamos = new ArrayList<>();
	}

	/** Método para obtener una copia profunda de los préstamos */
	public List<Prestamo> get() {
		Comparator<Alumno> compararAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> CompararLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> compararPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, compararAlumno).thenComparing(Prestamo::getLibro, CompararLibro);

		List<Prestamo> copiaPrestamos = copiaProfundaPrestamos();
		copiaPrestamos.sort(compararPrestamo);

		return copiaPrestamos;
	}

	/** Método para generar una copia profunda de los préstamos */
	private List<Prestamo> copiaProfundaPrestamos() {
		List<Prestamo> copiaPrestamos = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos)
			copiaPrestamos.add(new Prestamo(prestamo));

		return copiaPrestamos;
	}

	/** Método para obtener el tamaño */
	public int getTamano() {
		return coleccionPrestamos.size();
	}

	/**
	 * Método para obtener una copia de todos los préstamos que ha realizado un
	 * alumno.
	 */
	public List<Prestamo> get(Alumno alumno) {
		if (alumno == null)
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");

		List<Prestamo> auxiliarPrestamos = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getAlumno().equals(alumno))
				auxiliarPrestamos.add(new Prestamo(prestamo));
		}

		Comparator<Libro> CompararLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> compararPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getLibro, CompararLibro);

		auxiliarPrestamos.sort(compararPrestamo);

		return auxiliarPrestamos;
	}

	/**
	 * Método para obtener una copia de todos los préstamos que se han realizado de
	 * un libro en concreto.
	 */
	public List<Prestamo> get(Libro libro) {
		if (libro == null)
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");

		List<Prestamo> auxiliarPrestamos = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getLibro().equals(libro))
				auxiliarPrestamos.add(new Prestamo(prestamo));
		}

		Comparator<Alumno> compararAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Prestamo> compararPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, compararAlumno);

		auxiliarPrestamos.sort(compararPrestamo);

		return auxiliarPrestamos;
	}

	/**
	 * Método para obtener una copia de todos los préstamos realizados en una fecha
	 * concreta
	 */
	public List<Prestamo> get(LocalDate fecha) {
		if (fecha == null)
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");

		List<Prestamo> auxiliarPrestamo = new ArrayList<>();

		for (Prestamo prestamo : coleccionPrestamos) {
			if (mismoMes(prestamo.getFechaPrestamo(), fecha))
				auxiliarPrestamo.add(new Prestamo(prestamo));
		}

		Comparator<Alumno> compararAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> compararLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> compararPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, compararAlumno).thenComparing(Prestamo::getLibro, compararLibro);

		auxiliarPrestamo.sort(compararPrestamo);

		return auxiliarPrestamo;
	}

	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate mes) {
		Map<Curso, Integer> estadisticaMensualCurso = inicializarEstadisticas();

		List<Prestamo> prestamosMes = get(mes);

		Curso curso;

		for (Prestamo prestamo : prestamosMes) {
			curso = prestamo.getAlumno().getCurso();
			estadisticaMensualCurso.put(curso, estadisticaMensualCurso.get(curso) + prestamo.getPuntos());
		}

		return estadisticaMensualCurso;
	}

	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer> estadisticaMensualCurso = new EnumMap<>(Curso.class);

		for (Curso curso : Curso.values())
			estadisticaMensualCurso.put(curso, 0);

		return estadisticaMensualCurso;
	}

	/**
	 * Método para comprobar si dos fechas pasadas por parámetro, pertenecen al
	 * mismo mes y al mismo año
	 */
	private boolean mismoMes(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.getMonth().equals(fecha2.getMonth()) && (fecha1.getYear() == fecha2.getYear());
	}

	/**
	 * Método para generar un nuevo préstamo
	 * 
	 * @throws OperationNotSupportedException
	 */
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null)
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");

		if (coleccionPrestamos.contains(prestamo))
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");

		coleccionPrestamos.add(new Prestamo(prestamo));
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

		int indice = coleccionPrestamos.indexOf(prestamo);

		if (indice == -1)
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");

		coleccionPrestamos.get(indice).devolver(fecha);
	}

	/** Método para buscar un préstamo */
	public Prestamo buscar(Prestamo prestamo) {
		if (prestamo == null)
			throw new IllegalArgumentException("ERROR: No se puede buscar un préstamo nulo.");

		int indice = coleccionPrestamos.indexOf(prestamo);
		prestamo = (indice == -1) ? null : new Prestamo(coleccionPrestamos.get(indice));

		return prestamo;
	}

	/**
	 * Método para borrar un préstamo
	 * 
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {

		if (prestamo == null)
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");

		if (!coleccionPrestamos.contains(prestamo))
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");

		coleccionPrestamos.remove(prestamo);
	}
}
