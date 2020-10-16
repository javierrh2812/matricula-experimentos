package com.matricula.xd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matricula.xd.entity.Rol;
import com.matricula.xd.entity.Usuario;
import com.matricula.xd.repository.UsuarioRepository;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userDetailsService")
public class UsuarioService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioDao;
    
    
    @Transactional()
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("buscando a usuario: "+username);
    	Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario==null) throw new UsernameNotFoundException(username);
        else log.info("usuario encontrado!");
        var roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(usuario.getRole().getNombre()));

        return new User(
            usuario.getUsername(), usuario.getPassword(),roles
        );
	}
    
    
    public List<Usuario> listaDeUsuarios(){
    	return usuarioDao.findAll();
    }
    
    public void eliminarUsuarioPorId(Long id) {
    	usuarioDao.deleteById(id);
    	
    }
    public Boolean existeUsuarioPorUsername(String username) {
    	return usuarioDao.findByUsername(username)!=null;
    }
    
    public Usuario encontrarUsuarioPorUsername(String username) {
    	return usuarioDao.findByUsername(username);
    }
    
    public Optional<Usuario> encontrarUsuarioPorId(Long id) {
    	return usuarioDao.findById(id);
    }
    
    public Usuario guardarUsuario(Usuario usuario) {
    	return usuarioDao.save(usuario);
    }
    
    
    public Rol getRolById(Long id) {
    	return usuarioDao.getRolById(id);
    }
    
    public List<Rol> getAllRoles(){
    	return usuarioDao.findAllRoles();
    }
}