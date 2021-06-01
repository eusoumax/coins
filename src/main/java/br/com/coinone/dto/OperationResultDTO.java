package br.com.coinone.dto;



import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@JsonIgnoreProperties(ignoreUnknown = true)
public  class OperationResultDTO {

    //"success":true,                                  
    private Boolean success;
    //"terms":"https:\/\/currencylayer.com\/terms",    
    private String terms;
    //"privacy":"https:\/\/currencylayer.com\/privacy",
    private String privacy;
    //"timestamp":1622340844,   
    private long timestamp;                       
    //"source":"USD",
    private String source;

    private ErrorDTO error;

    private Map<String,Double> quotes;
    //"quotes":{                                       
    //"USDBRL":5.22635                               
    //}                                                
    
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Double> quotes) {
        this.quotes = quotes;
    }


   
    

}
