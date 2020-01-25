/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.controller;

import br.com.william.Echamada.bean.UsuarioBean;
import br.com.william.Echamada.sql.Sql;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class FXML_usuariosController implements Initializable {
    //Componentes***************************************************************
    @FXML
    private RadioButton radio_administrador;

    @FXML
    private ToggleGroup radio_grupo;

    @FXML
    private RadioButton radio_usuario;

    @FXML
    private TableView<UsuarioBean> tabela_chaves;

    @FXML
    private TableColumn<UsuarioBean, String> coluna_tipo;

    @FXML
    private TableColumn<UsuarioBean, String> coluna_chave;

    @FXML
    private TableColumn<UsuarioBean, String> coluna_usuario;
    
    private ObservableList<UsuarioBean> ConteudoTabelaChaves = FXCollections.observableArrayList();

    @FXML
    void BT_criarChave(ActionEvent event) {
        adicionarChave();
    }

    @FXML
    void BT_removerChave(ActionEvent event) {
        removerChave();
    }
    //**************************************************************************
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //coluna_mensagem.setCellValueFactory(cellData -> cellData.getValue().getMensagem());
        coluna_tipo.setCellValueFactory(cellData -> cellData.getValue().getTipo());
        coluna_chave.setCellValueFactory(cellData -> cellData.getValue().getChave());
        coluna_usuario.setCellValueFactory(cellData -> cellData.getValue().getUsuario());
        
        tabela_chaves.setItems(ConteudoTabelaChaves);
        
        listaChaves();
    }    
    public void removerChave(){
        String usuario = tabela_chaves.getSelectionModel().getSelectedItem().getUsuario().getValue();
        String chave = tabela_chaves.getSelectionModel().getSelectedItem().getChave().getValue();
        
        Sql excluir = new Sql();
        ArrayList values = new ArrayList();
        values.add(chave);
        excluir.executeQuery("DELETE FROM `acesso` WHERE chaveAcesso = ?", values);
        values.clear();values.add(usuario);
        excluir.executeQuery("DELETE FROM `admin` WHERE usuario = ?", values);
        
        listaChaves();
    }
    public void adicionarChave(){
        Sql adicionar = new Sql();
        ArrayList values = new ArrayList();
        values.add(gerarChave());
        
        if(values.get(0) != null){
            if(radio_grupo.getSelectedToggle().isSelected()){
                adicionar.executeQuery("INSERT INTO `acesso`(`chaveAcesso`, `id`) VALUES (?,'0')", values);  
            }
        }
        
        //
        
    }
    public String gerarChave(){
        String tipo = null;
        if(radio_administrador.isSelected()){
            tipo = "1";
        }else if(radio_usuario.isSelected()){
            tipo = "2";
        }
        Random gerador = new Random();
        int numero = gerador.nextInt((99999 - 10000) + 1) + 10000; 
        Sql buscar = new Sql();
        ArrayList values = new ArrayList();
        values.add(tipo+numero);
        ArrayList r = new ArrayList();
        String[] retorno = {"chaveAcesso"};
        r = buscar.executeQuery("SELECT `chaveAcesso` FROM `acesso` WHERE chaveAcesso  = ?", values, retorno);
        
        if(r.isEmpty()){
            return tipo+numero+"";
        }
        return null;
        
    }   
    
    public void listaChaves(){
        ConteudoTabelaChaves.clear();
        String t = "";
        //SELECT `acesso`.`id`,`acesso`.`chaveAcesso`, `admin`.`usuario` FROM `acesso`,`admin` WHERE `acesso`.`id` = `admin`.`id_admin` OR `acesso`.`id` = "0"
        Sql buscar = new Sql();
        String[] retorno = {"id","chaveAcesso","usuario"};
        String[] retorno2 = {"chaveAcesso"};
        ArrayList values = new ArrayList();
        ArrayList r = new ArrayList();
        r = buscar.executeQuery("SELECT `chaveAcesso` FROM `acesso` WHERE id = '0'", values, retorno2);
        for (int i = 0; i < r.size(); i++) {
            String a = r.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");   
            if(tokens[0].trim().substring(0, 1).equals("1")){
                t = "Administrador";
            }else if(tokens[0].trim().substring(0, 1).equals("2")){
                t = "Usuario";
            }else{
                t = "N";
            }
            ConteudoTabelaChaves.add(new UsuarioBean(t,tokens[0].trim(),"-"));
        }
        
        r.clear();
        r = buscar.executeQuery("SELECT `acesso`.`id`,`acesso`.`chaveAcesso`, `admin`.`usuario` FROM `acesso`,`admin` WHERE `acesso`.`id` = `admin`.`id_admin`", values, retorno);
        
        for (int i = 0; i < r.size(); i++) {
            String a = r.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");   
            if(tokens[1].trim().substring(0, 1).equals("1")){
                t = "Administrador";
            }else if(tokens[1].trim().substring(0, 1).equals("2")){
                t = "Usuario";
            }else{
                t = "N";
            }
            ConteudoTabelaChaves.add(new UsuarioBean(t,tokens[1].trim(),tokens[2].trim()));
        }   
    }
}
