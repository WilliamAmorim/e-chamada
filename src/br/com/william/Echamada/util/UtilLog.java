/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.util;

import br.com.william.Echamada.sql.Sql;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import javafx.scene.control.Alert;

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
     
    public static String retornarIDaluno(String usuario){
        Sql buscarID = new Sql();
        String[] r = {"id_aluno"};
        ArrayList values = new ArrayList();
        values.add(usuario);
        ArrayList retorno = new ArrayList();        
        retorno = buscarID.executeQuery("SELECT `id_aluno` FROM `alunos` WHERE  nome_aluno = ?", values,r);
        if(!retorno.isEmpty()){
            String a = retorno.get(0).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");
            return tokens[0].trim();
        }
        return null;
    }
    public void sairLog() throws IOException{
        
        File file = new File(getDiretoriolog());
        try {
            FileInputStream arquivo = new FileInputStream(getDiretoriolog());
            InputStreamReader in = new InputStreamReader(arquivo);
            BufferedReader br = new BufferedReader(in);
            Sql novo = new Sql();
            ArrayList values = new ArrayList();           
            values.add(InetAddress.getLocalHost().getHostAddress());
            arquivo.close();
            novo.executeQuery("DELETE FROM `log` WHERE ip = ?", values);
        
                try{
                    if (file.delete()) {}
                    Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);                
                    dialogoInfo.setHeaderText("Deletando o arquivo");
                    dialogoInfo.setContentText("");
                    dialogoInfo.showAndWait();
                }catch(Exception ex){
                    Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);                
                    dialogoInfo.setHeaderText("Erro ao deletar arquivo");
                    dialogoInfo.setContentText("");
                    dialogoInfo.showAndWait();
                }    
            
            System.exit(0);

        } catch (FileNotFoundException ex) {
            
        }
    }
}
