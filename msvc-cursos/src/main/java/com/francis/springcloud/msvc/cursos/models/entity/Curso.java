package com.francis.springcloud.msvc.cursos.models.entity;

import com.francis.springcloud.msvc.cursos.models.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String nombre;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "curso_id")
  private List<CursoUsuario> cursoUsuarios;

  @Transient
  private List<Usuario> usuarios;

  public Long getId() {
    return id;
  }

  public Curso() {
    cursoUsuarios = new ArrayList<>();
    usuarios = new ArrayList<>();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<CursoUsuario> getCursoUsuarios() {
    return cursoUsuarios;
  }

  public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
    this.cursoUsuarios = cursoUsuarios;
  }

  public void addCursoUsuario(CursoUsuario cursoUsuario){
    cursoUsuarios.add(cursoUsuario);
  }

  public void removeCursoUsuario(CursoUsuario cursoUsuario){
    cursoUsuarios.remove(cursoUsuario);
  }

  public List<Usuario> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }
}
