package com.gvendas.gestaovendas.servico;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;
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
                        itemVendaRepositorio.findByVendaCodigo(vendaCliente.getCodigo())))
                .collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendasResponseDTOList);
    }

    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda vendaCliente = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaCodigo(vendaCliente.getCodigo());
        return new ClienteVendaResponseDTO(vendaCliente.getCliente().getNome(),
                Arrays.asList(criarVendaResponseDTO(vendaCliente, itensVendaList)));

    }

    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDTO) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExiste(vendaDTO.getItemRequestDTOs());
        Venda vendaSalva = salvarVenda(cliente, vendaDTO);
        return new ClienteVendaResponseDTO(vendaSalva.getCliente().getNome(), Arrays.asList(
                criarVendaResponseDTO(vendaSalva, itemVendaRepositorio.findByVendaCodigo(vendaSalva.getCodigo()))));
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDTO) {
        Venda vendaSalva = vendaRepositorio.save(new Venda(vendaDTO.getLocalDate(), cliente));
        vendaDTO.getItemRequestDTOs().stream().map(itemVendaDto -> criarItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepositorio::save);
        return vendaSalva;
    }

    private ItemVenda criarItemVenda(ItemRequestDTO itemVendaDto, Venda venda) {
        return new ItemVenda(itemVendaDto.getQuantidade(), itemVendaDto.getPrecoVendido(),
                new Produto(itemVendaDto.getCodigoProduto()), venda);
    }

    private void validarProdutoExiste(List<ItemRequestDTO> itemRequestDTOs) {
        itemRequestDTOs.forEach(item -> produtoServico.validarProdutoExiste(item.getCodigoProduto()));
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
