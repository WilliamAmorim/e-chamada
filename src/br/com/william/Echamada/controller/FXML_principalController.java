/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.controller;

import br.com.william.Echamada.bean.AlunosBean;
import br.com.william.Echamada.sql.Sql;
import br.com.william.Echamada.util.MetodosSql;
import br.com.william.Echamada.util.Util;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class FXML_principalController implements Initializable {
    @FXML
    private ProgressBar progress;
    //Tabela_Alunos*************************************************************
    
    @FXML
    private TableView<AlunosBean> tabela_aluno;

    @FXML
    private TableColumn<AlunosBean, String> coluna_aluno;

    @FXML
    private TableColumn<AlunosBean, String> coluna_nascimento;

    @FXML
    private TableColumn<AlunosBean, String> coluna_sexo;

    @FXML
    private TableColumn<AlunosBean, String> coluna_endereco;

    @FXML
    private TableColumn<AlunosBean, String> coluna_pai;

    @FXML
    private TableColumn<AlunosBean, String> coluna_mae;

    @FXML
    private TableColumn<AlunosBean, String> coluna_telefone;

    @FXML
    private TableColumn<AlunosBean, String> coluna_serie;

    @FXML
    private TableColumn<AlunosBean, String> coluna_turma;

    @FXML
    private TableColumn<AlunosBean, String> coluna_turno;
    
    private ObservableList<AlunosBean> ConteudoTabelaAluno = FXCollections.observableArrayList();
    

    //**************************************************************************
    
    //Cadastrar e Editar aluno**************************************************
    @FXML
    private Button BT_cadastrarAluno;
    @FXML
    private TextField txt_codigoAluno;

    @FXML
    private TextField txt_nomeAluno;

    @FXML
    private TextField txt_senhaAluno;

    @FXML
    private DatePicker date_dataNascimentoAluno;

    @FXML
    private ComboBox combo_sexoAluno;

    @FXML
    private TextField txt_enderecoAluno;

    @FXML
    private TextField nome_pai;

    @FXML
    private TextField txt_nomeMae;

    @FXML
    private TextField txt_telefone;

    @FXML
    private ComboBox combo_serie;

    @FXML
    private ComboBox combo_turma;

    @FXML
    private ComboBox combo_turno;
    
    @FXML
    void BT_cadastrarAluno(ActionEvent event) {   
        progress.setProgress(50);
        cadastrarAluno();
        progress.setProgress(0);       
    }
    public void cadastrarAluno(){
        new Thread(){        
            public void run(){
                MetodosSql execute = new MetodosSql(); 
                String operacao;
                switch(tabela_aluno.getSelectionModel().getSelectedIndex()){
                    case -1:operacao = "cadastrar";break;
                    default:operacao = "update";
                }        
                execute.CadastrarAluno(operacao,"456987", txt_nomeAluno.getText(),txt_senhaAluno.getText(),date_dataNascimentoAluno.getValue(), combo_sexoAluno.getValue()+"", txt_enderecoAluno.getText(), nome_pai.getText(), txt_nomeMae.getText(), txt_telefone.getText(), combo_serie.getValue()+"", combo_turma.getValue()+"", combo_turno.getValue()+"",ConteudoTabelaAluno.get(i).getId().getValue());                
                listaTabelaAlunos(); 
                BT_cadastrarAluno.setText("Cadastrar");
            }
        }.start();
    }
    @FXML
    void clicked_tabelaAluno(MouseEvent event) {
        BT_cadastrarAluno.setText("Alterar");
        pegarDadosTabelaAlunos();
    }
    @FXML
    void tabPane_clicked(MouseEvent event) {
        tabela_aluno.getSelectionModel().clearSelection();
        BT_cadastrarAluno.setText("Cadastrar");
        sairDadosTabelaAlunos();
        
        System.out.println("Selected:"+tabela_aluno.getSelectionModel().getSelectedIndex());
    }
    public int i;
    public void pegarDadosTabelaAlunos(){
        i = tabela_aluno.getSelectionModel().getSelectedIndex();
        txt_nomeAluno.setText(ConteudoTabelaAluno.get(i).getAluno().getValue());
        txt_senhaAluno.setText(ConteudoTabelaAluno.get(i).getAluno().getValue());                        
        date_dataNascimentoAluno.setValue(Util.LOCAL_DATE(ConteudoTabelaAluno.get(i).getData_nascimento().getValue().trim()));        
        combo_sexoAluno.setValue(ConteudoTabelaAluno.get(i).getSexo().getValue());
        txt_enderecoAluno.setText(ConteudoTabelaAluno.get(i).getEndereco().getValue());
        nome_pai.setText(ConteudoTabelaAluno.get(i).getNome_pai().getValue());
        txt_nomeMae.setText(ConteudoTabelaAluno.get(i).getNome_mae().getValue());
        txt_telefone.setText(ConteudoTabelaAluno.get(i).getTelefone().getValue());
        combo_serie.setValue(ConteudoTabelaAluno.get(i).getSerie().getValue());
        combo_turma.setValue(ConteudoTabelaAluno.get(i).getTurma().getValue());
        combo_turno.setValue(ConteudoTabelaAluno.get(i).getTurno().getValue());
        System.out.println("index "+i);
    }
    public void sairDadosTabelaAlunos(){
        txt_nomeAluno.setText("");
        txt_senhaAluno.setText("");                        
        date_dataNascimentoAluno.setValue(null);        
        combo_sexoAluno.setValue("");
        txt_enderecoAluno.setText("");
        nome_pai.setText("");
        txt_nomeMae.setText("");
        txt_telefone.setText("");
        combo_serie.setValue(null);
        combo_turma.setValue(null);
        combo_turno.setValue(null);
    }  
    public void listaTabelaAlunos(){
        ConteudoTabelaAluno.clear();
        Sql novo = new Sql();
        ArrayList valores = new ArrayList();
        ArrayList resultadoQuery = new ArrayList();
        String[] resultado = {"id_aluno","nome_aluno", "data_nascimento", "sexo", "endereco", "nome_pai","nome_mae","telefone_responsavel","serie","turma","turno"};       
        resultadoQuery = novo.executeQuery("SELECT * FROM `alunos` WHERE 1", valores, resultado);
        for (int i = 0; i <= resultadoQuery.size() - 1; i++) {
            String a = resultadoQuery.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");            
            ConteudoTabelaAluno.add(new AlunosBean(tokens[0].trim(),tokens[1].trim(),tokens[2].trim(),tokens[3].trim(), tokens[4].trim(),tokens[5].trim(),tokens[6].trim(),tokens[7].trim(),tokens[8].trim(),tokens[9].trim(),tokens[10].trim()));
        }
    }
    //**************************************************************************
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializando a tabela alunos.
        coluna_aluno.setCellValueFactory(cellData -> cellData.getValue().getAluno());
        coluna_nascimento.setCellValueFactory(cellData -> cellData.getValue().getData_nascimento());
        coluna_sexo.setCellValueFactory(cellData -> cellData.getValue().getSexo());
        coluna_endereco.setCellValueFactory(cellData -> cellData.getValue().getEndereco());
        coluna_pai.setCellValueFactory(cellData -> cellData.getValue().getNome_pai());
        coluna_mae.setCellValueFactory(cellData -> cellData.getValue().getNome_mae());
        coluna_telefone.setCellValueFactory(cellData -> cellData.getValue().getTelefone());
        coluna_serie.setCellValueFactory(cellData -> cellData.getValue().getSerie());
        coluna_turma.setCellValueFactory(cellData -> cellData.getValue().getTurma());
        coluna_turno.setCellValueFactory(cellData -> cellData.getValue().getTurno());
        
        
        combo_sexoAluno.getItems().add("M");
        combo_sexoAluno.getItems().add("F");
        
        combo_serie.getItems().add("1");combo_serie.getItems().add("2");combo_serie.getItems().add("3");
        combo_turma.getItems().add("A");combo_turma.getItems().add("B");combo_turma.getItems().add("C");
        
        combo_turno.getItems().add("Matutino");combo_turno.getItems().add("Vespertino");combo_turno.getItems().add("Noturno");
//        tabela_aluno.setRowFactory(tv -> new TableRow<AlunosBean>() {
//            public void updateItem(AlunosBean item, boolean empty) {
//                super.updateItem(item, empty) ;
//
//
//                    if(item == null){
//                        setStyle("");
//                    }else if(item.getSexo().getValue().equals("M")){
//                        setStyle("-fx-background-color:#43CD80;");
//                    }else{
//                         setStyle("");
//                    }
//            }
//        });
       
        tabela_aluno.setItems(ConteudoTabelaAluno);
        
        listaTabelaAlunos();
    }    
    
}
