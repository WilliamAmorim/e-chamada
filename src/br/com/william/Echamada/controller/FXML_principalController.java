/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.controller;

import br.com.william.Echamada.bean.AlunosBean;
import br.com.william.Echamada.bean.HistoricoBean;
import br.com.william.Echamada.bean.LiberadosBean;
import br.com.william.Echamada.bean.TabelaHistoricoBean;
import br.com.william.Echamada.localizacao.ListaHistorico;
import br.com.william.Echamada.sql.Sql;
import br.com.william.Echamada.util.MetodosSql;
import br.com.william.Echamada.util.Util;
import br.com.william.Echamada.util.UtilLiberarAlunos;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
//import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
    
    //Tabela Historico**********************************************************
    @FXML
    private TableView<TabelaHistoricoBean> tabela_historico;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> colunaNomeHistorico;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> coluna_dataHistorico;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> coluna_aula01;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> coluna_aula02;
    //private TableColumn s = coluna_aula02;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> coluna_aula03;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> coluna_aula04;

    @FXML
    private TableColumn<TabelaHistoricoBean, String> coluna_aula05;
    
    private ObservableList<TabelaHistoricoBean> ConteudoTabelaHistorico = FXCollections.observableArrayList();
     
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
        try{
            atualizarTabelas();
            progress.setProgress(50);
            cadastrarAluno();
            progress.setProgress(0);       
        }catch(Exception ex){
            progress.setProgress(0);       
        }
    }
    
    public void cadastrarAluno(){
       // new Thread(){        
         //   public void run(){
                MetodosSql execute = new MetodosSql(); 
                String operacao;
                switch(tabela_aluno.getSelectionModel().getSelectedIndex()){
                    case -1:operacao = "cadastrar";System.out.println("Cadastrando");break;
                    default:operacao = "update";System.out.println("Update");break;
                }
                String Id = "";
                if(ConteudoTabelaAluno.size() > 0){
                    Id = ConteudoTabelaAluno.get(i).getId().getValue();
                }
                execute.CadastrarAluno(operacao,"456987", txt_nomeAluno.getText(),txt_senhaAluno.getText(),date_dataNascimentoAluno.getValue(), combo_sexoAluno.getValue()+"", txt_enderecoAluno.getText(), nome_pai.getText(), txt_nomeMae.getText(), txt_telefone.getText(), combo_serie.getValue()+"", combo_turma.getValue()+"", combo_turno.getValue()+"",Id);                //ConteudoTabelaAluno.get(i).getId().getValue()
                System.err.println("Cadastrado");
                listaTabelaAlunos(); 
                //BT_cadastrarAluno.setText("Cadastrar");
           // }
        //}.start();
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
        ConteudoTabelaHistorico.clear();
        ConteudoTabelaAluno.clear();
        MetodosSql m = new MetodosSql();
        Sql novo = new Sql();
        ArrayList valores = new ArrayList();
        ArrayList resultadoQuery = new ArrayList();
        String[] resultado = {"id_aluno","nome_aluno", "data_nascimento", "sexo", "endereco", "nome_pai","nome_mae","telefone_responsavel","serie","turma","turno","status"};       
        resultadoQuery = novo.executeQuery(m.criarQueryAluno((String)combo_serieFiltro.getValue(), (String)combo_turmaFiltro.getValue(), (String)combo_turnoFiltro.getValue(), (String)combo_statusFiltro.getValue(), (String)combo_generoFiltro.getValue()), valores, resultado);
        for (int i = 0; i <= resultadoQuery.size() - 1; i++) {
            String a = resultadoQuery.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");            
            ConteudoTabelaAluno.add(new AlunosBean("alsente",tokens[0].trim(),tokens[1].trim(),tokens[2].trim(),tokens[3].trim(), tokens[4].trim(),tokens[5].trim(),tokens[6].trim(),tokens[7].trim(),tokens[8].trim(),tokens[9].trim(),tokens[10].trim()));
            listaHistorico(tokens[1].trim(),(int)Float.parseFloat(tokens[0]),tokens[10].trim() , tokens[11].trim());
        }
    }
    //**************************************************************************
    
    //Tabela Liberados**********************************************************
    @FXML
    private TableView<LiberadosBean> tabela_Liberados;

    @FXML
    private TableColumn<LiberadosBean, String> coluna_parametro;

    @FXML
    private TableColumn<LiberadosBean, String> coluna_prazo;

    @FXML
    private TableColumn<LiberadosBean, String> coluna_dataLiberados;

    @FXML
    private TableColumn<LiberadosBean, String> coluna_mensagem;
    
    private final ObservableList<LiberadosBean> ConteudoTabelaLiberados = FXCollections.observableArrayList();
    
    public void listaTabelaLiberados(){
        ConteudoTabelaLiberados.clear();
        Sql novo = new Sql();
        ArrayList valores = new ArrayList();
        ArrayList resultadoQuery = new ArrayList();
        String[] resultado = {"status","parametro","prazo", "hora", "mensagem"};       
        resultadoQuery = novo.executeQuery("SELECT * FROM `liberados` WHERE 1", valores, resultado);
        for (int i = 0; i <= resultadoQuery.size() - 1; i++) {
            String a = resultadoQuery.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");     
            System.out.println("Status liberados:"+tokens[0]);
            ConteudoTabelaLiberados.add(new LiberadosBean(tokens[0].trim(),tokens[1].trim(),tokens[2].trim(),tokens[3].trim(),tokens[4].trim()));
            Util.verificarData(tokens[2], Util.time());
        }
    }
    //**************************************************************************
    
    //Liberar Alunos************************************************************
    @FXML
    private ComboBox combo_serieLiberados;

    @FXML
    private ComboBox combo_turmaLiberados;

    @FXML
    private ComboBox combo_turnoLiberados;

    @FXML
    private TextField txt_alunoLiberados;

    @FXML
    private DatePicker date_periodoLiberados;

    @FXML
    private TextArea txt_mensagem;

    @FXML
    private Button BT_liberar;
    private TableColumn coluna;

    @FXML
    private CheckBox check_todosLiberados;
    
    
    
    @FXML
    void BT_liberar(ActionEvent event) {        
        UtilLiberarAlunos li = new UtilLiberarAlunos();
        li.liberarAlunos((String)combo_serieLiberados.getValue(), (String)combo_turmaLiberados.getValue(), (String)combo_turnoLiberados.getValue(), txt_alunoLiberados.getText(),date_periodoLiberados.getValue(), txt_mensagem.getText(),check_todosLiberados.isSelected());
        listaTabelaLiberados();
    }
    //***************************************************************************
    
     @FXML
    private ComboBox combo_serieFiltro;

    @FXML
    private ComboBox combo_turmaFiltro;

    @FXML
    private ComboBox combo_turnoFiltro;

    @FXML
    private ComboBox combo_statusFiltro;

    @FXML
    private ComboBox combo_generoFiltro;
    
     @FXML
    void BT_filtrarAluno(ActionEvent event) {
        listaTabelaAlunos();
    }
      @FXML
    void BT_limparFiltro(ActionEvent event) {
        combo_serieFiltro.setValue(null);
        combo_turmaFiltro.setValue(null);
        combo_turnoFiltro.setValue(null);
        combo_statusFiltro.setValue(null);
        combo_generoFiltro.setValue(null);
        listaTabelaAlunos();
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          // listaHistorico();

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
        //**********************************************************************************
        colunaNomeHistorico.setCellValueFactory(cellData -> cellData.getValue().getAluno());
        coluna_dataHistorico.setCellValueFactory(cellData -> cellData.getValue().getData());
        coluna_aula01.setCellValueFactory(cellData -> cellData.getValue().getAula01());
        coluna_aula02.setCellValueFactory(cellData -> cellData.getValue().getAula02());
        coluna_aula03.setCellValueFactory(cellData -> cellData.getValue().getAula03());
        coluna_aula04.setCellValueFactory(cellData -> cellData.getValue().getAula04());
        coluna_aula05.setCellValueFactory(cellData -> cellData.getValue().getAula05());
        //************************************************************************************
        combo_sexoAluno.getItems().add("M");
        combo_sexoAluno.getItems().add("F");
        
        combo_serie.getItems().add("1");combo_serie.getItems().add("2");combo_serie.getItems().add("3");
        combo_turma.getItems().add("A");combo_turma.getItems().add("B");combo_turma.getItems().add("C");
        
        combo_turno.getItems().add("Matutino");combo_turno.getItems().add("Vespertino");combo_turno.getItems().add("Noturno");
        
        
        combo_serieLiberados.getItems().add("");
        combo_serieLiberados.getItems().add("1");combo_serieLiberados.getItems().add("2");combo_serieLiberados.getItems().add("3");
        combo_turmaLiberados.getItems().add("A");combo_turmaLiberados.getItems().add("B");combo_turmaLiberados.getItems().add("C");
        combo_turmaLiberados.getItems().add("");
        combo_turnoLiberados.getItems().add("Matutino");combo_turnoLiberados.getItems().add("Vespertino");combo_turnoLiberados.getItems().add("Noturno");
        combo_turnoLiberados.getItems().add("");
        
        tabela_aluno.setRowFactory(tv -> new TableRow<AlunosBean>() {
            public void updateItem(AlunosBean item, boolean empty) {
                super.updateItem(item, empty) ;
                if(item == null){
                    setStyle("");
                }else if(item.getStatus().equals("presente")){                    
                    setStyle("-fx-background-color:#43CD80;");
                }else{
                    setStyle("-fx-background-color:red;");
                }
            }
        });
        
        tabela_Liberados.setRowFactory(tv -> new TableRow<LiberadosBean>() {
            public void updateItem(LiberadosBean item, boolean empty) {
                super.updateItem(item, empty) ;
                if(item == null){
                    setStyle("");
                }else if(item.getStatus().getValue().equals("1")){                    
                    setStyle("-fx-background-color:#43CD80;");
                }else if(item.getStatus().getValue().equals("0")){
                    setStyle("-fx-background-color:red;");
                }else{
                    setStyle("");
                }
            }
        });
        
        for (int h = 1; h <= 5; h++) {
            
            switch(h){
                case 1:coluna = coluna_aula01;break;
                case 2:coluna = coluna_aula02;break;
                case 3:coluna = coluna_aula03;break;
                case 4:coluna = coluna_aula04;break;
                case 5:coluna = coluna_aula05;break;
            }
            
                coluna.setCellFactory(column -> {
    return new TableCell<TabelaHistoricoBean, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                    setText(null);
                    setStyle("");
            } else {
                    // Format date.
                    setText(item);
                    
                    // Style all dates in March with a different color.
                    if (item.equals("Alsente")) {
                            setTextFill(Color.rgb(255, 64, 64));
                            //setStyle("-fx-background-color: yellow");
                    } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                    }
            }
        }
    };
});
        }
        
   
       
        tabela_aluno.setItems(ConteudoTabelaAluno);  
        tabela_historico.setItems(ConteudoTabelaHistorico);  
        
        listaTabelaAlunos();
        
        coluna_parametro.setCellValueFactory(cellData -> cellData.getValue().getParametro());
        coluna_prazo.setCellValueFactory(cellData -> cellData.getValue().getPrazo());
        coluna_dataLiberados.setCellValueFactory(cellData -> cellData.getValue().getHora());
        coluna_mensagem.setCellValueFactory(cellData -> cellData.getValue().getMensagem());
        
        tabela_Liberados.setItems(ConteudoTabelaLiberados);
        listaTabelaLiberados();
        
        
        combo_generoFiltro.getItems().add("M");
        combo_generoFiltro.getItems().add("F");
        
        combo_statusFiltro.getItems().add("Ativo");
        combo_statusFiltro.getItems().add("Inativo");
        
        combo_serieFiltro.getItems().add("1");combo_serieFiltro.getItems().add("2");combo_serieFiltro.getItems().add("3");
        combo_turmaFiltro.getItems().add("A");combo_turmaFiltro.getItems().add("B");combo_turmaFiltro.getItems().add("C");
        
        combo_turnoFiltro.getItems().add("Matutino");combo_turnoFiltro.getItems().add("Vespertino");combo_turnoFiltro.getItems().add("Noturno");
        AcompanharLocalizacao(true);
    } 
    
    
    public void listaHistorico(String nome,int id,String turno,String status){
        ListaHistorico his = new ListaHistorico();
        ArrayList retorno = new ArrayList();
        retorno = his.lista(nome, id, "",turno,status);
        System.out.println("ID:"+id);
       // for (int j = 0; j < retorno.size(); j++) {
       //System.out.println(retorno.get(0)+" - "+retorno.get(1)+" - "+retorno.get(2)+" - "+retorno.get(3)+" - "+retorno.get(4)+" - "+retorno.get(5)+" - "+retorno.get(6));
        //}
        ConteudoTabelaHistorico.add(new TabelaHistoricoBean(retorno.get(0).toString(),retorno.get(1).toString(),retorno.get(2).toString(),retorno.get(3).toString(),retorno.get(4).toString(),retorno.get(5).toString(),retorno.get(6).toString()));
        
    }
    
    
    public void AcompanharLocalizacao(boolean continuar){
        Timer time = new Timer();
        final long SEGUNDOS = (5000 * 5);
        try{
                if(continuar){
                    TimerTask tarefa = new TimerTask(){
                    @Override
                    public void run(){  
                        System.out.println("********************\nExecutando Verificação\n********************");
                        for (int j = 0; j <= ConteudoTabelaAluno.size(); j++) {
                            
                            int id = (int)Float.parseFloat(ConteudoTabelaAluno.get(j).getId().getValue());
                            ListaHistorico lo = new ListaHistorico();          
                            HistoricoBean objeto = new HistoricoBean();
                            objeto = lo.PegarLocalizacao(id,ListaHistorico.time());
                            boolean result =  false;                                                          
                            if(objeto.getLatitude() != null){                               
                                result =  ListaHistorico.localizacao(Float.parseFloat(objeto.getLatitude()),Float.parseFloat(objeto.getLongitude()));                                                        
                            }
                            
                            if(result){
                                ConteudoTabelaAluno.get(id).getStatus().setValue("presente");                                
                            }else{
                                ConteudoTabelaAluno.get(id).getStatus().setValue("alsente");
                            }
                        }  
                    }                    
                };
                 
                 
                time.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);
                     
                
                }
                   
        }catch(IndexOutOfBoundsException e){
            System.out.println("############################################Ocorreu um erro:"+e);
        }

    }
    
    public void atualizarTabelas(){
        //atualizando todas as tabelas
        try{
            listaTabelaAlunos();
            listaTabelaLiberados();    
        }catch(Exception ex){
            System.err.println("********************\nErro ao lista Tabela:\n"+ex+"\n**********************");
        }
        
    }
}
        
        
    

    

