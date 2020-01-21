/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.controller;

import br.com.william.Echamada.sql.Sql;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class FXML_criarSerieController implements Initializable {
    //Componentes***************************************************************
    @FXML
    private TextField txt_turma;

    @FXML
    private TextField txt_serie;

    @FXML
    private ListView list_series;

    @FXML
    private ListView list_turmas;

    @FXML
    private CheckBox check_matutino;

    @FXML
    private CheckBox check_vespertino;

    @FXML
    private CheckBox check_noturno;

    @FXML
    void BT_adicionar(ActionEvent event) {
        System.out.println("Botão presionado");
        cadastrarSerie();
    }

    @FXML
    void BT_remover(ActionEvent event) {
        String serie = list_series.getSelectionModel().getSelectedItem().toString();
        String turma = list_turmas.getSelectionModel().getSelectedItem().toString(); 
        if(serie != null && turma == null){
            deletar(serie,turma,true);
            listaSerie();
        }else if(turma != null){
            deletar(serie,turma,false);
            listaTurma(serie);
        }
        serie = null;
        turma = null;
    }
    @FXML
    void desfazerClick(MouseEvent event) {
        list_series.getSelectionModel().clearSelection();
        list_turmas.getSelectionModel().clearSelection();
    }

    @FXML
    void check_matutino(ActionEvent event) {
        if(check_matutino.isSelected()){
             updateTurno("Matutino",true);
        }else{
            updateTurno("Matutino",false);
        }
    }

    @FXML
    void check_noturno(ActionEvent event) {
        if(check_noturno.isSelected()){
            updateTurno("Noturno",true);
        }else{
            updateTurno("Noturno",false);
        }
    }

    @FXML
    void check_vespertino(ActionEvent event) {
        if(check_vespertino.isSelected()){            
            updateTurno("Vespertino",true);
        }else{            
            updateTurno("Vespertino",false);
        }
    }
    
        @FXML
    void listSerie_click(MouseEvent event) {
        String serie = list_series.getSelectionModel().getSelectedItem().toString();
        listaTurma(serie);
    }
    //**************************************************************************
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listaSerie();
        verificarTurno();
    }   
    public void deletar(String s,String t,boolean operacao){
        Sql excluir = new Sql();
        ArrayList values = new ArrayList();
        ArrayList r = new ArrayList();
        
        String[] retorno = {"id_serie"};
        if(operacao){
            values.add(s);
            excluir.executeQuery("DELETE FROM `serie` WHERE serie = ?", values);
        }else{
            values.add(t);
            values.add(s);
            excluir.executeQuery("DELETE FROM `turma` WHERE turma = ? AND id_serie = ?", values);
        }
    }
    public void cadastrarSerie(){
        Sql cadastrar = new Sql();
        ArrayList values = new ArrayList();
        ArrayList r = new ArrayList();
        values.add(txt_serie.getText());
        String[] retorno = {"id_serie"};
        cadastrar.executeQuery("INSERT INTO `serie`(`serie`) VALUES (?)", values);
        values.clear();
        //r = cadastrar.executeQuery("SELECT * FROM serie ORDER BY id_serie DESC LIMIT 1", values, retorno);
        //if(!r.isEmpty()){
        //String a = r.get(0).toString();
        //String b = a.replace("[", "").replace("]", "");
        //String[] tokens = b.split(",");
            cadastrarTurma(txt_serie.getText());
        //}
    }
    public void updateTurno(String turno,boolean operacao){
        Sql cadastrar = new Sql();
        ArrayList values = new ArrayList();           
        if(operacao){
            values.add("1");
        }else{
            values.add("0");
        }
        values.add(turno);
        
        cadastrar.executeQuery("UPDATE `turno` SET `status`=? WHERE turno = ?", values);
    }
    public void cadastrarTurma(String id){
        Sql cadastrar = new Sql();
        ArrayList values = new ArrayList();
        String[] turmas = txt_turma.getText().split(",");            
            for (int i = 0; i < turmas.length; i++) {
                values.add(id);
                values.add(turmas[i]);
                cadastrar.executeQuery("INSERT INTO `turma`(`id_serie`, `turma`) VALUES (?,?)", values);
                values.clear();
            }
            
         listaSerie();
    }
    ObservableList<String> seriesList = FXCollections.observableArrayList();
    public void listaSerie(){
        seriesList.clear();
        Sql cadastrar = new Sql();
        ArrayList values = new ArrayList();
        ArrayList r = new ArrayList();
        String[] retorno = {"serie"};
        
        r = cadastrar.executeQuery("SELECT * FROM `serie` WHERE 1", values, retorno);
        
         for (int i = 0; i < r.size(); i++) {
            String a = r.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");   
            seriesList.add(tokens[0]);
         }   
        list_series.setItems(seriesList);
        
    }
    
    ObservableList<String> turmaList = FXCollections.observableArrayList();
    public void listaTurma(String serie){
        turmaList.clear();
        Sql cadastrar = new Sql();
        ArrayList values = new ArrayList();
        ArrayList r = new ArrayList();
        String[] retorno = {"turma"};
        values.add(serie);
        r = cadastrar.executeQuery("SELECT * FROM `turma` WHERE id_serie = ?", values, retorno);
        
        for (int i = 0; i < r.size(); i++) {
            String a = r.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");
            turmaList.add(tokens[0]);
        }   
        list_turmas.setItems(turmaList);
        
    }
    public void verificarTurno(){    
        Sql buscar = new Sql();
        String[] retorno = {"status","turno"};
        ArrayList values = new ArrayList();
        values.add("1");
        ArrayList retur  = new ArrayList();
        retur = buscar.executeQuery("SELECT * FROM `turno` WHERE status = ?", values, retorno);
        for (int i = 0; i < retur.size(); i++) {
            String a = retur.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");
            System.out.println("executou");
            float c = Float.parseFloat(tokens[0].trim());
            
            
            if(tokens[1].trim().equals("Matutino")){
                if(tokens[0].trim().equals("1")){
                    check_matutino.setSelected(true);
                }
            }else if(tokens[1].trim().equals("Vespertino")){
                if(tokens[0].trim().equals("1")){
                    check_vespertino.setSelected(true);
                }
            }else if(tokens[1].trim().equals("Noturno")){
                if(tokens[0].trim().equals("1")){
                    check_noturno.setSelected(true);
                }
            }
                
                
            }
        }
//        for (int i = 0; i <= retur.size() - 1; i++) {
//            String a = retur.get(i).toString();
//            String b = a.replace("[", "").replace("]", "");
//            String[] tokens = b.split(",");                 
//            System.out.println("turnos:"+tokens[0]);
//            if(tokens[i].equals("1")){
//                switch(i){
//                    case 0:check_matutino.setSelected(true);break;
//                    case 1:check_vespertino.setSelected(true);break;
//                    case 2:check_noturno.setSelected(true);break;
//                }
//            }    
//            System.out.println("executou");
//        }

    }
    
    

