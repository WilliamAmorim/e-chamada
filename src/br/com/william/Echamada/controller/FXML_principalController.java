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
import br.com.william.Echamada.util.MapUtil;
import br.com.william.Echamada.util.MetodosSql;
import br.com.william.Echamada.util.Util;
import br.com.william.Echamada.util.UtilLiberarAlunos;
import br.com.william.Echamada.view.AbrirCriarSerie;
import br.com.william.Echamada.view.AbrirTelas;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author willi
 */
public class FXML_principalController implements Initializable {
    //Estatisticas**********************************************************
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    @FXML
    private ToggleButton toggle_statisticaPresentes;

    @FXML
    private ToggleGroup estatistica;

    @FXML
    private ToggleButton toggle_EstatisticaAlsente;

 
    @FXML
    private ComboBox combo_estatisticaSerie;

    @FXML
    private ComboBox combo_estatisticaTurma;

    @FXML
    private ComboBox combo_estatisticaTurno;

   

    @FXML
    private DatePicker date_estatisticaFim;
    @FXML
    LineChart<String,Number> grafico_estatistica;
    int adicionados = 0;
    @FXML
    void BT_adicionarEstatistica(ActionEvent event) {
        //if(combo_estatisticaSerie.getValue() != null  combo_estatisticaTurma.getValue() != null  && combo_estatisticaTurno.getValue() != null){//
        if(combo_estatisticaTurma.getValue() != null  && combo_estatisticaSerie.getValue() != null){
            adicionados++;
            XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
            serie.setName((String)combo_estatisticaSerie.getValue()+(String)combo_estatisticaTurma.getValue());
            grafico_estatistica.getData().add(serie);
        }else if(combo_estatisticaSerie.getValue() != null){
            adicionados++;
            XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
            serie.setName((String)combo_estatisticaSerie.getValue());
            grafico_estatistica.getData().add(serie);
        }else if(combo_estatisticaTurno.getValue() != null){
            adicionados++;
            XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
            serie.setName((String)combo_estatisticaTurno.getValue());
            serie.getData().add(new XYChart.Data<String, Number>("10/05/202"+adicionados, 200));
            grafico_estatistica.getData().add(serie);
            
        }                
    }
    @FXML
    void toggle_estatisticaAlsente(ActionEvent event) {
        System.out.println("Serie:"+grafico_estatistica.getData().get(0).getName());
    }

    @FXML
    void toggle_estatisticaLiberados(ActionEvent event) {

    }

    @FXML
    void toggle_statisticaPresentes(ActionEvent event) {

    }
    
    public void setStatisticas(){
        XYChart.Series<String, Number> serie1 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> serie2 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> serie3 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> serie4 = new XYChart.Series<String, Number>();
        serie4.getData().add(new XYChart.Data<String, Number>("jan", 200));
        serie4.getData().add(new XYChart.Data<String, Number>("Feb", 500));
        serie3.getData().add(new XYChart.Data<String, Number>("Mar", 300));
        serie3.getData().add(new XYChart.Data<String, Number>("Apr", 600));
        serie1.setName("Month Pay");
        serie2.setName("3C");
        serie3.setName("3 A");
        serie4.setName("Matutino");        
        grafico_estatistica.getData().add(serie1);
        grafico_estatistica.getData().add(serie2);
        grafico_estatistica.getData().add(serie3);
        grafico_estatistica.getData().add(serie4);

        
        
    }
    
   



  
    //**********************************************************************
    @FXML
    private Label label_statusProgress;
     @FXML
    private ProgressIndicator progress_status;
    @FXML
    private DatePicker date_dataHora;//label_statusProgress
    @FXML
    private ProgressBar progress; 
    
    public void progress(String msg){
        new Thread(){
            public void run(){  
                //progress_status.setVisible(fluxo);
               // label_statusProgress.setText(msg);
            }
         }.start();
    }
    @FXML
    private ProgressIndicator progress_hora;   
    @FXML
    private ListView list_alunos;//date_dataHora
    ObservableList<String> alunosListados = FXCollections.observableArrayList();
    
    @FXML
    private ListView list_horario;
    ObservableList<String> listaHorario = FXCollections.observableArrayList();
    
    @FXML
    private WebView webView_mapa;
    
     @FXML
    private ToggleGroup Aulas;
    //Map***********************************************************************progress_hora
     @FXML
    void action_list_alunos(MouseEvent event) {
        try{
            list_horario.setItems(listaHorario);
            Aulas.getSelectedToggle().setSelected(false);        
        }catch(Exception ex){
            
        }
    }
    
     @FXML
    void action_list_horario(MouseEvent event) {
        int i = list_alunos.getSelectionModel().getSelectedIndex();
        if(i != -1){
            try{
                String hora = list_horario.getSelectionModel().getSelectedItem().toString();
                if(!hora.equals(" -")){
                    String id =  ConteudoTabelaAluno.get(i).getId().getValue();                    
                    if(date_dataHora.getValue() != null){
                        System.out.println("Data:"+date_dataHora.getValue().toString());
                        webView_mapa.getEngine().load(MapUtil.mostrarNoMapa(id, hora,Util.converterData(date_dataHora.getValue().toString(),"normal")));
                    }else{
                        webView_mapa.getEngine().load(MapUtil.mostrarNoMapa(id, hora,Util.time()));
                    }
                }
            }catch(Exception ex){
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                dialogoInfo.setHeaderText("Ocorreu um erro");
                dialogoInfo.setContentText(ex+"");
                dialogoInfo.showAndWait();
            }
        }
    }
    public void pegarHorario(int matutino,int vespertino,int noturno){
        int i = list_alunos.getSelectionModel().getSelectedIndex();
        if(i != -1){
            try{
                String id =  ConteudoTabelaAluno.get(i).getId().getValue();
                String turno = ConteudoTabelaAluno.get(i).getTurno().getValue();
                switch(turno){
                   case "Matutino":list_horario.setItems(MapUtil.listaHorario(matutino));break;
                   case "Vespertino":list_horario.setItems(MapUtil.listaHorario(vespertino));break;
                   case "Noturno":list_horario.setItems(MapUtil.listaHorario(noturno));break;
                }
               System.out.println("index mostrar:"+i);                                   
               verificar(id);               
            }catch(Exception ex){
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                dialogoInfo.setHeaderText("Ocorreu um erro");
                dialogoInfo.setContentText(ex+"");
                dialogoInfo.showAndWait();
            }
        }
    }
    public void verificar(String id){        
            new Thread(){
                public void run(){                    
                    try{
                        progress_hora.setVisible(true); 
                        if(date_dataHora.getValue() != null){
                            list_horario.setItems(MapUtil.verificarLocalização(list_horario.getItems(),id,Util.converterData(date_dataHora.getValue().toString(),"normal")));//Platform.runLater(()->
                        }else{
                            list_horario.setItems(MapUtil.verificarLocalização(list_horario.getItems(),id,Util.time()));//Platform.runLater(()->
                        }   
                        progress_hora.setVisible(false); 
                    }catch(IllegalStateException ex){
                        Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                        dialogoInfo.setHeaderText("Ocorreu um erro");
                        dialogoInfo.setContentText(ex+"");
                        dialogoInfo.showAndWait();
                        progress_hora.setVisible(false); 
                    }
                }
            }.start();
       
    }
    @FXML
    void toggle_aula01(ActionEvent event) {
        pegarHorario(7,13,19);
    }

    @FXML
    void toggle_aula02(ActionEvent event) {
        pegarHorario(8,14,20);
    }

    @FXML
    void toggle_aula03(ActionEvent event) {
        pegarHorario(9,15,21);
    }

    @FXML
    void toggle_aula04(ActionEvent event) {
        pegarHorario(10,16,22);
    }

    @FXML
    void toggle_aula05(ActionEvent event) {
        pegarHorario(11,17,23);
    }
    //**************************************************************************
    
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
    
    @FXML
    private TableColumn<AlunosBean, String> coluna_hora;
    
    private ObservableList<AlunosBean> ConteudoTabelaAluno = FXCollections.observableArrayList();
    
    //**************************************************************************
      @FXML
    private ComboBox combo_numeroLinhas;
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
    //Pesquisar Aluno***********************************************************
    @FXML
    private TextField txt_pesquisar;

    @FXML
    void BT_pesquisar(ActionEvent event) {
        
                if(!"".equals(txt_pesquisar.getText())){
                    listaTabelaAlunos(txt_pesquisar.getText());
                }else{
                    listaTabelaAlunos("");            
                }
               
        
    }
    //**************************************************************************
    @FXML
    void BT_atualizar(ActionEvent event) {
        progress("Atualizando..."); 
        listaTabelaAlunos("null");
        listaTabelaLiberados();
        progress(".");
        //progress.setVisible(true);
      
    }
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
    String operacao;
    /**
     * Cadastra um novo aluno e altera um aluno
     */
    public void cadastrarAluno(){
       // new Thread(){        
         //   public void run(){
                MetodosSql execute = new MetodosSql(); 
                
                
                String Id = "";
                if(ConteudoTabelaAluno.size() > 0){
                    Id = ConteudoTabelaAluno.get(i).getId().getValue();
                }
                boolean resposta = Util.verificarData(date_dataNascimentoAluno.getValue().toString());
                if(resposta){                   
                    execute.CadastrarAluno(operacao,"456987", txt_nomeAluno.getText(),txt_senhaAluno.getText(),date_dataNascimentoAluno.getValue(), combo_sexoAluno.getValue()+"", txt_enderecoAluno.getText(), nome_pai.getText(), txt_nomeMae.getText(), txt_telefone.getText(), combo_serie.getValue()+"", combo_turma.getValue()+"", combo_turno.getValue()+"",Id);                //ConteudoTabelaAluno.get(i).getId().getValue()
                    System.err.println("Cadastrado");
                    listaTabelaAlunos("null"); 
                }else{
                    Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                    dialogoInfo.setHeaderText("Data Invalida");
                    dialogoInfo.showAndWait();
                }
                //BT_cadastrarAluno.setText("Cadastrar");
           // }
        //}.start();
    }
    
    //Criando grafico status****************************************************
    @FXML
    private PieChart grafico_status;
    private ObservableList<Data> data2d = FXCollections.observableArrayList();
    /**
     * Adiciona ao  grafico o ObservableList na inicialização do sistema
     * @return 
     */
    private ObservableList<Data> getChartData() {                          
        data2d.add(new PieChart.Data("Liberados",0));
        data2d.add(new PieChart.Data("Presente",0));
        data2d.add(new PieChart.Data("Alsente",0));
                
        //return data2d;
        return null;
    }
    /**
     * Joga as informacoes dos aluno liberados, presente, e faltas listados no sistema
     * @param liberados porcentagem de alunos liberados listados na tabela
     * @param p1 total de alunos liberados
     * @param presente porcentagem de alunos presentes
     * @param p2 total de alunos presentes
     * @param faltas porcentagem de aluno alsentes
     * @param p3  total de alunos alsentes
     */
     private void criarGrafico(int liberados,int p1,int presente,int p2,int faltas,int p3){
        data2d.get(0).setName("Liberados "+p1);
        data2d.get(1).setName("Presente "+p2);
        data2d.get(2).setName("Alsente "+p3);
        
        data2d.get(0).setPieValue(liberados);
        data2d.get(1).setPieValue(presente);
        data2d.get(2).setPieValue(faltas);
        Platform.runLater(() -> {  
           // grafico_status.setData(getChartData(0,0,0));            
            grafico_status.setTitle("Alunos "+(p1+p2+p3));            
            grafico_status.setLegendVisible(false);//Posicion de leyenda 
            grafico_status.setData(data2d);            
            int i = 1;
            String color = null;
            for (PieChart.Data data : data2d) {             
                switch(i){
                    case 1:color = "#1C86EE";break;
                    case 2:color = "#43CD80";break;
                    case 3:color = "#FF4040";break;
                }
                data.getNode().setStyle( "-fx-pie-color: "+color+";");                          
                i++;
            }
        });    
     }
    //**************************************************************************
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
    /**
     * Pega as informações do aluno selecionado e joga no formulario de cadastro
     */
    public void pegarDadosTabelaAlunos(){
        switch(tabela_aluno.getSelectionModel().getSelectedIndex()){
                    case -1:operacao = "cadastrar";System.out.println("Cadastrando");break;
                    default:operacao = "update";System.out.println("Update");break;
        }
        i = tabela_aluno.getSelectionModel().getSelectedIndex();
        txt_nomeAluno.setText(ConteudoTabelaAluno.get(i).getAluno().getValue());
        txt_senhaAluno.setText(ConteudoTabelaAluno.get(i).getId().getValue());                        
        date_dataNascimentoAluno.setValue(Util.LOCAL_DATE(ConteudoTabelaAluno.get(i).getData_nascimento().getValue().trim()));        
        combo_sexoAluno.setValue(ConteudoTabelaAluno.get(i).getSexo().getValue());
        txt_enderecoAluno.setText(ConteudoTabelaAluno.get(i).getEndereco().getValue());
        nome_pai.setText(ConteudoTabelaAluno.get(i).getNome_pai().getValue());
        txt_nomeMae.setText(ConteudoTabelaAluno.get(i).getNome_mae().getValue());
        txt_telefone.setText(ConteudoTabelaAluno.get(i).getTelefone().getValue());
        combo_serie.setValue(ConteudoTabelaAluno.get(i).getSerie().getValue());
        combo_turma.setValue(ConteudoTabelaAluno.get(i).getTurma().getValue());
        combo_turno.setValue(ConteudoTabelaAluno.get(i).getTurno().getValue());
        System.out.println("index: "+i);
    }
    /**
     * Apaga todas as informações do formulario de cadastro de aluno
     */
    public void sairDadosTabelaAlunos(){
        switch(tabela_aluno.getSelectionModel().getSelectedIndex()){
                    case -1:operacao = "cadastrar";System.out.println("Cadastrando");break;
                    default:operacao = "update";System.out.println("Update");break;
        }
        txt_nomeAluno.setText("");
        txt_senhaAluno.setText("");                        
        date_dataNascimentoAluno.setValue(null);        
        combo_sexoAluno.setValue(null);
        txt_enderecoAluno.setText("");
        nome_pai.setText("");
        txt_nomeMae.setText("");
        txt_telefone.setText("");
        combo_serie.setValue(null);
        combo_turma.setValue(null);
        combo_turno.setValue(null);
    }  
    /**
     * lista o conteudo da tabela aluno
     * @param aluno usado para pesquisar um aluno especifico
     */
    public void listaTabelaAlunos(String aluno){
        String linhas = (String)combo_numeroLinhas.getValue();
        if(combo_numeroLinhas.getValue() == null){
            linhas = "25";
        }
        alunosListados.clear();
        ConteudoTabelaHistorico.clear();
        ConteudoTabelaAluno.clear();
        MetodosSql m = new MetodosSql();
        Sql novo = new Sql();
        ArrayList valores = new ArrayList();
        ArrayList resultadoQuery = new ArrayList();
        String[] resultado = {"id_aluno","nome_aluno", "data_nascimento", "sexo", "endereco", "nome_pai","nome_mae","telefone_responsavel","serie","turma","turno","status"};       
        resultadoQuery = novo.executeQuery(m.criarQueryAluno((String)combo_serieFiltro.getValue(), (String)combo_turmaFiltro.getValue(), (String)combo_turnoFiltro.getValue(), (String)combo_statusFiltro.getValue(), (String)combo_generoFiltro.getValue(),aluno,linhas), valores, resultado);
        for (int i = 0; i <= resultadoQuery.size() - 1; i++) {
            String a = resultadoQuery.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");            
            ConteudoTabelaAluno.add(new AlunosBean("alsente",tokens[0].trim(),tokens[1].trim(),tokens[2].trim(),tokens[3].trim(), tokens[4].trim(),tokens[5].trim(),tokens[6].trim(),tokens[7].trim(),tokens[8].trim(),tokens[9].trim(),tokens[10].trim(),"-"));
            listaHistorico(tokens[1].trim(),(int)Float.parseFloat(tokens[0]),tokens[10].trim() , tokens[11].trim());
            alunosListados.add(tokens[1]);
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
    ArrayList liberados = new ArrayList();
    /**
     * Lista o conteudo a da tabela liberados
     */
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
            ConteudoTabelaLiberados.add(new LiberadosBean(tokens[0].trim(),tokens[1].trim(),Util.converterData(tokens[2].trim(),"normal"),tokens[3].trim(),tokens[4].trim()));
            if(Util.verificarData(tokens[2].trim(), Util.time())){
                liberados.add(tokens[1].trim());
            }
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
        boolean resposta = Util.verificarData(date_periodoLiberados.getValue().toString());
                if(resposta){                                       
                    UtilLiberarAlunos li = new UtilLiberarAlunos();
                    li.liberarAlunos((String)combo_serieLiberados.getValue(), (String)combo_turmaLiberados.getValue(), (String)combo_turnoLiberados.getValue(), txt_alunoLiberados.getText(),date_periodoLiberados.getValue(), txt_mensagem.getText(),check_todosLiberados.isSelected());
                    listaTabelaLiberados();
                }else{
                    Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);                
                    dialogoInfo.setHeaderText("Data Invalida");
                    dialogoInfo.showAndWait();
                }
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
        AcompanharLocalizacao(false);
        listaTabelaAlunos("null");
        AcompanharLocalizacao(true);
    }
      @FXML
    void BT_limparFiltro(ActionEvent event) {
        combo_serieFiltro.setValue(null);
        combo_turmaFiltro.setValue(null);
        combo_turnoFiltro.setValue(null);
        combo_statusFiltro.setValue(null);
        combo_generoFiltro.setValue(null);
        listaTabelaAlunos("null");
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getChartData();
        //setStatisticas();
        //listaHorario.addAll();
        progress.setVisible(true);  
        progress_hora.setVisible(false);
        progress_status.setVisible(false);
          // listaHistorico();
        list_alunos.setItems(alunosListados);
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
        coluna_hora.setCellValueFactory(cellData -> cellData.getValue().getHora());
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
        
        combo_numeroLinhas.getItems().add("25");
        combo_numeroLinhas.getItems().add("50");
        combo_numeroLinhas.getItems().add("100");
        combo_numeroLinhas.getItems().add("250");
        combo_numeroLinhas.getItems().add("500");
        combo_numeroLinhas.getItems().add("Todas");
                        
        
//        tabela_aluno.setRowFactory(tv -> new TableRow<AlunosBean>() {
//            public void updateItem(AlunosBean item, boolean empty) {
//                super.updateItem(item, empty) ;
//                System.out.println("COLORINDO");
//                if(item == null){
//                    setStyle("");
//                }else if(item.getStatus().getValue().equals("presente")){                    
//                    setStyle("-fx-background-color:#43CD80;");                     
//                }else if(item.getStatus().getValue().equals("alsente")){
//                    setStyle("-fx-background-color:red;");                    
//                }
//            }
//        });
        
//        tabela_Liberados.setRowFactory(tv -> new TableRow<LiberadosBean>() {
//            public void updateItem(LiberadosBean item, boolean empty) {
//                super.updateItem(item, empty) ;
//                if(item == null){
//                    setStyle("");
//                }else if(item.getStatus().getValue().equals("1")){                    
//                    setStyle("-fx-background-color:#43CD80;");
//                }else if(item.getStatus().getValue().equals("0")){
//                    setStyle("-fx-background-color:red;");
//                }else{
//                    setStyle("");
//                }
//            }
//        });
        
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
        
        listaTabelaAlunos("null");
        
        coluna_parametro.setCellValueFactory(cellData -> cellData.getValue().getParametro());
        coluna_prazo.setCellValueFactory(cellData -> cellData.getValue().getPrazo());
        coluna_dataLiberados.setCellValueFactory(cellData -> cellData.getValue().getHora());
        coluna_mensagem.setCellValueFactory(cellData -> cellData.getValue().getMensagem());
        
        tabela_Liberados.setItems(ConteudoTabelaLiberados);
        listaTabelaLiberados();
        
        combo_estatisticaSerie.getItems().add("1");
        combo_estatisticaSerie.getItems().add("2");
        combo_estatisticaSerie.getItems().add("3");
        
        combo_estatisticaTurma.getItems().add("A");
        combo_estatisticaTurma.getItems().add("B");
        combo_estatisticaTurma.getItems().add("C");
        
        combo_estatisticaTurno.getItems().add("Matutino");
        combo_estatisticaTurno.getItems().add("Vespertino");
        combo_estatisticaTurno.getItems().add("Noturno");
        
        combo_generoFiltro.getItems().add("M");
        combo_generoFiltro.getItems().add("F");
        
        combo_statusFiltro.getItems().add("Ativo");
        combo_statusFiltro.getItems().add("Inativo");
        
        combo_serieFiltro.getItems().add("1");combo_serieFiltro.getItems().add("2");combo_serieFiltro.getItems().add("3");
        combo_turmaFiltro.getItems().add("A");combo_turmaFiltro.getItems().add("B");combo_turmaFiltro.getItems().add("C");
        
        combo_turnoFiltro.getItems().add("Matutino");combo_turnoFiltro.getItems().add("Vespertino");combo_turnoFiltro.getItems().add("Noturno");
        AcompanharLocalizacao(true);
    } 
    
    /**
     * lista o historico de presença do aluno
     * @param nome 
     * @param id 
     * @param turno 
     * @param status 
     */
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
    
    /**
     * Acompanha a presença do aluno
     * @param continuar 
     */
    public void AcompanharLocalizacao(boolean continuar){
        Timer time = new Timer();
        final long SEGUNDOS = (2000 * 5);
        try{
                if(continuar){
                    TimerTask tarefa = new TimerTask(){
                    @Override
                    public void run(){  
                        System.out.println("********************\nExecutando Verificação\n********************"+ConteudoTabelaAluno.size());
                        for (int j = 0; j <= ConteudoTabelaAluno.size()-1; j++) {
                            String texto = ConteudoTabelaAluno.get(j).getId().getValue();
                            int id = (int)Float.parseFloat(texto);
                            ListaHistorico lo = new ListaHistorico();          
                            HistoricoBean objeto = new HistoricoBean();
                            objeto = lo.PegarLocalizacao(id,ListaHistorico.time());
                            boolean result =  false;                                                          
                            if(objeto.getLatitude() != null){                               
                                result =  ListaHistorico.localizacao(Float.parseFloat(objeto.getLatitude()),Float.parseFloat(objeto.getLongitude()));                                                        
                            }
                            
                            if(result){
                                ConteudoTabelaAluno.get(j).getStatus().setValue("presente");                                
                                ConteudoTabelaAluno.get(j).getHora().setValue(objeto.getHora());                                
                                
                            }else{
                                ConteudoTabelaAluno.get(j).getStatus().setValue("alsente");
                                ConteudoTabelaAluno.get(j).getHora().setValue(objeto.getHora());                                
                            }
                            colorir();
                            li = 0;
                            pre = 0;
                            fa = 0;
                        }  
                    }                    
                };
                time.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);
                     
                
                }
                   
        }catch(IndexOutOfBoundsException e){
            System.out.println("############################################Ocorreu um erro:"+e);
        }

    }
    /**
     * Executa os metodos listaTabelaAlunos() e listaTabelaLiberados()
     */
    public void atualizarTabelas(){
        //atualizando todas as tabelas
        try{
            listaTabelaAlunos("null");
            listaTabelaLiberados();    
        }catch(Exception ex){
            System.err.println("********************\nErro ao lista Tabela:\n"+ex+"\n**********************");
        }
        
    }
    int pre = 0,li = 0,fa = 0;
    /**
     * Adiciona cor nas tabelas aluno e liberados
     */
    public void colorir(){
        tabela_aluno.setRowFactory(tv -> new TableRow<AlunosBean>() {
            public void updateItem(AlunosBean item, boolean empty) {
                super.updateItem(item, empty) ;                                
                if(item == null){
                    setStyle("");
                }else if(Util.comparar(liberados,item.getAluno().getValue(),item.getSerie().getValue(),item.getTurma().getValue(),item.getTurno().getValue())){    
                    setStyle("-fx-background-color:#1E90FF;");   
                    li++;
                }else if(item.getStatus().getValue().equals("presente")){                    
                    setStyle("-fx-background-color:#43CD80;");                     
                    pre++;
                }else if(item.getStatus().getValue().equals("alsente")){
                    setStyle("-fx-background-color:#FF4040;");                    
                    fa++;
                }
                if((li+pre+fa) == ConteudoTabelaAluno.size() || ConteudoTabelaAluno.isEmpty()){
                    int p1 = 0,p2 = 0,p3 = 0;
                    if(ConteudoTabelaAluno.isEmpty()){
                        
                    }else{                                            
                    p1 = (li*100)/ConteudoTabelaAluno.size();
                    p2 = (pre*100)/ConteudoTabelaAluno.size();
                    p3 = (fa*100)/ConteudoTabelaAluno.size(); 
                    }
                    criarGrafico(p1,li,p2,pre,p3,fa);
                    li = 0;
                    pre = 0;
                    fa = 0;
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
        
        
    }
    
     @FXML
    void criarSerie(ActionEvent event) throws Exception {
        AbrirCriarSerie abrir = new AbrirCriarSerie();        
        abrir.start(new Stage());        
    }
   
       @FXML
    void sairData(MouseEvent event) {
        if(date_dataNascimentoAluno.getValue() != null){
            
                
            
        }else{
             System.err.println("sair Mouse 2");
        }
        System.err.println("sair Mouse");
    }
    
}
        
        
    

    

