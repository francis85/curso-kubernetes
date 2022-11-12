package com.francis.springcloud.msvc.cursos.clients;

import com.francis.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

  @GetMapping("/{id}")
  Usuario find(@PathVariable Long id);

  @PostMapping("/")
  Usuario create(@RequestBody Usuario usuario);

  @GetMapping("/students-by-course")
  List<Usuario> getStudentsByCourse(@RequestParam Iterable<Long> ids);

}
