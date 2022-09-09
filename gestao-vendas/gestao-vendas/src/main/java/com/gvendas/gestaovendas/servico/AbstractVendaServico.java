package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.stream.Collectors;

import com.gvendas.gestaovendas.dto.venda.ItemResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Venda;

public abstract class AbstractVendaServico {
    
    protected VendaResponseDTO criarVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        List<ItemResponseDTO> itensVendaResponseDTO = itensVendaList.stream()
                .map(this::criaItensResponseDTO).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDTO);
    }

    protected ItemResponseDTO criaItensResponseDTO(ItemVenda itemVenda) {
        return new ItemResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }
    
}
