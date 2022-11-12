package com.francis.springcloud.msvc.cursos.services;

import com.francis.springcloud.msvc.cursos.clients.UsuarioClientRest;
import com.francis.springcloud.msvc.cursos.models.Usuario;
import com.francis.springcloud.msvc.cursos.models.entity.Curso;
import com.francis.springcloud.msvc.cursos.models.entity.CursoUsuario;
import com.francis.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{
  @Autowired
  private CursoRepository repository;
  @Autowired
  private UsuarioClientRest clientUserRest;

  @Override
  @Transactional(readOnly = true)
  public List<Curso> findAll() {
    return (List<Curso>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> findByIdWithUsers(Long id) {
    Optional<Curso> curso = repository.findById(id);
    if (curso.isPresent()){
      if(!curso.get().getCursoUsuarios().isEmpty()){
        List<Long> idsUsers = curso.get().getCursoUsuarios().stream().map(cu -> cu.getUsuarioId()).collect(Collectors.toList());
        List<Usuario> users = clientUserRest.getStudentsByCourse(idsUsers);
        curso.get().setUsuarios(users);
      }
      return Optional.of(curso.get());
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public Curso save(Curso curso) {
    return repository.save(curso);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteCourseUserById(Long id) {
    repository.deleteCourseUserById(id);
  }

  @Override
  public Optional<Usuario> assignUser(Usuario usuario, Long cursoId) {
    Optional<Curso> curso = repository.findById(cursoId);
    if (curso.isPresent()){
      Usuario userMsvc= clientUserRest.find(usuario.getId());
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(userMsvc.getId());
      curso.get().addCursoUsuario(cursoUsuario);
      repository.save(curso.get());
      return Optional.of(userMsvc);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Usuario> createUser(Usuario usuario, Long cursoId) {
    Optional<Curso> curso = repository.findById(cursoId);
    if (curso.isPresent()){
      Usuario newUserMsvc= clientUserRest.create(usuario);
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(newUserMsvc.getId());
      curso.get().addCursoUsuario(cursoUsuario);
      repository.save(curso.get());
      return Optional.of(newUserMsvc);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Usuario> deleteUser(Usuario usuario, Long cursoId) {
    Optional<Curso> curso = repository.findById(cursoId);
    if (curso.isPresent()){
      Usuario userMsvc= clientUserRest.find(usuario.getId());
      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(userMsvc.getId());
      curso.get().removeCursoUsuario(cursoUsuario);
      repository.save(curso.get());
      return Optional.of(userMsvc);
    }
    return Optional.empty();
  }
}
