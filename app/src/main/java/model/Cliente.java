package model;

import agile.core.orm.annotation.Entity;
import agile.core.orm.annotation.Field;
import agile.core.orm.annotation.Id;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

@Entity(tableName = "CLIENTE")
public class Cliente {

    @Id
    @Field(fieldName = "ID")
    private Integer id;

    @Field(fieldName = "NOME")
    private String nome;

    public int getId() {
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String Value) {
        this.nome = Value;
    }

    public void setId(int Value) {
        this.id = Value;
    }

}
