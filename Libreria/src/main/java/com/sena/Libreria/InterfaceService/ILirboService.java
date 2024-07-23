package com.sena.Libreria.InterfaceService;

import java.util.List;
import java.util.Optional;

import com.sena.Libreria.Models.Libro;



public interface  ILirboService {
	public String save(Libro libro);
	public List <Libro> findAll();
	public List<Libro> filtroLibro(String filtro);
	public Optional<Libro>findOne (String id);
	public int delete (String id);
	public List<Libro>filtroIngresoLibro(String titulo_libro);
	
}
