package org.iesalandalus.programacion.biblioteca.mvc.vista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	/** Constructor por defecto */
	private Consola() {
	}

	/** Método para mostrar menú */
	public static void mostrarMenu() {
		for (Opcion opcion : Opcion.values()) {
			System.out.println("\n" + opcion);
		}
	}

	/** Método para mostrar cabecera */
	public static void mostrarCabecera(String cabecera) {
		System.out.printf("%n%s%n", cabecera);
		String formatoCadena = "%0" + cabecera.length() + "d%n";
		System.out.print(String.format(formatoCadena, 0).replace("0", "-"));
	}

	/** Método para elegir una opción */
	public static int elegirOpcion() {
		int ordinal;
		do {
			System.out.print("\nElija una opción: ");
			ordinal = Entrada.entero();

		} while (!Opcion.esOrdinalValido(ordinal));
		return ordinal;
	}
	
	/** Método para introducir un alumno nuevo por teclado */
	public static Alumno leerAlumno() {
		int numeroCurso;
		
		System.out.printf("\nIntroduzca el nombre de el alumno: ");
		String nombre = Entrada.cadena();
		
		System.out.print("Introduzca el correo de el alumno: ");
		String correo = Entrada.cadena();
		
		do {
			System.out.print("Introduzca el curso de el alumno (1 a 4); ");
			numeroCurso = Entrada.entero();
		} while (numeroCurso < 1 || numeroCurso > 4);
		
		Curso curso = Curso.values()[numeroCurso - 1];
		
		return new Alumno(nombre, correo, curso);
		
	}

	public static Alumno leerAlumnoFicticio() {
		System.out.print("\nIntroduzca el correo de el alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}
	
	/** Método para introducir un nuevo libro por teclado */
	public static Libro leerLibro() {
		System.out.print("\nIntroduzca el título de el libro: ");
		String titulo = Entrada.cadena();
		
		System.out.print("Introduzca el autor de el libro: ");
		String autor = Entrada.cadena();
		
		System.out.print("Introduzca el número de páginas de el libro: ");
		int numeroPaginas = Entrada.entero();
		
		return new Libro(titulo, autor, numeroPaginas);
	}

	public static Libro leerLibroFicticio() {
		System.out.print("\nIntroduce el título de el libro: ");
		String titulo = Entrada.cadena();
		
		System.out.print("Introduzca el autor de el libro: ");
		String autor = Entrada.cadena();
		
		return Libro.getLibroFicticio(titulo, autor);
	}
	
	/** Método para introducir un nuevo préstamo por teclado */
	public static Prestamo leerPrestamo() {
		return new Prestamo(leerAlumno(), leerLibro(), leerFecha("Introduzca la fecha de el préstamo: "));
	}

	public static Prestamo leerPrestamoFicticio() {
		return Prestamo.getPrestamoFicticio(leerAlumnoFicticio(), leerLibroFicticio());
	}

	public static LocalDate leerFecha(String mensaje) {
		System.out.printf("%s (dd/MM/yyyy): ", mensaje);
		String fechaStr = Entrada.cadena();
		LocalDate fecha = null;
		try {
			fecha = LocalDate.parse(fechaStr, Prestamo.FORMATO_FECHA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("ERROR: La fecha introducida no tiene el formato adecuado.");
		}
		return fecha;
	}
}
