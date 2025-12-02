package com.example.demo.usuario;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Usuario u = repository.findById(id).orElse(null);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        return ResponseEntity.ok(
                repository.findAll()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteusuario(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                repository.save(u)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@Valid @RequestBody Usuario u, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                repository.findById(id).map(usuario -> {
                    usuario.setEmail(u.getEmail());
                    usuario.setPassword(u.getPassword());
                    return repository.save(usuario);
                }).orElse(null)
        );
    }
}
