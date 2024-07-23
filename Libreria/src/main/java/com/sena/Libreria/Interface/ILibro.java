package com.sena.Libreria.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.sena.Libreria.Models.Libro;


@Repository
public interface  ILibro extends CrudRepository <Libro,String> {
	@Query("SELECT l FROM Libro l WHERE "
			+ "l.titulo_libro LIKE %?1% OR "
			+ "l.autor_libro LIKE %?1% OR "
			+ "l.isbn_libro LIKE %?1% OR "
			+ "l.genero_libro LIKE %?1% OR "
			+ "l.numero_ejemplares_disponibles LIKE %?1% OR "
			+ "l.numero_ejemplares_ocupados LIKE %?1%")
	List<Libro>filtroLibro (String filtro);
	
	@Query("SELECT l FROM Libro l WHERE l.isbn_libro = ?1")
	List<Libro> filtroIngresoLibro(String isbn_libro);
}
