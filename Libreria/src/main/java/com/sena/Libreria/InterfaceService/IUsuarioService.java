package com.sena.Libreria.InterfaceService;

import java.util.List;
import java.util.Optional;

import com.sena.Libreria.Models.Usuario;



public interface  IUsuarioService {
	
	public String save(Usuario usuario);
	
	public List <Usuario> findAll();
	
	public List<Usuario> filtroUsuario(String filtro);
	
	public Optional<Usuario>findOne (String id);
	
	public int delete (String id);
	
	public List<Usuario>filtroIngresoUsuario(String numero_documento_usuario);
	
}
