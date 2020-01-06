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
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.Alert;

/**
 *
 * @author willi
 */
public class UtilLiberarAlunos {
    public ArrayList retornarValores(String serie,String turma,String turno,String aluno,LocalDate data,String mensagem,boolean todos){
        ArrayList values = new ArrayList();
        Calendar horaAtual = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
        int escolhido = 0;
        if(todos){
            values.add("todos");escolhido++;
        }else{
            if(serie != null && turma != null){
                values.add(serie+turma);escolhido++;System.out.println("escolhe 1");
            }else if(serie != null && turma != null){
                values.add("S"+serie);escolhido++;System.out.println("escolhe 2");
            }else if(serie != null && turma != null){
                values.add("T"+turma);escolhido++;System.out.println("escolhe 3");
            }
            
            if(turno != null){
                values.add(turno);escolhido++;System.out.println("escolhe 4");
            }
            if(!"".equals(aluno)){
                values.add(aluno);escolhido++;System.out.println("escolhe 5");
            }
            
        }
      
        if(date != null && mensagem != null && escolhido == 1){
            values.add(dateFormat.format(date));
            values.add(1);            
            values.add(""+horaAtual.get(Calendar.HOUR_OF_DAY)+":"+horaAtual.get(Calendar.MINUTE));        
            values.add(mensagem);
        }else {
            values.clear();
            return values;
        }
        
        
        return values;        
    }
    
    
    public void liberarAlunos(String serie,String turma,String turno,String aluno,LocalDate data,String mensagem,boolean todos){
        try{
            Sql liberar = new Sql();
            ArrayList values = new ArrayList();
            UtilLiberarAlunos li = new UtilLiberarAlunos();
            values = li.retornarValores(serie,turma,turno,aluno,data,mensagem,todos);
            if(!values.isEmpty()){
                liberar.executeQuery("INSERT INTO `liberados`(`parametro`, `prazo`, `status`, `hora`, `mensagem`) VALUES (?,?,?,?,?)", values);
                Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);                
                dialogoInfo.setHeaderText("Aluno Liberado");                
                dialogoInfo.showAndWait();  
            }else{
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);                
                dialogoInfo.setHeaderText("Preencha os dados correntamente");                
                dialogoInfo.showAndWait();  
            }
        }catch(Exception ex){
             Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                dialogoInfo.setHeaderText("Ocorreu um erro");                
                dialogoInfo.showAndWait();            
        }
    }
}
