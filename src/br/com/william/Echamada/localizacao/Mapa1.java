/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.localizacao;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 *
 * @author willi
 */
public class Mapa1 {

    /**
     * Metodo usado para mostrar um mapa javaSript do google maps
     *
     * @param latitude
     * @param longitude
     * @return uma view que sera adicionada ao componente de for utilizar o
     * mapa.
     */
    public static JFXPanel mostraMap(double latitude,double longitude) {
        
        
             final JFXPanel fxpanel=new JFXPanel();
   
 
            Platform.runLater(new Runnable(){
            @Override
            public void run() {
                WebEngine engine;
                WebView wv =new WebView();
                engine=wv.getEngine();
                
                //engine.load("D://Projetos-git//java//controleDeEntrada//map.html?latitude="+latitude+"&longitude="+longitude);         
                engine.load("http://controledeentrada-com.umbler.net/map.html?latitude="+latitude+"&longitude="+longitude);         
               
                 fxpanel.setScene(new Scene(wv));
            }
            
        });

            
        
        
        return fxpanel;
    }
    
   
    
    
    public static void main(String[] args){
        
    }
    
    
    
}
