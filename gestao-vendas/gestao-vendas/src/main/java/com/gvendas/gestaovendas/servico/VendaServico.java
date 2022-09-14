package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraDeNegocioException;
import com.gvendas.gestaovendas.repositorio.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repositorio.VendaRepositorio;

@Service
public class VendaServico extends AbstractVendaServico {

    private ClienteServico clienteServico;
    private VendaRepositorio vendaRepositorio;
    private ItemVendaRepositorio itemVendaRepositorio;
    private ProdutoServico produtoServico;

    @Autowired
    public VendaServico(ClienteServico clienteServico, VendaRepositorio vendaRepositorio,
            ItemVendaRepositorio itemVendaRepositorio, ProdutoServico produtoServico) {
        this.clienteServico = clienteServico;
        this.vendaRepositorio = vendaRepositorio;
        this.itemVendaRepositorio = itemVendaRepositorio;
        this.produtoServico = produtoServico;
    }

    public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendasResponseDTOList = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
                .map(vendaCliente -> criarVendaResponseDTO(vendaCliente,
                        itemVendaRepositorio.findByVendaPorCodigo(vendaCliente.getCodigo())))
                .collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendasResponseDTOList);
    }

    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda vendaCliente = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaPorCodigo(vendaCliente.getCodigo());
        return criaClienteVendaResponseDTO(vendaCliente, itensVendaList);

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDTO) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExisteERetirarEstoque(vendaDTO.getItemRequestDTOs());
        Venda vendaSalva = salvarVenda(cliente, vendaDTO);
        return criaClienteVendaResponseDTO(vendaSalva,
                itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deletar(Long codigoVenda){
        validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVenda);
        itemVendaRepositorio.deleteAll(itensVenda);
        vendaRepositorio.deleteById(codigoVenda);        
    }

    public ClienteVendaResponseDTO atualizar(Long codigoCliente, Long codigoVenda, VendaRequestDTO vendaDTO){
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVenda);
        validarProdutoExisteERetirarEstoque(vendaDTO.getItemRequestDTOs());
        itemVendaRepositorio.deleteAll(itensVenda);
        Venda vendaAtualizada = atualizarVenda(cliente, codigoVenda, vendaDTO);
        return criaClienteVendaResponseDTO(vendaAtualizada, itemVendaRepositorio.findByVendaPorCodigo(codigoVenda));
    }
    
    private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itensVenda) {
        itensVenda.forEach(item -> {
            Produto produto = produtoServico.validarProdutoExiste(item.getProduto().getCodigo());
            produto.setQuantidade(produto.getQuantidade()+item.getQuantidade());
            produtoServico.atualizarQuantidadeDoEstoque(produto);
        });
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDTO) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(vendaDTO.getLocalDate(), cliente));
        vendaDTO.getItemRequestDTOs().stream().map(itemVendaDto -> criarItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepositorio::save);
        return vendaSalva;
    }

    private Venda atualizarVenda(Cliente cliente, Long codigoVenda, VendaRequestDTO vendaDTO) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(codigoVenda, vendaDTO.getLocalDate(), cliente));
        vendaDTO.getItemRequestDTOs().stream().map(itemVendaDto -> criarItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepositorio::save);
        return vendaSalva;
    }

    private void validarProdutoExisteERetirarEstoque(List<ItemRequestDTO> itemRequestDTOs) {
        itemRequestDTOs.forEach(item -> {
            Produto produto = produtoServico.validarProdutoExiste(item.getCodigoProduto());
            validarQuantidadeProdutoExiste(produto, item.getQuantidade());
            produto.setQuantidade(produto.getQuantidade()-item.getQuantidade());
            produtoServico.atualizarQuantidadeDoEstoque(produto);
        });
    }

    private void validarQuantidadeProdutoExiste(Produto produto, Integer quantidade) {
        if (produto.getQuantidade() < quantidade) {
            throw new RegraDeNegocioException(
                    String.format("Foi pedido %s itens do produto %s, porém há %s disponível em estoque", quantidade,
                            produto.getDescricao(), produto.getQuantidade()));
        }
    }

    private Venda validarVendaExiste(Long codigoVenda) {
        Optional<Venda> venda = vendaRepositorio.findById(codigoVenda);
        if (venda.isEmpty()) {
            throw new RegraDeNegocioException(
                    String.format("A venda de código %s informado não existe no cadastro", codigoVenda));
        }
        return venda.get();
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigoCliente);
        if (cliente.isEmpty()) {
            throw new RegraDeNegocioException(
                    String.format("O cliente de código %s informado não existe no cadastro", codigoCliente));
        }
        return cliente.get();
    }

}
