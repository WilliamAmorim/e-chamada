/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.controller;

import br.com.william.Echamada.sql.Sql;
import br.com.william.Echamada.util.UtilLog;
import br.com.william.Echamada.view.AbrirTelas;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class FXML_loginController implements Initializable {
    //Componentes***************************************************************
    @FXML
    private TextField txt_codigoAcesso;

    @FXML
    private TextField txt_usuarioCadastro;

    @FXML
    private PasswordField txt_senhaCadastro;

    @FXML
    private ProgressIndicator progress_cadastro;

    @FXML
    private TextField txt_usuarioLogin;

    @FXML
    private PasswordField txt_senhaLogin;

    @FXML
    private ProgressIndicator progress_login;

    @FXML
    void BT_cadastrese(ActionEvent event) {
        try{
            new Thread(){
                public void run(){
                    progress_cadastro.setVisible(true);
                    if(txt_codigoAcesso.getText() != null && txt_usuarioCadastro.getText() != null && txt_senhaCadastro.getText() != null){
                        cadastrarAdmin();
                    }
                    progress_cadastro.setVisible(false);                                  
                }
            }.start();
           
        }catch(Exception ex){
            progress_cadastro.setVisible(false);
            //mostrar mensagem de erro
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                dialogoInfo.setHeaderText("Não foi possivel Fazer o cadastro");
                dialogoInfo.setContentText("Verifique o codigo de acesso");
                dialogoInfo.showAndWait();
        }
    }

    @FXML
    void BT_entrarLogin(ActionEvent event) {
        try{
            new Thread(){
                public void run(){
                    progress_login.setVisible(true);
                    if(txt_usuarioLogin.getText() != null && txt_senhaLogin.getText() != null){
                        fazerLogin();
                    }
                    progress_login.setVisible(false);                                  
                }
            }.start();            
        }catch(Exception ex){
            progress_login.setVisible(false);
            //mostrar mensagem de erro
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                dialogoInfo.setHeaderText("Não foi possivel Fazer o login");
                dialogoInfo.setContentText("Verifique sua conexão com a internet e tente novamente");
                dialogoInfo.showAndWait();        
        }
    }
    //**************************************************************************
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        progress_cadastro.setVisible(false);
        progress_login.setVisible(false);
    }   
   
    public boolean validarChaveAcesso(String chave){
        Sql buscarChave = new Sql();
        String[] r = {"chaveAcesso","id"};
        ArrayList values = new ArrayList();
        ArrayList retorno = new ArrayList();
        values.add(chave);
        retorno = buscarChave.executeQuery("SELECT `chaveAcesso`, `id` FROM `acesso` WHERE chaveAcesso = ?", values,r);
        for (int i = 0; i < retorno.size(); i++) {
            String a = retorno.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");           
            if("0".equals(tokens[1].trim())){                
                return true;                
            }
        }
        return false;
        
    }   
    public void cadastrarAdmin(){
        if(validarChaveAcesso(txt_codigoAcesso.getText())){
            Sql cadastrar = new Sql();
            ArrayList values = new ArrayList();
            values.add(txt_usuarioCadastro.getText());
            values.add(txt_senhaCadastro.getText());
            values.add(txt_codigoAcesso.getText().substring(0, 1));
            cadastrar.executeQuery("INSERT INTO `admin`(`usuario`, `senha`, `nivel`) VALUES (?,?,?)", values);
            values.clear();
            values.add(UtilLog.retornarID(txt_usuarioCadastro.getText()));
            values.add(txt_codigoAcesso.getText());
            cadastrar.executeQuery("UPDATE `acesso` SET `id`=? WHERE chaveAcesso = ?", values);
            
            AbrirTelas abrir = new AbrirTelas();
            criarLog(txt_usuarioCadastro.getText());
            abrir.abrirScene("principal");
        }else{
            
        }
        
    }
            
    public void fazerLogin(){
        Sql buscar = new Sql();
        String[] r = {"usuario","senha"};
        ArrayList values = new ArrayList();
        values.add(txt_usuarioLogin.getText());
        values.add(txt_senhaLogin.getText());
        ArrayList returno = new ArrayList();
        returno = buscar.executeQuery("SELECT `usuario`, `senha`, `nivel` FROM `admin` WHERE usuario = ? AND senha = ?", values,r);
        for (int i = 0; i < returno.size(); i++) {
            String a = returno.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");           
            if(txt_usuarioLogin.getText().equals(tokens[0].trim()) && txt_senhaLogin.getText().equals(tokens[1].trim())){                
                    AbrirTelas abrir = new AbrirTelas();
                    criarLog(txt_usuarioLogin.getText());
                    Platform.runLater(()->abrir.abrirScene("principal"));       
            }
        }
    }
    private void criarLog(String nome){
        UtilLog pegar = new UtilLog();
        File file = new File(pegar.getDiretoriolog());
        if (file.delete()) {}
        File diretorio = new File(pegar.getDiretoriolog());
        
        if (diretorio.exists() == false){               
            try {
                FileWriter arquivo = new FileWriter(pegar.getDiretoriolog());
                System.out.println("inserindo");                
                arquivo.write(nome);  
                Sql novo = new Sql();
                ArrayList values = new ArrayList();
                values.add(UtilLog.retornarID(nome));           
                values.add(InetAddress.getLocalHost().getHostAddress());            
                novo.executeQuery("INSERT INTO `log`(`id`, `ip`) VALUES (?,?)", values);
                arquivo.close();  
            } catch (IOException ex) {
              
               System.out.print("Erro no Logo:");
               System.err.print(ex);
            }    
        }    
    }
}
