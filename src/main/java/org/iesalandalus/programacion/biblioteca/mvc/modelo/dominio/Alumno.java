package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alumno {
	// Constantes para almacenar las expresiones regulares de nombre y correo
	private static final String ER_NOMBRE = "[a-zA-ZÁÉÍÓÚáéíóúÑñ]+[\\s]+[a-zA-ZÁÉÍÓÚáéíóúÑñ\\s]*";

	private static final String ER_CORREO = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";

	// Variables para nombre, correo y curso
	private String nombre;
	private String correo;
	private Curso curso;

	/** Constructor con parámetros de la clase Alumno. */
	public Alumno(String nombre, String correo, Curso curso) {
		setNombre(nombre);
		setCorreo(correo);
		setCurso(curso);
	}

	/** Constructor copia de la clase alumno */
	public Alumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}

		setNombre(alumno.getNombre());
		setCorreo(alumno.getCorreo());
		setCurso(alumno.getCurso());
	}

	/** Método para devolver un alumno ficticio con nombre y curso válido y correo
	* pasado por parámetro. */
	public static Alumno getAlumnoFicticio(String correo) {
		return new Alumno("Waldo Geraldo Faldo", correo, Curso.TERCERO);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}

		if (nombre.trim().length() == 0 || !nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}

		this.nombre = formateaNombre(nombre);
	}

	/** Método para formatear nombre */
	private String formateaNombre(String nombre) {
		// primero quito espacios al principio y al final
		nombre = nombre.trim();
		// Buscamos las secuencias de dos o más espacios y las sustituimos por un
		// espacio
		nombre = nombre.replaceAll("\\s{2,}", " ");
		// Divido el nombre en un array para separar por palabras, separado por espacios
		// en blanco
		String[] partesNombre = nombre.split(" ");
		// Vacío el nombre para luego reconstruirlo
		nombre = "";
		// Bucle para recorrer el array partes
		for (String partes : partesNombre) {
			// Por cada parte, pongo en mayúscula la posición 0 y en minúscula las demas, y
			// añado un espacio al final.
			partes = partes.substring(0, 1).toUpperCase() + partes.substring(1).toLowerCase() + " ";
			// Reconstruyo el nombre
			nombre += partes;
		}
		// Devuelvo el nombre borrándole el espacio final que me creó en el bucle
		return nombre.trim();

	}
	
	public String getCorreo() {
		return correo;
	}
	
	private void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}

		Pattern pattern = Pattern.compile(ER_CORREO);

		Matcher matcher = pattern.matcher(correo);

		if (!matcher.matches()) {

			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");

		}

		this.correo = correo;
	}

	/** Método para devolver las iniciales de un nombre */
	private String getIniciales() {
		// Creo variable para guardar iniciales
		String iniciales = "";
		// pongo el nombre en formato correcto
		String nombreFormateado = formateaNombre(nombre);
		// Creo un array para dividir el nombre
		String[] partesNombre = nombreFormateado.split(" ");
		// Recorro el array para que vaya guardando las iniciales de el nombre.
		for (String partes : partesNombre) {
			iniciales += partes.substring(0, 1);
		}
		return iniciales;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		if (curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}

		this.curso = curso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, curso=%s", formateaNombre(nombre), getIniciales(), correo,
				curso);
	}
}
