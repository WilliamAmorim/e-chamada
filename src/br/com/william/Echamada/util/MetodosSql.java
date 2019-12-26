/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.util;

import br.com.william.Echamada.sql.Sql;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author willi
 */
public class MetodosSql extends Sql{
    
    public void CadastrarAluno(String operacao,String codigo,String aluno,String senha,LocalDate data,String sexo,String endereco,String pai,String mae,String telefone,String serie,String turma,String turno,String id){        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());     
        ArrayList values = new ArrayList();
        values.add(aluno);
        values.add(senha);       
        values.add(dateFormat.format(date));
        values.add(sexo);
        values.add(endereco);
        values.add(pai);
        values.add(mae);
        values.add(telefone);
        values.add(serie);
        values.add(turma);
        values.add(turno);        
        values.add(id);
        switch(operacao){
            case "cadastrar":executeQuery("INSERT INTO `alunos`(`nome_aluno`, `senha`, `data_nascimento`, `sexo`, `endereco`, `nome_pai`, `nome_mae`, `telefone_responsavel`, `serie`, `turma`, `turno`,`status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", values);
            case "update":executeQuery("UPDATE `alunos` SET `nome_aluno`=?, `senha`=?, `data_nascimento`=?, `sexo`=?, `endereco`=?, `nome_pai`=?, `nome_mae`=?, `telefone_responsavel`=?, `serie`=?, `turma`=?, `turno`=? WHERE id_aluno = ?", values);
        }
        
    }
}
