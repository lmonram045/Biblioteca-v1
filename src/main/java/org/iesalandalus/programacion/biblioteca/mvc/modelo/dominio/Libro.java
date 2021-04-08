package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public class Libro {
	// Constantes de la clase
	private static final int PAGINAS_PARA_RECOMPENSA = 25;
	private static final float PUNTOS_PREMIO = 0.5f;
	// Variables de la clase
	private String titulo;
	private String autor;
	private int numPaginas;

	/** Constructor con parámetros */
	public Libro(String titulo, String autor, int numPaginas) {
		setTitulo(titulo);
		setAutor(autor);
		setNumPaginas(numPaginas);
	}

	/** Constructor copia */
	public Libro(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		setTitulo(libro.getTitulo());
		setAutor(libro.getAutor());
		setNumPaginas(libro.getNumPaginas());
	}

	/** Método para devolver un libro ficticio */
	public static Libro getLibroFicticio(String titulo, String autor) {
		return new Libro(titulo, autor, 1527);
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	private void setTitulo(String titulo) {
		// Comprueba que no sea nulo el nombre
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		// Compruebo si al eliminar los espacios, el nombre está vacío
		if (titulo.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	private void setAutor(String autor) {
		// Comprobamos que el autor no sea nulo
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		// Comprobamos que el nombre del autor no está vacío.
		if (autor.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		this.autor = autor;
	}
	
	public int getNumPaginas() {
		return numPaginas;
	}
	
	private void setNumPaginas(int numPaginas) {
		if (numPaginas < 1) {
			throw new IllegalArgumentException("ERROR: El número de páginas debe ser mayor que cero.");
		}
		this.numPaginas = numPaginas;
	}

	/** Método para obtener los puntos obtenidos por libro */
	public float getPuntos() {
		return ((numPaginas / PAGINAS_PARA_RECOMPENSA) + 1) * PUNTOS_PREMIO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Libro other = (Libro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "título=" + titulo + ", autor=" + autor + ", número de páginas=" + numPaginas;
	}

}
