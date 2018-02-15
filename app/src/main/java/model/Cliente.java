package model;

import agile.core.orm.*;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

@Entity(tableName = "CLIENTE")
public class Cliente {

    @Field(fieldName = "ID")
    public Integer id;

    @Field(fieldName = "NOME")
    public String nome;

    public int getId() {
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String Value) {
        this.nome = Value;
    }

}
