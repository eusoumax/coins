package br.com.coinone.model;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class User extends PanacheEntity{
           
    public String name;
    public String age;

    
    public Long getId() {
        return super.id;
    }
    
}
