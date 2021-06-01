package br.com.coinone.dto;

import java.util.Calendar;

import br.com.coinone.model.Conversion;

public class OperationResponseDTO {

    private Long id;
    private Long idUser;
    private String currencySource;
    private Double valueSource;    
    private String currencyTarget;
    private Double valueTarget;
    private Double fee;
    private Calendar utcDate;

    public OperationResponseDTO(){
        super();
    }
    public OperationResponseDTO(Conversion conversion){
        this.id  = conversion.getId();
        this.idUser = conversion.getUser().getId();
        this.currencySource = conversion.getCurrencySource();
        this.currencyTarget = conversion.getCurrencyTarget();
        this.valueSource = conversion.getValueSource();
        this.valueTarget = conversion.getValueTarget();
        this.fee = conversion.getFee();
        this.utcDate = conversion.getUtcDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getValueSource() {
        return valueSource;
    }

    public void setValueSource(Double valueSource) {
        this.valueSource = valueSource;
    }

    public String getCurrencyTarget() {
        return currencyTarget;
    }

    public void setCurrencyTarget(String currencyTarget) {
        this.currencyTarget = currencyTarget;
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

    public Calendar getUtcDate() {
        return utcDate;
    }
    public void setUtcDate(Calendar utcDate) {
        this.utcDate = utcDate;
    }
    
}
