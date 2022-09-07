package com.gvendas.gestaovendas.dto.venda;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Venda retorno DTO")
public class VendaResponseDTO {
  
    @ApiModelProperty(value = "Codigo")
    private Long codigo;

    @ApiModelProperty(value = "Data")
    private LocalDate localDate;

    @ApiModelProperty(value = "Itens da venda")
    private List<ItemResponseDTO> itemResponseDTOs;

    public VendaResponseDTO(Long codigo, LocalDate localDate, List<ItemResponseDTO> itemResponseDTOs) {
        this.codigo = codigo;
        this.localDate = localDate;
        this.itemResponseDTOs = itemResponseDTOs;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public List<ItemResponseDTO> getItemResponseDTOs() {
        return itemResponseDTOs;
    }

    public void setItemResponseDTOs(List<ItemResponseDTO> itemResponseDTOs) {
        this.itemResponseDTOs = itemResponseDTOs;
    }

    
}
