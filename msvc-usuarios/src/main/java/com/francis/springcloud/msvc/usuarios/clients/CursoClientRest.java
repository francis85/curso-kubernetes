package com.francis.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002")
public interface CursoClientRest {

  @DeleteMapping("/delete-course-user/{id}")
  void deleteCourseUserById(@PathVariable Long id);
}
