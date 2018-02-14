package model;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

import agile.core.orm.*;

public class Cliente extends Entity{

    public Cliente() {
        this.setTableName("CLIENTE");
        this.addField("ID", "COD_CLIENTE");
        this.addField("NOME","NOM_CLIENTE","NOME NÃO INFORMADO");
    }

    public int getId() throws Exception {
        return (Integer) this.getFieldValue("ID");
    }

    public String getNome() throws Exception {
        return (String) this.getFieldValue("NOME");
    }

    public void setNome(String Value) throws Exception {
        this.setFieldValue("NOME", Value);
    }

}
