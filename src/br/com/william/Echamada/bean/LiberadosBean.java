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
public class LiberadosBean {
    private StringProperty  parametro;    
    private StringProperty  prazo;
    private StringProperty  hora;
    private StringProperty  mensagem;

    public StringProperty getParametro() {
        return parametro;
    }

    public void setParametro(StringProperty parametro) {
        this.parametro = parametro;
    }

    public StringProperty getPrazo() {
        return prazo;
    }

    public void setPrazo(StringProperty prazo) {
        this.prazo = prazo;
    }

    public StringProperty getHora() {
        return hora;
    }

    public void setHora(StringProperty hora) {
        this.hora = hora;
    }

    public StringProperty getMensagem() {
        return mensagem;
    }

    public void setMensagem(StringProperty mensagem) {
        this.mensagem = mensagem;
    }
    
    public LiberadosBean(String parametro,String prazo,String hora,String mensagem){
        this.parametro = new SimpleStringProperty(parametro);
        this.prazo = new SimpleStringProperty(prazo);
        this.hora = new SimpleStringProperty(hora);
        this.mensagem = new SimpleStringProperty(mensagem);
        
    }
   
}
