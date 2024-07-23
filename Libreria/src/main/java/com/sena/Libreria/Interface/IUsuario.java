package com.sena.Libreria.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sena.Libreria.Models.Usuario;



public interface IUsuario {

	@Query("SELECT u FROM Usuario u WHERE "
			+ "u.numero_documento_usuario LIKE %?1% OR "
			+ "u.nombre_usuario LIKE %?1% OR "
			+ "u.direccion_usuario LIKE %?1% OR "
			+ "u.correo_electronico_usuario LIKE %?1% OR "
			+ "u.tipo_Usuario LIKE %?1%")
	List<Usuario>filtroUsuario (String filtro);
	
	@Query("SELECT u FROM usuario u WHERE u.numero_documento_usuario = ?1")
	List<Usuario> filtroIngresoUsuario(String numero_documento_usuario);
}
