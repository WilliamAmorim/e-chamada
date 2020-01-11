/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.util;

import br.com.william.Echamada.sql.Sql;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author willi
 */
public class Util {
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(converterData(dateString,"sql"), formatter);
        return localDate;
    }
    
    /**
     * Verifica se a liberação do aluno esta OK ou se ja passou da data
     * @param dateLiberacao 
     * @param dateHoje
     */
    public static void verificarData(String dateLiberacao,String dateHoje){  
        Sql update = new Sql();
        ArrayList values = new ArrayList();
        
        Date date2 = new Date(dateHoje);
        Date date1 = new Date(converterData(dateLiberacao,"normal"));
       
        if (date1.compareTo(date2) > 0) {         
            //System.out.println("Date1 é posterior a Date2");
            //updateLiberacao(date02);//Date1 é posterior a Date2
        } else if (date1.compareTo(date2) < 0) {     
            //System.out.println("Data1 é anterior a Data2");
           values.add(dateLiberacao);
           update.executeQuery("UPDATE `liberados` SET `status`='0' WHERE prazo = ?", values);
        } else if (date2.compareTo(date1) == 0) {    
            //System.out.println("Date1 is equal to Date2");
           values.add(dateLiberacao);
           update.executeQuery("UPDATE `liberados` SET `status`='0' WHERE prazo = ?", values);
        } else {
           // System.out.println("How to get here?");
        }
    }
    
    
    /**
     *
     * @return retorna a data atual
     */
    public static String time() {
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("d/MM/yyyy");
        return df.format(hoje);
    }
    
    public static String converterData(String data,String ordem){
       if(ordem.equals("normal")){ 
        String[] dividir = data.split("-");
        return dividir[2]+"/"+dividir[1]+"/"+dividir[0];
       }else if(ordem.equals("sql")){
        String[] dividir2 = data.split("/");
        return dividir2[2]+"-"+dividir2[1]+"-"+dividir2[0];
       }        
       return null;
    }
}
