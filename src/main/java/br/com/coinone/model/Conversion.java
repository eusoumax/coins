package br.com.coinone.model;

import java.util.Calendar;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import br.com.coinone.dto.OperationDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
@RegisterForReflection
@Entity
public  class Conversion extends PanacheEntity{


	

    @OneToOne   
    private User user;
//    ● Moeda origem;
    private String currencySource;
//    ● Valor origem;
    private Double valueSource;
//    ● Moeda destino;
    private String currencyTarget;
//    ● Valor destino;
    private Double valueTarget;
//    ● Taxa de conversão utilizada;
    private Double fee;
//    ● Data/Hora UTC;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar utcDate;


	@Schema(hidden = true)
	public Long getId() {
		return super.id;
	}

	public Conversion(){
		super();
	}

    public Conversion(OperationDTO dto,User user){
        this.currencySource = dto.getCurrencySource();
        this.currencyTarget = dto.getCurrencyTarget();
        this.valueSource = dto.getValueSource();
        this.valueTarget = dto.getValueTarget();
        this.fee = dto.getFee();
        this.user = user;
    }



    
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
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
