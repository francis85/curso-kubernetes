package com.francis.springcloud.msvc.cursos.services;

import com.francis.springcloud.msvc.cursos.models.Usuario;
import com.francis.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
  public List<Curso> findAll();
  public Optional<Curso> findById(Long id);
  public Optional<Curso> findByIdWithUsers(Long id);
  public Curso save(Curso curso);
  public void deleteById(Long id);

  void deleteCourseUserById(Long id);


  Optional<Usuario> assignUser(Usuario usuario, Long cursoId);
  Optional<Usuario> createUser(Usuario usuario, Long cursoId);
  Optional<Usuario> deleteUser(Usuario usuario, Long cursoId);
}
