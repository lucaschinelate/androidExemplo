package model;

import agile.core.orm.annotation.Entity;
import agile.core.orm.annotation.Field;
import agile.core.orm.annotation.Id;
import agile.core.orm.annotation.JoinField;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

@Entity(tableName = "CLIENTE")
public class Cliente {

    @Id
    @Field(fieldName = "COD_CLIENTE")
    private Integer id;

    @Field(fieldName = "DSC_CLIENTE")
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
