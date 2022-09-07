package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraDeNegocioException;
import com.gvendas.gestaovendas.repositorio.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repositorio.VendaRepositorio;

@Service
public class VendaServico {

    private ClienteServico clienteServico;
    private VendaRepositorio vendaRepositorio;
    private ItemVendaRepositorio itemVendaRepositorio;

    @Autowired
    public VendaServico(ClienteServico clienteServico, VendaRepositorio vendaRepositorio,
            ItemVendaRepositorio itemVendaRepositorio) {
        this.clienteServico = clienteServico;
        this.vendaRepositorio = vendaRepositorio;
        this.itemVendaRepositorio = itemVendaRepositorio;
    }

    public ClienteVendaResponseDTO listaVendaPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendasCliente = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
                .map(this::criaVendaResponseDTO).collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendasCliente);
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigoCliente);
        if (cliente.isEmpty()) {
            throw new RegraDeNegocioException(
                    String.format("O cliente de código %s informado não existe no cadastro", codigoCliente));
        }
        return cliente.get();
    }

    private VendaResponseDTO criaVendaResponseDTO(Venda venda) {
        List<ItemResponseDTO> itensVendaResponseDTO = itemVendaRepositorio.findByVendaCodigo(venda.getCodigo()).stream()
                .map(this::criaItensResponseDTO).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDTO);
    }

    private ItemResponseDTO criaItensResponseDTO(ItemVenda itemVenda) {
        return new ItemResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }
}
