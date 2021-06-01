package br.com.coinone.dto;

import java.util.Date;

import br.com.coinone.model.User;



public  class CurrencyConversion {

    private User user;
    private String currencySource; //"moedaOrigem": "BTC",
    private Double valueSource;  //"valorOrigem": "0.0002",
    private Double fee;//"taxaConversao": "0.01",
    private String currencyTarget;//"moedaDestino": "USDT",
    private Double valueTarget;//"valorDestino": "23",
    private Date date;//"dataHoraTransacao": "15-05-202105:06:12",   
    
    public CurrencyConversion(){
        super();
    }

    public CurrencyConversion(OperationDTO dto,User user){
        this.currencySource = dto.getCurrencySource();
        this.currencyTarget = dto.getCurrencyTarget();
        this.valueSource = dto.getValueSource();        
        this.fee = dto.getFee();
        this.user = user;
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
    public Double getFee() {
        return fee;
    }
    public void setFee(Double fee) {
        this.fee = fee;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }



}
