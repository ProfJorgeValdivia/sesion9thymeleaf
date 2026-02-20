package com.cibertec.lp2.sesion9_thymel_jr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.lp2.sesion9_thymel_jr.model.Usuario;
import com.cibertec.lp2.sesion9_thymel_jr.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
    @Autowired
    private UsuarioRepository usuarioRepository;
    
	 //LISTAR USUARIOS
    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/lista";
    }

    // FORMULARIO NUEVO USUARIO
    @GetMapping("/usuarios/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    // GUARDAR USUARIO (CREATE / UPDATE)
    @PostMapping("/usuarios/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    // EDITAR USUARIO
    @GetMapping("/usuarios/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    // ELIMINAR (LÓGICO)
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setActivo(0);
            usuarioRepository.save(usuario);
        });
        return "redirect:/usuarios";
    }
    
    /**
     * | Acción   | URL                                         |
	| -------- | ------------------------------------------- |
	| Listar   | `http://localhost:8080/usuarios`            |
	| Nuevo    | `http://localhost:8080/usuarios/nuevo`      |
	| Editar   | `http://localhost:8080/usuarios/editar/1`   |
	| Eliminar | `http://localhost:8080/usuarios/eliminar/1` |

     */
}
