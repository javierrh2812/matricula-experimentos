package com.matricula.xd.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Table(name="usuarios")
public @Data class Usuario{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Length(min=4, max = 15, message = "El nombre de usuario debe tener entre 4 y 15 caracteres. Solo letras y números permitidos")
    @NotEmpty(message = "El campo no puede estar vacío")
    private String username;
    
    @NotEmpty(message = "El campo no puede estar vacío")
    private String password;
    
    @Length(max=20, message ="Error. máximo 20 caracteres")
    @NotEmpty(message = "El campo no puede estar vacío")
    private String nombre;
    private String apellido;
    
    @NotEmpty(message = "El campo no puede estar vacío")
    @Email(message = "Correo inválido")
    private String correo;
    
    @ManyToOne    
    private Rol role;
}