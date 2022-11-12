package com.francis.springcloud.msvc.usuarios.controllers;

import com.francis.springcloud.msvc.usuarios.models.entity.Usuario;
import com.francis.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

  @Autowired
  private UsuarioService service;

  @GetMapping
  public List<Usuario> findAll(){
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Usuario findById(@PathVariable Long id){
    Optional<Usuario> usuarioOpt = service.findById(id);
    return usuarioOpt.orElseThrow();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario, BindingResult result){
    if (result.hasErrors()){
      return validate(result);
    }
    if (service.findByEmail(usuario.getEmail()).isPresent()){
      return ResponseEntity.badRequest().body(Collections
              .singletonMap("email", "Ya existe un usuario con el email: "+ usuario.getEmail()));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
    if (result.hasErrors()){
      return validate(result);
    }

    Optional<Usuario> usuarioOpt = service.findById(id);
    if (usuarioOpt.isPresent()){
      Usuario usuarioDb = usuarioOpt.get();
      usuarioDb.setNombre(usuario.getNombre());
      usuarioDb.setEmail(usuario.getEmail());
      usuarioDb.setPassword(usuario.getPassword());
      return ResponseEntity.status(HttpStatus.OK).body(service.save(usuarioDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id){
    Optional<Usuario> usuarioOpt = service.findById(id);
    if (usuarioOpt.isPresent()){
      service.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/students-by-course")
  public ResponseEntity<?> getStudentsPerCourse(@RequestParam List<Long> ids){
    return ResponseEntity.ok(service.findAllByIds(ids));
  }

  private ResponseEntity<Map<String, String>> validate(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errors.put(err.getField(), err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
