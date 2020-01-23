/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.util;

import br.com.william.Echamada.sql.Sql;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class UtilLog {
    private String diretoriolog = "C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Local\\logEchamada\\log.txt";

    public String getDiretoriolog() {
        return diretoriolog;
    }

    public void setDiretoriolog(String diretoriolog) {
        this.diretoriolog = diretoriolog;
    }
    public  UtilLog(){
        File diretorio = new File("C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Local\\logEchamada");
        
        if (diretorio.exists() == false){   
            diretorio.mkdir();
        }
    }
    
     public static String retornarID(String usuario){
        Sql buscarID = new Sql();
        String[] r = {"id_admin"};
        ArrayList values = new ArrayList();
        values.add(usuario);
        ArrayList retorno = new ArrayList();        
        retorno = buscarID.executeQuery("SELECT `id_admin` FROM `admin` WHERE  usuario = ?", values,r);
        if(!retorno.isEmpty()){
            String a = retorno.get(0).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");
            return tokens[0].trim();
        }
        return null;
    }
}
