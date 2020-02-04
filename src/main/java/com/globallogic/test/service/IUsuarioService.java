package com.globallogic.test.service;


import com.globallogic.test.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
