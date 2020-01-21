/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 *
 * @author willi
 */
public class AbrirCriarSerie extends Application {
    
   private static Stage stage;
    private static Scene ScenePrincipal;
    
    
    int inicialize = 0;

    
    @Override
    public void start(Stage stage) throws Exception {
        AbrirCriarSerie.stage = stage;
        Parent fxmlInicio = FXMLLoader.load(getClass().getResource("FXML_criarSerie.fxml"));
        ScenePrincipal = new Scene(fxmlInicio);
        
      
        
        
        
        /*
        //ESTE COGIGO E RESPONSAVEL POR IMPEDIR QUE O USUARIO MAXIMIZE A TELA.
        stage.setResizable(false);
        stage.show();
        //***********************************************************************
        */

        //stage.setMaximized(true);
        switch(inicialize){
            case 0:stage.setScene(ScenePrincipal); stage.show();break;
            
        }
            
        
        
        
        
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
