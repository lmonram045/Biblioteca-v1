package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;

public class Libros {

	private List<Libro> coleccionLibros;

	/** Constructor por defecto de la clase Libros */
	public Libros() {
		coleccionLibros = new ArrayList<>();
	}

	/** Método para devolver una copia profunda de Libros */
	public List<Libro> get() {
		List<Libro> copiaLibros = copiaProfundaLibros();
		copiaLibros.sort(Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor));
		
		return copiaLibros;
	}

	/** Método para realizar una copia profunda de Libros */
	private List<Libro> copiaProfundaLibros() {
		List<Libro> copiaLibros = new ArrayList<>();

		for (Libro libro : coleccionLibros)
			copiaLibros.add(new Libro(libro));

		return copiaLibros;
	}

	/** Devuelve el tamaño de los libros ya insertados */
	public int getTamano() {
		return coleccionLibros.size();
	}

	/** Método para insertar libros nuevos */
	public void insertar(Libro libro) throws OperationNotSupportedException {
		if (libro == null)
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");

		if (coleccionLibros.contains(libro))
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese título y autor.");

		coleccionLibros.add(new Libro(libro));
	}

	/** Método para buscar un libro */
	public Libro buscar(Libro libro) {
		if (libro == null)
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");

		int indice = coleccionLibros.indexOf(libro);

		return (indice == -1) ? null : new Libro(coleccionLibros.get(indice));
	}

	/** Método para borrar un libro. */
	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null)
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");

		if (!coleccionLibros.contains(libro))
			throw new OperationNotSupportedException("ERROR: No existe ningún libro con ese título y autor.");

		coleccionLibros.remove(libro);
	}
}
