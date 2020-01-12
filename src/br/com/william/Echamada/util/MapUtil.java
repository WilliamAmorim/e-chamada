/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.util;

import br.com.william.Echamada.sql.Sql;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author willi
 */
public class MapUtil {    
    public static ObservableList listaHorario(int inicio){
        ObservableList<String> lista = FXCollections.observableArrayList();
        //for (int i = inicio; i < inicio+5; i++) {
            for(int j = 10; j <= 50 ; j += 10) {                
                lista.add(inicio+":"+j);               
            }
      // }
        return lista;
    }
    /**
     * Buscando e mostrando no mapa a localização do aluno
     * @param id
     * @param hora
     * @return 
     */
    public static String mostrarNoMapa(String id,String hora,String data){
        
        String latitude = "0",longitude = "0";
        Sql buscar = new Sql();
        ArrayList values = new ArrayList();
        values.add(id);
        values.add(hora);  
        values.add(data);
        ArrayList resultado = new ArrayList();
        String[] retorno = {"latitude","longitude"};
        resultado = buscar.executeQuery("SELECT `latitude`, `longitude` FROM `historicolocalizacao` WHERE id_aluno = ? AND hora = ? AND data = ?", values, retorno);
        if(!resultado.isEmpty()){
            for (int i = 0; i <= resultado.size() - 1; i++) {
                String a = resultado.get(i).toString();
                    String b = a.replace("[", "").replace("]", "");
                    String[] tokens = b.split(",");  
                    latitude = tokens[0].trim();
                    longitude = tokens[1].trim();
            }
            return "https://controledeentrada.000webhostapp.com/map.html?latitude="+latitude+"&longitude="+longitude;
        }else{
            return "https://controledeentrada.000webhostapp.com/map.html?latitude="+latitude+"&longitude="+longitude;
        }
       
        
    }
    
    public static ObservableList<String> verificarLocalização(ObservableList horas,String id,String data){
        ObservableList<String> returno = FXCollections.observableArrayList();
        for (int i = 0; i < horas.size(); i++) {
            Sql buscar = new Sql();
        ArrayList values = new ArrayList();
        values.add(id);
        values.add(horas.get(i));        
        values.add(data);        
        ArrayList resultado = new ArrayList();
        String[] retorno = {"latitude","longitude"};
        resultado = buscar.executeQuery("SELECT `latitude`, `longitude` FROM `historicolocalizacao` WHERE id_aluno = ? AND hora = ? AND data = ?", values, retorno);
            if(!resultado.isEmpty()){
               returno.add((String)horas.get(i));
            }else{
                returno.add(" -");
            }
        }
        return returno;
    }
}
