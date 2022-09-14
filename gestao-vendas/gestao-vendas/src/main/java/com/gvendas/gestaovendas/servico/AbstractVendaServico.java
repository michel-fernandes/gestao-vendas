package com.gvendas.gestaovendas.servico;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemRequestDTO;
import com.gvendas.gestaovendas.dto.venda.ItemResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;

public abstract class AbstractVendaServico {
    
    protected VendaResponseDTO criarVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        List<ItemResponseDTO> itensVendaResponseDTO = itensVendaList.stream()
                .map(this::criarItensResponseDTO).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDTO);
    }

    protected ItemResponseDTO criarItensResponseDTO(ItemVenda itemVenda) {
        return new ItemResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }

    protected ClienteVendaResponseDTO criaClienteVendaResponseDTO(Venda venda, List<ItemVenda> itemVenda) {
        return new ClienteVendaResponseDTO(venda.getCliente().getNome(), Arrays.asList(
                criarVendaResponseDTO(venda, itemVenda)));
    }

    protected ItemVenda criarItemVenda(ItemRequestDTO itemVendaDto, Venda venda) {
        return new ItemVenda(itemVendaDto.getQuantidade(), itemVendaDto.getPrecoVendido(),
                new Produto(itemVendaDto.getCodigoProduto()), venda);
    }
}
