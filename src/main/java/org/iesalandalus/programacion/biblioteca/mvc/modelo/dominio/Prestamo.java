package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Prestamo {
	// Constantes
	private static final int MAX_DIAS_PRESTAMO = 20;
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	// Variables
	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucion;
	private Alumno alumno;
	private Libro libro;

	/** Constructor con parámetros */
	public Prestamo(Alumno alumno, Libro libro, LocalDate fechaPrestamo) {
		setAlumno(alumno);
		setLibro(libro);
		setFechaPrestamo(fechaPrestamo);
	}

	/** Constructor copia */
	public Prestamo(Prestamo prestamo) {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No es posible copiar un préstamo nulo.");
		}

		this.alumno = prestamo.getAlumno();
		this.libro = prestamo.getLibro();
		this.fechaPrestamo = prestamo.getFechaPrestamo();

		if (prestamo.getFechaDevolucion() != null) {
			this.fechaDevolucion = prestamo.getFechaDevolucion();
		}
	}

	/** Método para obtener un prestamo ficticio */
	public static Prestamo getPrestamoFicticio(Alumno alumno, Libro libro) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}

		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		return new Prestamo(alumno, libro, LocalDate.now());
	}

	/** Método para devolver un libro */
	public void devolver(LocalDate fecha) {

		setFechaDevolucion(fecha);
	}

	/** Método para obtener los puntos de el préstamo */
	public int getPuntos() {
		if (getFechaDevolucion() == null) {
			return 0;
		}
		// Si los dias pasados entre la fecha de prestamo y de devolución supera 20,
		// obtenemos 0 puntos.
		if (ChronoUnit.DAYS.between(getFechaPrestamo(), getFechaDevolucion()) > MAX_DIAS_PRESTAMO) {
			return 0;
		}
		// Si no se cumple ninguna de las condiciones anteriores, devuelve los puntos
		// obtenidos por la lectura de ese libro
		// redondeando el resultado de los puntos que vale el libro, entre los dias
		// pasados
		return Math.round(libro.getPuntos() / ChronoUnit.DAYS.between(getFechaPrestamo(), getFechaDevolucion()));
	}
	
	public Alumno getAlumno() {
		return new Alumno(alumno);
	}
	
	private void setAlumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		this.alumno = alumno;
	}
	
	public Libro getLibro() {
		return new Libro(libro);
	}
	
	private void setLibro(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		this.libro = libro;
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	
	private void setFechaPrestamo(LocalDate fechaPrestamo) {
		if (fechaPrestamo == null) {
			throw new NullPointerException("ERROR: La fecha de préstamo no puede ser nula.");
		}
		if (fechaPrestamo.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de préstamo no puede ser futura.");
		}
	
		this.fechaPrestamo = fechaPrestamo;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if (fechaDevolucion.isBefore(getFechaPrestamo()) || fechaDevolucion.isEqual(getFechaPrestamo())) {
			throw new IllegalArgumentException(
					"ERROR: La fecha de devolución debe ser posterior a la fecha de préstamo.");
		}
		if (this.fechaDevolucion != null) {
			throw new IllegalArgumentException("ERROR: La devolución ya estaba registrada.");
		}

		
		this.fechaDevolucion = fechaDevolucion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + ((libro == null) ? 0 : libro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		if (alumno == null) {
			if (other.alumno != null)
				return false;
		} else if (!alumno.equals(other.alumno))
			return false;
		if (libro == null) {
			if (other.libro != null)
				return false;
		} else if (!libro.equals(other.libro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (fechaDevolucion == null) {
			return "alumno=(" + alumno + "), libro=(" + libro + "), fecha de préstamo="
					+ fechaPrestamo.format(FORMATO_FECHA) + ", puntos=" + getPuntos();
		}
		return "alumno=(" + alumno + "), libro=(" + libro + "), fecha de préstamo="
				+ fechaPrestamo.format(FORMATO_FECHA) + ", fecha de devolución=" + fechaDevolucion.format(FORMATO_FECHA)
				+ ", puntos=" + getPuntos();
	}

}
