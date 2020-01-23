/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.view;




import br.com.william.Echamada.controller.FXML_loginController;
import br.com.william.Echamada.sql.Sql;
import br.com.william.Echamada.util.UtilLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author willi
 */
public class AbrirTelas extends Application{
    private static Stage stage;
    private static Scene ScenePrincipal;
    private static Scene SceneLogin;
    
    
    int inicialize = 1;

    
    @Override
    public void start(Stage stage) throws Exception {
        AbrirTelas.stage = stage;
        Parent fxmlInicio = FXMLLoader.load(getClass().getResource("FXML_principal.fxml"));
        ScenePrincipal = new Scene(fxmlInicio);
        
        Parent fxmlLogin = FXMLLoader.load(getClass().getResource("FXML_login.fxml"));
        SceneLogin = new Scene(fxmlLogin);
        
        
        
        /*
        //ESTE COGIGO E RESPONSAVEL POR IMPEDIR QUE O USUARIO MAXIMIZE A TELA.
        stage.setResizable(false);
        stage.show();
        //***********************************************************************
        */
        verificarLog();
        //stage.setMaximized(true);
        switch(inicialize){
            case 0:stage.setScene(ScenePrincipal); stage.show();break;
            case 1:stage.setScene(SceneLogin);stage.show();break;            
        }
            
        
        
        
        
    }
//    public void verificarLog(){        
//        File diretorio = new File("D:\\Projetos-git\\java\\Quiz\\src\\br\\com\\william\\jogoquiz\\log\\log.txt");
//        Sql novo = new Sql();
//        if (diretorio.exists() == true){  
//            try {
//                FileInputStream arquivo = new FileInputStream("D:\\Projetos-git\\java\\Quiz\\src\\br\\com\\william\\jogoquiz\\log\\log.txt");
//                InputStreamReader in = new InputStreamReader(arquivo); 
//                BufferedReader br = new BufferedReader(in);
//                String query = "";                        
//                String nome = br.readLine();
//                
//                System.out.println("nome:"+nome);
//                
//                if(nome != null){                     
//                        //nome = br.readLine().substring(0, 1);
//
//                    switch(nome){
//                        case "0":query = "SELECT `status` FROM `aluno` where nome_aluno = ? AND status = '1'";break;
//                        case "1":query = "SELECT `status` FROM `professor` where nome_professor = ? AND status = '1'";break;
//                    }
//                    String[] retorno = {"status"};
//                    ArrayList valorRetornado = new ArrayList();
//                    ArrayList values = new ArrayList();                
//                    values.add(br.readLine().substring(4));
//                    
//                   
//                    valorRetornado = novo.executeQuery(query, values, retorno);
//                    if(valorRetornado.isEmpty()){
//                         System.out.println("nome2:"+values.get(0));
//                    }else{
//                        System.out.println("nome3:"+values.get(0));
////                        switch(nome){;
////                            case "0":novaTela.abrirScene("inicioAluno");System.out.println("nome4:"+values.get(0));break;
////                            case "1":novaTela.abrirScene("inicioProfessor");System.out.println("nome5:"+values.get(0)); break;
////                        }
//                        if("1".equals(nome)){
//                            //novaTela.abrirScene("inicioProfessor");
//                            inicialize = 2;
//                        }else if("0".equals(nome)){
//                            //novaTela.abrirScene("inicioAluno");
//                             inicialize = 1;
//                        }
//                    }
//                    arquivo.close();  
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(FXML_inicioController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }      
//    }
    public void abrirScene(String scene){
        switch(scene){
            case "principal":stage.setScene(ScenePrincipal);break;
            
            
        }
        
    }
    
    
      public void verificarLog(){     
        UtilLog pegar = new UtilLog();
        File diretorio = new File(pegar.getDiretoriolog());       
        if (diretorio.exists() == true){  
            try {
                FileInputStream arquivo = new FileInputStream(pegar.getDiretoriolog());
                InputStreamReader in = new InputStreamReader(arquivo); 
                BufferedReader br = new BufferedReader(in);                                        
                String nome = br.readLine();
                
                System.out.println("nome:"+nome);
                
                if(nome != null){                                                                                         
                        if(verificarIP(UtilLog.retornarID(nome))){
                        
                            inicialize = 0;
                        }else{
                            inicialize = 1;
                        }
                    
                    arquivo.close();  
                }
            } catch (IOException ex) {
                
            }
        }      
    }

    public boolean verificarIP(String n){
        try{
            ArrayList v = new ArrayList();
            Sql novo = new Sql();
            ArrayList valorRetornado = new ArrayList();
            v.add(n);
            v.add(InetAddress.getLocalHost().getHostAddress());
            String[] retur = {"ip"};     
            valorRetornado = novo.executeQuery("SELECT `ip` FROM `log` WHERE id = ? AND ip = ?", v, retur);
            System.out.println("Valor retornado = "+valorRetornado.toString());
            if(!valorRetornado.isEmpty()){
                return true;
            }else{
                return false;
            }  
        }catch(Exception ex){
            
        }
        return false;
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
