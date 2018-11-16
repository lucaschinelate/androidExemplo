package model;

import agile.core.orm.annotation.Entity;
import agile.core.orm.annotation.Field;
import agile.core.orm.annotation.Id;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

@Entity(tableName = "ROTA")
public class Rota {

    @Id
    @Field(fieldName = "COD_ROTA")
    private Integer id;

    @Field(fieldName = "DSC_ROTA")
    private String descricao;

    public int getId() {
        return this.id;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public void setDescricao(String Value) {
        this.descricao = Value;
    }

    public void setId(int Value) {
        this.id = Value;
    }

}
