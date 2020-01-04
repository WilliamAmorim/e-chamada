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
public class TabelaHistoricoBean {
    private StringProperty aluno;
    private StringProperty data;
    private StringProperty aula01;
    private StringProperty aula02;
    private StringProperty aula03;
    private StringProperty aula04;
    private StringProperty aula05;

    public StringProperty getAluno() {
        return aluno;
    }

    public void setAluno(StringProperty aluno) {
        this.aluno = aluno;
    }

    public StringProperty getData() {
        return data;
    }

    public void setData(StringProperty data) {
        this.data = data;
    }

    public StringProperty getAula01() {
        return aula01;
    }

    public void setAula01(StringProperty aula01) {
        this.aula01 = aula01;
    }

    public StringProperty getAula02() {
        return aula02;
    }

    public void setAula02(StringProperty aula02) {
        this.aula02 = aula02;
    }

    public StringProperty getAula03() {
        return aula03;
    }

    public void setAula03(StringProperty aula03) {
        this.aula03 = aula03;
    }

    public StringProperty getAula04() {
        return aula04;
    }

    public void setAula04(StringProperty aula04) {
        this.aula04 = aula04;
    }

    public StringProperty getAula05() {
        return aula05;
    }

    public void setAula05(StringProperty aula05) {
        this.aula05 = aula05;
    }
    
    public TabelaHistoricoBean(String aluno,String data,String aula01,String aula02,String aula03,String aula04,String aula05){
        this.aluno =  new SimpleStringProperty(aluno);
        this.data = new SimpleStringProperty(data);
        this.aula01 =  new SimpleStringProperty(aula01);
        this.aula02 =  new SimpleStringProperty(aula02);
        this.aula03 =  new SimpleStringProperty(aula03);
        this.aula04 =  new SimpleStringProperty(aula04);
        this.aula05 =  new SimpleStringProperty(aula05);
    }

}
