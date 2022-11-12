package com.francis.springcloud.msvc.usuarios.services;

import com.francis.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

  public List<Usuario> findAll();
  public Optional<Usuario> findById(Long id);
  public Optional<Usuario> findByEmail(String email);
  public Usuario save(Usuario usuario);
  public void deleteById(Long id);
  List<Usuario> findAllByIds(Iterable<Long> ids);

}
