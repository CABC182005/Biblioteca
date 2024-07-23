package com.sena.Libreria.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.Libreria.Interface.ILibro;
import com.sena.Libreria.InterfaceService.ILirboService;
import com.sena.Libreria.Models.Libro;


@Service
public class LibroService implements ILirboService {
	@Autowired 
	private ILibro data;
	
	@Override
	public String save(Libro libro) {
		data.save(libro);
		return libro.getId_libro();
	}

	@Override
	public List<Libro> findAll() {
		List <Libro> listaLibro = (List<Libro>) data.findAll() ;
		
		return listaLibro;
	}

	@Override
	public List<Libro> filtroLibro(String filtro) {
		List <Libro> listaLibro=data.filtroLibro(filtro);
		return listaLibro;
	}
	
	
	@Override
	public Optional<Libro> findOne(String id_libro) {
		Optional<Libro>libro=data.findById(id_libro);
		
		return libro;
	}

	@Override
	public int delete(String id_libro) {
		data.deleteById(id_libro);
		return 1;
	}
	@Override

	public List<Libro> filtroIngresoLibro(String titulo_libro) {
		List<Libro> listaLibro=data.filtroIngresoLibro(titulo_libro);
		return listaLibro;
	}
}
