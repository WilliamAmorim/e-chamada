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
public class AlunosBean {
    private StringProperty status;

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }
    private StringProperty id;    
    private StringProperty aluno;
    private StringProperty data_nascimento;
    private StringProperty sexo;
    private StringProperty endereco;
    private StringProperty nome_pai;
    private StringProperty nome_mae;
    private StringProperty telefone;
    private StringProperty serie;
    private StringProperty turma;
    private StringProperty turno;

    public StringProperty getId() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }
    
    public AlunosBean(String status,String id,String aluno,String data_nascimento,String sexo,String endereco,String nome_pai,String nome_mae,String telefone,String serie,String turma,String turno ){
        this.status = new SimpleStringProperty(status);
        this.id = new SimpleStringProperty(id);
        this.aluno = new SimpleStringProperty(aluno);
        this.data_nascimento = new SimpleStringProperty(data_nascimento);
        this.sexo = new SimpleStringProperty(sexo);
        this.endereco = new SimpleStringProperty(endereco);
        this.nome_pai = new SimpleStringProperty(nome_pai);
        this.nome_mae = new SimpleStringProperty(nome_mae);
        this.telefone = new SimpleStringProperty(telefone);
        this.serie = new SimpleStringProperty(serie);
        this.turma = new SimpleStringProperty(turma);
        this.turno = new SimpleStringProperty(turno);
        
    }

    public StringProperty getAluno() {
        return aluno;
    }

    public void setAluno(StringProperty aluno) {
        this.aluno = aluno;
    }

    public StringProperty getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(StringProperty data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public StringProperty getSexo() {
        return sexo;
    }

    public void setSexo(StringProperty sexo) {
        this.sexo = sexo;
    }

    public StringProperty getEndereco() {
        return endereco;
    }

    public void setEndereco(StringProperty endereco) {
        this.endereco = endereco;
    }

    public StringProperty getNome_pai() {
        return nome_pai;
    }

    public void setNome_pai(StringProperty nome_pai) {
        this.nome_pai = nome_pai;
    }

    public StringProperty getNome_mae() {
        return nome_mae;
    }

    public void setNome_mae(StringProperty nome_mae) {
        this.nome_mae = nome_mae;
    }

    public StringProperty getTelefone() {
        return telefone;
    }

    public void setTelefone(StringProperty telefone) {
        this.telefone = telefone;
    }

    public StringProperty getSerie() {
        return serie;
    }

    public void setSerie(StringProperty serie) {
        this.serie = serie;
    }

    public StringProperty getTurma() {
        return turma;
    }

    public void setTurma(StringProperty turma) {
        this.turma = turma;
    }

    public StringProperty getTurno() {
        return turno;
    }

    public void setTurno(StringProperty turno) {
        this.turno = turno;
    }
            
}
