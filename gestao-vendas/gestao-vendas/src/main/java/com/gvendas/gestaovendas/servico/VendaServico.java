package com.gvendas.gestaovendas.servico;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraDeNegocioException;
import com.gvendas.gestaovendas.repositorio.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repositorio.VendaRepositorio;

@Service
public class VendaServico extends AbstractVendaServico {

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
        List<VendaResponseDTO> vendasResponseDTOList = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
                .map(vendaCliente -> criaVendaResponseDTO(vendaCliente,
                        itemVendaRepositorio.findByVendaCodigo(vendaCliente.getCodigo())))
                .collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendasResponseDTOList);
    }

    public ClienteVendaResponseDTO listaVendaPorCodigo(Long codigo) {
        Venda vendaCliente = validarVendaExiste(codigo);
        List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaCodigo(vendaCliente.getCodigo());
        return new ClienteVendaResponseDTO(vendaCliente.getCliente().getNome(),
                Arrays.asList(criaVendaResponseDTO(vendaCliente, itensVendaList)));

    }

    private Venda validarVendaExiste(Long codigo) {
        Optional<Venda> venda = vendaRepositorio.findById(codigo);
        if (venda.isEmpty()) {
            throw new RegraDeNegocioException(
                    String.format("A venda de código %s informado não existe no cadastro", codigo));
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
