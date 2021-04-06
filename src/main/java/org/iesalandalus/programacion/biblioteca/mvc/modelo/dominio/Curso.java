package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public enum Curso {
	// Enumerado Curso, con los valores para el constructor
	PRIMERO("1º ESO"), SEGUNDO("2º ESO"), TERCERO("3º ESO"), CUARTO("4º ESO");
	
	// Variable para almacenar cadena
	private final String cadenaAMostrar;
	
	// Constructor con parámetros para 
	private Curso(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	// Método toString para mostrar cadena con nombre completo del curso.
	@Override
	public String toString() {
		return cadenaAMostrar;
	}
}
