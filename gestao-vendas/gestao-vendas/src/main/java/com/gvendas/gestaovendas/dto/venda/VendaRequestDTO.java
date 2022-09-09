package com.gvendas.gestaovendas.dto.venda;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Venda requisição DTO")
public class VendaRequestDTO {
    
    @ApiModelProperty(value = "Data")
    private LocalDate localDate;

    @ApiModelProperty(value = "Itens da venda")
    private List<ItemRequestDTO> itemRequestDTOs;

    public VendaRequestDTO(LocalDate localDate, List<ItemRequestDTO> itemRequestDTOs) {
        this.localDate = localDate;
        this.itemRequestDTOs = itemRequestDTOs;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public List<ItemRequestDTO> getItemRequestDTOs() {
        return itemRequestDTOs;
    }

    public void setItemRequestDTOs(List<ItemRequestDTO> itemRequestDTOs) {
        this.itemRequestDTOs = itemRequestDTOs;
    }

}
