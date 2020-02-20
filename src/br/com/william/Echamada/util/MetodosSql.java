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
import javafx.scene.control.Alert;

/**
 *
 * @author willi
 */
public class MetodosSql extends Sql{
    
    public void CadastrarAluno(String operacao,String codigo,String aluno,String senha,LocalDate data,String sexo,String endereco,String pai,String mae,String telefone,String serie,String turma,String turno,String id){               
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
        if(!"".equals(codigo) && !"".equals(aluno) && !"".equals(senha) && date != null && !"".equals(sexo) && !"".equals(endereco) && !"".equals(pai) && !"".equals(mae) && !"".equals(telefone) && !"".equals(serie) && !"".equals(turma) && !"".equals(turno)){
            ArrayList values = new ArrayList();
            values.add(aluno);
            values.add(senha);       
            values.add(Util.converterData(data.toString(), "normal"));//dateFormat.format(date)
            values.add(sexo);
            values.add(endereco);
            values.add(pai);
            values.add(mae);
            values.add(telefone);
            values.add(serie);
            values.add(turma);
            values.add(turno);            
            switch(operacao){
                case "cadastrar":
                    values.add(0);executeQuery("INSERT INTO `alunos`(`nome_aluno`, `senha`, `data_nascimento`, `sexo`, `endereco`, `nome_pai`, `nome_mae`, `telefone_responsavel`, `serie`, `turma`, `turno`,`status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", values);

                    ArrayList v = new ArrayList();v.add(codigo);v.add(UtilLog.retornarIDaluno(aluno));
                    executeQuery("INSERT INTO `codigo_aluno`(`codigo`, `id_aluno`) VALUES (?,?)",v);

                    v.clear();v.add(UtilLog.retornarIDaluno(aluno));v.add(pai);v.add(Util.gerarSenha());
                    executeQuery("INSERT INTO `pais`(`id_aluno`,`usuario`,`senha`) VALUES (?,?,?)",v);                
                break;
                case "update":
                    values.add(id);executeQuery("UPDATE `alunos` SET `nome_aluno`=?, `senha`=?, `data_nascimento`=?, `sexo`=?, `endereco`=?, `nome_pai`=?, `nome_mae`=?, `telefone_responsavel`=?, `serie`=?, `turma`=?, `turno`=? WHERE id_aluno = ?", values);
                    
                    values.clear();values.add(pai);values.add(id);
                    executeQuery("UPDATE `pais` SET `usuario`=? WHERE id_aluno = ?",values);
                break;
            }
        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);                
                dialogoInfo.setHeaderText("Preencha todos os dados");
                dialogoInfo.showAndWait();
    

        }
    }
    public String criarQueryAluno(String serie,String turma,String turno,String status,String genero,String aluno,String numeroLinhas){
        String query = "SELECT * FROM `alunos` WHERE 1 ";
        if(!"null".equals(aluno)){
            query += "AND nome_aluno  LIKE '%"+aluno+"%'";
        }
        if(serie != null){
            query += " AND serie = '"+serie+"'";System.out.println("serie");
        }
        
        if(turma != null){
            query += " AND turma = '"+turma+"'";System.out.println("turma");
        }
        
        if(turno != null){
            query += " AND turno = '"+turno+"'";System.out.println("turno");
        }
        
        if(status != null){
            if(status.equals("Ativo")){
                status = "1";                
            }else{
                status = "0";
            }
            query += " AND status = '"+status+"'";System.out.println("Status");
        }
        
        if(genero != null){
            query += " AND sexo = '"+genero+"'";System.out.println("genero");
        }
        if(!"Todas".equals(numeroLinhas)){
            query += " limit "+numeroLinhas;
            
        }
        System.out.println(query);
        return query;
    }
    
    //**************************************************************************
    
    
    public void alterarPais(String idPais,String id,String usuario,String senha){
        if(!id.equals("") && !usuario.equals("") && !senha.equals("")){ 
            ArrayList values = new ArrayList();
            values.add(id);
            values.add(usuario);
            values.add(senha);
            values.add(idPais);


            executeQuery("UPDATE `pais` SET `id_aluno`=?,`usuario`=?,`senha`=? WHERE id_pais = ?",values);
            values.clear();values.add(usuario);values.add(id);
            executeQuery("UPDATE `alunos` SET `nome_pai`=? WHERE id_aluno = ?",values);
        }else{
            System.err.println("else pais");
        }
    }
}
