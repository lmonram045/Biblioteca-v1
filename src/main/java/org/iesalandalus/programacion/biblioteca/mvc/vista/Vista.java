package org.iesalandalus.programacion.biblioteca.mvc.vista;

import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.Controlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public class Vista {

	private Controlador controlador;

	/** Constructor por defecto */
	public Vista() {
		Opcion.setVista(this);
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void comenzar() {
		int numeroOpcion;
		Opcion opcion;

		Consola.mostrarCabecera("Gestión de préstamos de la biblioteca");

		do {
			Consola.mostrarMenu();
			numeroOpcion = Consola.elegirOpcion();
			opcion = Opcion.getOpcionSegunOrdinal(numeroOpcion);
			opcion.ejecutar();
			System.out.println("\n");
		} while (numeroOpcion != Opcion.SALIR.ordinal());
	}

	public void terminar() {
		controlador.terminar();
	}

	public void insertarAlumno() {
		Consola.mostrarCabecera("Insertar alumno");

		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("El alumno se insertó correctamente.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlumno() {
		Alumno alumno;

		Consola.mostrarCabecera("Buscar un alumno");

		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe este alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlumno() {
		Consola.mostrarCabecera("Borrar un alumno");

		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("El alumno se eliminó correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlumnos() {
		Consola.mostrarCabecera("Lista de alumnos");

		// Alumno[] alumnos = controlador.getAlumnos();
		List<Alumno> alumnos = controlador.getAlumnos();
		
		if (alumnos.isEmpty()) {
			System.out.println("No se encontraron alumnos");
		} else {
			for (Alumno alumno : alumnos) {
				if (alumno != null)
					System.out.println(alumno);
			}
		}
	}

	public void insertarLibro() {
		Consola.mostrarCabecera("Insertar libro");

		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("El libro se insertó correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarLibro() {
		Libro libro;

		Consola.mostrarCabecera("Buscar libro");

		try {
			libro = controlador.buscar(Consola.leerLibroFicticio());
			String mensaje = (libro == null) ? "No existe el libro" : libro.toString();
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarLibro() {
		Consola.mostrarCabecera("Borrar libro");

		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("El libro se borró correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarLibros() {
		Consola.mostrarCabecera("Lista de libros");

		List<Libro> libros = controlador.getLibros();

		if (libros.isEmpty())
			System.out.println("No se encontraron libros");
		else {
			for (Libro libro : libros) {
				if (libro != null)
					System.out.println(libro);
			}
		}
	}

	public void prestarLibro() {
		Consola.mostrarCabecera("Prestar libro");

		try {
			controlador.prestar(Consola.leerPrestamo());
			System.out.println("Préstamo realizado.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverLibro() {
		Consola.mostrarCabecera("Devolución de libro");

		try {
			controlador.devolver(Consola.leerPrestamoFicticio(),
					Consola.leerFecha("Introduzca la fecha de préstamo: "));
			System.out.println("Devolución realizada.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarPrestamo() {
		Consola.mostrarCabecera("Buscar préstamo");

		try {
			Prestamo prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo == null) ? "Préstamo no existente." : prestamo.toString();
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarPrestamo() {
		Consola.mostrarCabecera("Borrar préstamo");

		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Préstamo borrado.");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamos() {
		Consola.mostrarCabecera("Lista de préstamos");

		List<Prestamo> prestamos = controlador.getPrestamos();
		if (prestamos.isEmpty()) {
			System.out.println("No se encontraron préstamos.");
		} else {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null)
					System.out.println(prestamo);
			}
		}

	}

	public void listarPrestamosAlumno() {
		Consola.mostrarCabecera("Lista de préstamos de un alumno");

		try {
			List<Prestamo> prestamosAlumno = controlador.getPrestamos(Consola.leerAlumnoFicticio());
			if (prestamosAlumno.isEmpty()) {
				System.out.println("No se encontraron préstamos para este alumno");
			} else {
				for (Prestamo prestamo : prestamosAlumno) {
					if (prestamo != null)
						System.out.println(prestamo);
				}
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamosLibro() {
		Consola.mostrarCabecera("Lista de préstamos realizados de un libro");

		try {
			List<Prestamo> prestamosLibro = controlador.getPrestamos(Consola.leerLibroFicticio());
			if (prestamosLibro.isEmpty()) {
				System.out.println("No se encontraron préstamos realizados de este libro.");
			} else {
				for (Prestamo prestamo : prestamosLibro) {
					if (prestamo != null)
						System.out.println(prestamo);
				}
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("Lista de préstamos realizados en una fecha");

		try {
			List<Prestamo> prestamosFecha = controlador
					.getPrestamos(Consola.leerFecha("Introduzca la fecha de el préstamo "));
			if (prestamosFecha.isEmpty()) {
				System.out.println("No se encontraron préstamos en esta fecha");
			} else {
				for (Prestamo prestamo : prestamosFecha) {
					if (prestamo != null)
						System.out.println(prestamo);
				}
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarEstadisticaMensualPorCurso() {
		Consola.mostrarCabecera("Estadísticas mensuales por curso");
		
		try {
			Map<Curso, Integer> estadisticasMensuales = controlador.getEstadisticaMensualPorCurso(Consola.leerFecha("Introduzca la fecha sobre la que quiere realizar la consulta "));
			
			if (estadisticasMensuales.isEmpty()) {
				System.out.println("No se encontraron préstamos en esta fecha");
			} else {
				//for (Map<Curso,Integer> auxiliarEstadisticas : estadisticasMensuales) {
				System.out.println(estadisticasMensuales);
				//}
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
