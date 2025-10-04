package com.example.cpcloud2.Controller;

import com.example.cpcloud2.Model.Categoria;
import com.example.cpcloud2.Repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    public ModelAndView listarCategorias() {
        ModelAndView mv = new ModelAndView("categoria/lista");
        mv.addObject("categorias", categoriaRepository.findAll());
        return mv;
    }

    @GetMapping("/categoria/nova")
    public ModelAndView novaCategoria() {
        ModelAndView mv = new ModelAndView("categoria/nova");
        mv.addObject("categoria", new Categoria());
        return mv;
    }

    @PostMapping("/categoria/salvar")
    public ModelAndView salvarCategoria(@Valid Categoria categoria, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("categoria/nova");
            mv.addObject("categoria", categoria);
            return mv;
        }

        categoriaRepository.save(categoria);
        return new ModelAndView("redirect:/categorias");
    }

    @GetMapping("/categoria/detalhes/{id}")
    public ModelAndView detalhesCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        
        if (categoriaOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("categoria/detalhes");
            mv.addObject("categoria", categoriaOpt.get());
            return mv;
        } else {
            return new ModelAndView("redirect:/categorias");
        }
    }

    @GetMapping("/categoria/editar/{id}")
    public ModelAndView editarCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        
        if (categoriaOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("categoria/editar");
            mv.addObject("categoria", categoriaOpt.get());
            return mv;
        } else {
            return new ModelAndView("redirect:/categorias");
        }
    }

    @PostMapping("/categoria/atualizar/{id}")
    public ModelAndView atualizarCategoria(@PathVariable Long id, 
                                          @Valid Categoria categoria,
                                          BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("categoria/editar");
            mv.addObject("categoria", categoria);
            return mv;
        }

        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoriaExistente = categoriaOpt.get();
            categoriaExistente.setNome(categoria.getNome());
            categoriaExistente.setDescricao(categoria.getDescricao());
            
            categoriaRepository.save(categoriaExistente);
        }

        return new ModelAndView("redirect:/categorias");
    }

    @GetMapping("/categoria/remover/{id}")
    public ModelAndView removerCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return new ModelAndView("redirect:/categorias");
    }
}
