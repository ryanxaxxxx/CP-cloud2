package com.example.cpcloud2.Controller;

import com.example.cpcloud2.Model.Categoria;
import com.example.cpcloud2.Model.Produto;
import com.example.cpcloud2.Repository.CategoriaRepository;
import com.example.cpcloud2.Repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/")
    public ModelAndView listarProdutos() {
        ModelAndView mv = new ModelAndView("home/index");
        mv.addObject("produtos", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("/produto/novo")
    public ModelAndView novoProduto() {
        ModelAndView mv = new ModelAndView("produto/novo");
        mv.addObject("produto", new Produto());
        mv.addObject("categorias", categoriaRepository.findAll());
        return mv;
    }

    @PostMapping("/produto/salvar")
    public ModelAndView salvarProduto(@Valid Produto produto, 
                                    @RequestParam(name = "categoria_id") Long categoriaId,
                                    BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("produto/novo");
            mv.addObject("produto", produto);
            mv.addObject("categorias", categoriaRepository.findAll());
            return mv;
        }

        Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
        if (categoriaOpt.isPresent()) {
            produto.setCategoria(categoriaOpt.get());
            produtoRepository.save(produto);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/produto/detalhes/{id}")
    public ModelAndView detalhesProduto(@PathVariable Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        
        if (produtoOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("produto/detalhes");
            mv.addObject("produto", produtoOpt.get());
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/produto/editar/{id}")
    public ModelAndView editarProduto(@PathVariable Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        
        if (produtoOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("produto/editar");
            mv.addObject("produto", produtoOpt.get());
            mv.addObject("categorias", categoriaRepository.findAll());
            return mv;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @PostMapping("/produto/atualizar/{id}")
    public ModelAndView atualizarProduto(@PathVariable Long id, 
                                       @Valid Produto produto,
                                       @RequestParam(name = "categoria_id") Long categoriaId,
                                       BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("produto/editar");
            mv.addObject("produto", produto);
            mv.addObject("categorias", categoriaRepository.findAll());
            return mv;
        }

        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produtoExistente = produtoOpt.get();
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setPreco(produto.getPreco());
            produtoExistente.setDescricao(produto.getDescricao());
            
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
            if (categoriaOpt.isPresent()) {
                produtoExistente.setCategoria(categoriaOpt.get());
            }
            
            produtoRepository.save(produtoExistente);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/produto/remover/{id}")
    public ModelAndView removerProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return new ModelAndView("redirect:/");
    }
}
