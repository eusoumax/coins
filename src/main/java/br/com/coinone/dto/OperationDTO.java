package br.com.coinone.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class OperationDTO {

    private Long idUser;
    private String currencySource;
    private String currencyTarget;
    private Double valueSource;
    @Schema(hidden = true)
    private Double valueTarget;
    private Double fee;
    public Long getIdUser() {
        return idUser;
    }
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    public String getCurrencySource() {
        return currencySource;
    }
    public void setCurrencySource(String currencySource) {
        this.currencySource = currencySource;
    }
    public String getCurrencyTarget() {
        return currencyTarget;
    }
    public void setCurrencyTarget(String currencyTarget) {
        this.currencyTarget = currencyTarget;
    }
    public Double getValueSource() {
        return valueSource;
    }
    public void setValueSource(Double valueSource) {
        this.valueSource = valueSource;
    }
    public Double getValueTarget() {
        return valueTarget;
    }
    public void setValueTarget(Double valueTarget) {
        this.valueTarget = valueTarget;
    }
    public Double getFee() {
        return fee;
    }
    public void setFee(Double fee) {
        this.fee = fee;
    }
    
    
    
}
