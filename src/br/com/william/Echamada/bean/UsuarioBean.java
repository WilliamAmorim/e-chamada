/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author willi
 */
public class UsuarioBean {
    private StringProperty tipo;
    private StringProperty chave;
    private StringProperty usuario;

    public UsuarioBean(String tipo,String chave,String usuario){
        this.tipo       = new SimpleStringProperty(tipo);
        this.chave      = new SimpleStringProperty(chave);
        this.usuario    =  new SimpleStringProperty(usuario);
    }
    public StringProperty getTipo() {
        return tipo;
    }

    public void setTipo(StringProperty tipo) {
        this.tipo = tipo;
    }

    public StringProperty getChave() {
        return chave;
    }

    public void setChave(StringProperty chave) {
        this.chave = chave;
    }

    public StringProperty getUsuario() {
        return usuario;
    }

    public void setUsuario(StringProperty usuario) {
        this.usuario = usuario;
    }
    

}
