package com.matricula.xd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Rol;
import com.matricula.xd.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    
    @Query("select u from Usuario u where u.role.nombre='ROLE_USER'")
    List<Usuario> findAllUsuarios();
    
    @Query("select r from Rol r where r.id=?1")
    Rol getRolById(Long id);
    
    @Query("select r from Rol r")
    List<Rol> findAllRoles();
}