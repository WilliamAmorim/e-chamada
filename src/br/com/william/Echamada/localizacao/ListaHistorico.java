/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.localizacao;

import br.com.william.Echamada.bean.HistoricoBean;
import br.com.william.Echamada.sql.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public class ListaHistorico extends Localizacao {

    public static int h1, h2, h3, h4, h5,horaInicio,a;
    public static boolean A = true;
    public static int fluxo = 100;

    /**
     * metodo que retornar todo o historio de um aluno com base nos parametros
     * pasados
     *
     * @param idAluno
     * @param data
     * @return
     */
    public List<HistoricoBean> readHistorico(int idAluno, String data) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<HistoricoBean> historico_list = new ArrayList();
        try {
            stmt = con.prepareStatement("SELECT * FROM `historicoLocalizacao` WHERE id_aluno LIKE ? AND data LIKE ?");//"SELECT * FROM `historicoLocalizacao` WHERE id_aluno LIKE ? AND data LIKE ?"
            stmt.setInt(1, idAluno);
            stmt.setString(2, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                HistoricoBean his = new HistoricoBean(); // alu = alunos
                his.setId_aluno(rs.getInt("id_aluno"));
                his.setLatitude(rs.getString("latitude"));
                his.setLongitude(rs.getString("longitude"));
                his.setHora(rs.getString("hora"));
                his.setData(rs.getString("data"));
                historico_list.add(his);
            }
        } catch (SQLException ex) {
           // Logger.getLogger(PaisBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return historico_list;
    }

    /**
     *
     * @return retorna a data atual
     */
    public static String time() {
        Date hoje = new Date();
        SimpleDateFormat df;
        df = new SimpleDateFormat("d/MM/yyyy");
        return df.format(hoje);
    }

    public static ArrayList lista(String aluno, int idAluno, String data, String turno, String status) {
        int inicioAula = 0;
       
        switch (turno) {
            case "Matutino":
                inicioAula = 7;
                horaInicio = 7;
                a = 7;
                break;
            case "Vespertino":
                inicioAula = 13;
                horaInicio = 13;
                a = 13;
                break;
            case "Noturno":
                inicioAula = 19;
                horaInicio = 19;
                a = 19;
                break;
        }  
          if (data.equals("")) {
            parar(inicioAula);
            
            data = time();
        }
        ListaHistorico l = new ListaHistorico();
        for (HistoricoBean h : l.readHistorico(idAluno, data)) {                 
            for (int i = 0; i <= 4; i++) {                                               
                A = true;
                if (percorerAulas(h.getHora()) == (inicioAula+i)) {                   
                    if (presente(Float.parseFloat(h.getLatitude()), Float.parseFloat(h.getLongitude()), status)) {                                            
                        switch (i) {
                            case 0:h1++;break;
                            case 1:h2++;break;
                            case 2:h3++;break;
                            case 3:h4++;break;
                            case 4:h5++;break;
                        }
                    }
                }   
            }
        } 
        ArrayList retor = new ArrayList();
        retor.add(aluno);
        retor.add(data);
       
        
        if (h1 >= 3) {
            retor.add("Presente");
        }else if(a >= fluxo && data.equals(time())){
            retor.add("");
        }else{retor.add("Alsente");}
        
        if (h2 >= 3) {
            retor.add("Presente");
        }else if(a+1 >= fluxo && data.equals(time())){
            retor.add("");
        }else{retor.add("Alsente");}
        
        if (h3 >= 3) {
            retor.add("Presente");
        }else if(a+2 >= fluxo && data.equals(time())){
            retor.add("");
        }else{retor.add("Alsente");}
        
        if (h4 >= 3) {
            retor.add("Presente");
        }else if(a+3 >= fluxo && data.equals(time())){
            retor.add("");
        }else{retor.add("Alsente");}
        
        if (h5 >= 3) {
            retor.add("Presente");
        }else if(a+4 >= fluxo && data.equals(time())){
            retor.add("");
        }else{retor.add("Alsente");
        
        }
        
        h1 = 0;h2 = 0;h3 = 0;h4 = 0;h5 = 0;
       
           return retor;
       
    }

    public static int percorerAulas(String t) {
        int minutos = 0;        
        if(A){
            A = false;
            horaInicio = a;
            
        }
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                //System.out.println(horaInicio + ":" + minutos);
                if (t.equals(horaInicio + ":" + minutos)) {
                    return horaInicio;
                    
                }
                minutos += 10;

            }
            if (minutos > 40) {
                minutos = 0;
            }

            horaInicio++;

        }
        //return 0;
        return 100;
    }
    
    
    public static void parar(int inicio){          
          Calendar horas = Calendar.getInstance();
          int h = horas.get(Calendar.HOUR_OF_DAY);          
         // System.out.println("hora:"+h);
          for (int i = inicio; i <= inicio+5; i++) {
              if(h-1 == i){
                  fluxo = i;
                  break;
              }
        }
    }

    public static void main(String[] args) {
        lista("william", 3, "", "Matutino", "1");
        horaInicio = 7;
        System.out.println(percorerAulas("7:10"));
        parar(7);

    }
}
