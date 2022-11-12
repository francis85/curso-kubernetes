package com.francis.springcloud.msvc.cursos.controllers;

import com.francis.springcloud.msvc.cursos.models.Usuario;
import com.francis.springcloud.msvc.cursos.models.entity.Curso;
import com.francis.springcloud.msvc.cursos.services.CursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

  @Autowired
  private CursoService service;

  @GetMapping
  public List<Curso> findAll(){
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Curso findById(@PathVariable Long id){
    Optional<Curso> cursoOpt = service.findByIdWithUsers(id);
    return cursoOpt.orElseThrow();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> save(@Valid @RequestBody Curso curso, BindingResult result){
    if (result.hasErrors()){
      return validate(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@Valid @RequestBody Curso usuario, BindingResult result, @PathVariable Long id){
    if (result.hasErrors()){
      return validate(result);
    }
    Optional<Curso> cursoOpt = service.findById(id);
    if (cursoOpt.isPresent()){
      Curso cursoDb = cursoOpt.get();
      cursoDb.setNombre(usuario.getNombre());
      return ResponseEntity.status(HttpStatus.OK).body(service.save(cursoDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    Optional<Curso> usuarioOpt = service.findById(id);
    if (usuarioOpt.isPresent()){
      service.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/assing-user/{cursoId}")
  public ResponseEntity<?> assignUser(@RequestBody Usuario usuario, @PathVariable Long cursoId){
    Optional<Usuario> u;
    try{
      u = service.assignUser(usuario, cursoId);
    }catch (FeignException e){
      return ResponseEntity.status(e.status()).body(e.getMessage());
    }
    if (u.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(u.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/create-user/{cursoId}")
  public ResponseEntity<?> createUser(@RequestBody Usuario usuario, @PathVariable Long cursoId){
    Optional<Usuario> u;
    try{
      u = service.createUser(usuario, cursoId);
    }catch (FeignException e){
      return ResponseEntity.status(e.status()).body(e.getMessage());
    }
    if (u.isPresent()){
      return ResponseEntity.status(HttpStatus.CREATED).body(u.get());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/delete-user/{cursoId}")
  public ResponseEntity<?> deleteUser(@RequestBody Usuario usuario, @PathVariable Long cursoId){
    Optional<Usuario> u;
    try{
      u = service.deleteUser(usuario, cursoId);
    }catch (FeignException e){
      return ResponseEntity.status(e.status()).body(e.getMessage());
    }
    if (u.isPresent()){
      return ResponseEntity.status(HttpStatus.OK).body(u.get());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/delete-course-user/{id}")
  public ResponseEntity<?> deleteCourseUserById(@PathVariable Long id){
    service.deleteCourseUserById(id);
    return ResponseEntity.noContent().build();
  }

  private ResponseEntity<Map<String, String>> validate(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errors.put(err.getField(), err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
