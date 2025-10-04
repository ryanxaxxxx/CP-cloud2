package com.example.cpcloud2.Repository;

import com.example.cpcloud2.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
