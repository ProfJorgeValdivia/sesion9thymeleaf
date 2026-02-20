package com.cibertec.lp2.sesion9_thymel_jr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.lp2.sesion9_thymel_jr.model.Usuario;
import com.cibertec.lp2.sesion9_thymel_jr.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow();
	}
}
