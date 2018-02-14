package model;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

import agile.core.orm.Entity;
import agile.core.orm.EntityAnnotation;
import agile.core.orm.Field;

@EntityAnnotation(tableName = "CLIENTE")
public class ClienteTest extends Entity{

    @Field(fieldName = "ID")
    public int id;

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
