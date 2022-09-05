package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.categoria.CategoriaRequestDTO;
import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.servico.CategoriaServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaServico categoriaServico;

    @ApiOperation(value = "Listar", nickname = "listarTodasCategoria")
    @GetMapping
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaServico.listarTodas().stream()
                .map(categoria -> CategoriaResponseDTO.converterParaCategoriaDTO(categoria))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar por código", nickname = "buscarCategoriaPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Categoria> categoria = categoriaServico.buscarPorCodigo(codigo);
        return categoria.isPresent()
                ? ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoria.get()))
                : ResponseEntity.notFound().build();
    }

    // @valid irá aplicar as constrints bean validatio da class produto @NotNull e
    // @Length
    @ApiOperation(value = "Salvar", nickname = "salvarCategoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaSalva = categoriaServico.salvar(categoriaDTO.convertParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaSalva));
    }

    @ApiOperation(value = "Atualizar", nickname = "atualizarCategoria")
    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable(name = "codigo") Long codigo,
            @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        Categoria categoriaAtualizada = categoriaServico.atualizar(codigo, categoriaDTO.convertParaEntidade(codigo));
        return ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaAtualizada));
    }

    @ApiOperation(value = "Deletar", nickname = "deletarCategoria")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable(name = "codigo") Long codigo, @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        categoriaServico.deletar(codigo);
    }
}
