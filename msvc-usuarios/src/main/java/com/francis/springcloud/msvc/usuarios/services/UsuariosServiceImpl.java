package com.francis.springcloud.msvc.usuarios.services;

import com.francis.springcloud.msvc.usuarios.clients.CursoClientRest;
import com.francis.springcloud.msvc.usuarios.models.entity.Usuario;
import com.francis.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuarioService{

  @Autowired
  private UsuarioRepository repository;
  @Autowired
  private CursoClientRest clientCourseRest;

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> findAll() {
    return (List<Usuario>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Usuario> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Usuario> findByEmail(String email){
    return repository.findByEmail(email);
  }

  @Override
  @Transactional
  public Usuario save(Usuario usuario) {
    return repository.save(usuario);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    repository.deleteById(id);
    clientCourseRest.deleteCourseUserById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> findAllByIds(Iterable<Long> ids) {
    return (List<Usuario>) repository.findAllById(ids);
  }
}
