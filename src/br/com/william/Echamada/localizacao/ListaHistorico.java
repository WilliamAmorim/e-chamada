/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.localizacao;

import br.com.william.Echamada.bean.HistoricoBean;
import br.com.william.Echamada.sql.ConnectionFactory;
import br.com.william.Echamada.sql.Sql;
import br.com.william.Echamada.util.Util;
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
   public static int h1, h2, h3, h4, h5,hora;
    public static ArrayList buscarHistorico(String id,String data,String turno){
        switch(turno){
            case "Matutino":hora = 7;break;
            case "Vespertino":hora = 13;break;
            case "Noturnon":hora = 19;break;
        }
        Sql buscar = new Sql();
        String[] retorno = {"id_aluno","data","hora","latitude","longitude"};
        ArrayList values = new ArrayList();
        values.add(id);
        if("0".equals(data.substring(0, 1))){
           data = data.substring(1);
        }
        
        values.add(data);
        ArrayList r = new ArrayList();
        r = buscar.executeQuery("SELECT `id_aluno`, `data`, `hora`, `latitude`, `longitude` FROM `historicolocalizacao` WHERE id_aluno = ? AND data = ?", values, retorno);
        
        for (int i = 0; i <= r.size() - 1; i++) {
            String a = r.get(i).toString();
            String b = a.replace("[", "").replace("]", "");
            String[] tokens = b.split(",");     
            //System.out.println(tokens[0]+" - "+tokens[1]+" - "+tokens[2]+" - "+tokens[3]+" - "+tokens[4]);
            float la = Float.parseFloat(tokens[3].trim());
            float lo = Float.parseFloat(tokens[4].trim());
            if(localizacao(la,lo)){                
                String[] hora = tokens[2].trim().split(":"); 
                conferirHora(hora[0].trim(),turno);
            }
        }
        
        return conferirPresenca(data);
        
    }
    
    private static void conferirHora(String hora,String turno){
        //System.err.println(hora);
        if(turno.equals("Matutino")){
            switch(hora){
                case "7": h1++;
                case "8": h2++;
                case "9": h3++;
                case "10":h4++;
                case "11":h5++;            
            }
        }else if(turno.equals("Vespertino")){
            switch(hora){
                case "13": h1++;
                case "14": h2++;
                case "15": h3++;
                case "16": h4++;
                case "17": h5++;            
            }
        }else if(turno.equals("Noturno")){
            switch(hora){
                case "19": h1++;
                case "20": h2++;
                case "21": h3++;
                case "22": h4++;
                case "23": h5++;            
            }
        }
    }
    private static ArrayList conferirPresenca(String data){
        ArrayList retor = new ArrayList();       
        
        retor.add(data);
        
        if(h1 >= 3){
            retor.add("Presente");
        }else if(data.equals(Util.time()) && Util.hora() <= hora){
            retor.add("");
        }else{
            retor.add("Alsente");
        }
        
        if(h2 >= 3){
            retor.add("Presente");
        }else if(data.equals(Util.time()) && Util.hora() <= hora+1){
            retor.add("");
        }else{
            retor.add("Alsente");
        }
        
        if(h3 >= 3){
            retor.add("Presente");
        }else if(data.equals(Util.time()) && Util.hora() <= hora+2){
            retor.add("");
        }else{
            retor.add("Alsente");
        }
        
        if(h4 >= 3){
            retor.add("Presente");
        }else if(data.equals(Util.time()) && Util.hora() <= hora+3){
            retor.add("");
        }else{
            retor.add("Alsente");
        }
        
        if(h5 >= 3){
            retor.add("Presente");
        }else if(data.equals(Util.time()) && Util.hora() <= hora+4){
            retor.add("");
        }else{
            retor.add("Alsente");
        }
        h1 = 0; h2 = 0; h3= 0;h4=0; h5=0;
        return retor;
        //System.err.println(retor.get(0)+" | "+retor.get(1)+" | "+retor.get(2)+" | "+retor.get(3)+" | "+retor.get(4)+" | "+retor.get(5));
    }
    public static void main(String[] args){
        //buscarHistorico("14","2/02/2020","Matutino");
        System.out.println(Util.hora());
    }
    
//    public static int h1, h2, h3, h4, h5,horaInicio,a;
//    public static boolean A = true;
//    public static int fluxo = 100;
//
//    /**
//     * metodo que retornar todo o historio de um aluno com base nos parametros
//     * pasados
//     *
//     * @param idAluno
//     * @param data
//     * @return
//     */
//    public List<HistoricoBean> readHistorico(int idAluno, String data) {
//        Connection con = ConnectionFactory.getConnection();
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        List<HistoricoBean> historico_list = new ArrayList();
//        try {
//            stmt = con.prepareStatement("SELECT * FROM `historicoLocalizacao` WHERE id_aluno LIKE ? AND data LIKE ?");//"SELECT * FROM `historicoLocalizacao` WHERE id_aluno LIKE ? AND data LIKE ?"
//            stmt.setInt(1, idAluno);
//            stmt.setString(2, data);
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                HistoricoBean his = new HistoricoBean(); // alu = alunos
//                his.setId_aluno(rs.getInt("id_aluno"));
//                his.setLatitude(rs.getString("latitude"));
//                his.setLongitude(rs.getString("longitude"));
//                his.setHora(rs.getString("hora"));
//                his.setData(rs.getString("data"));
//                historico_list.add(his);
//            }
//        } catch (SQLException ex) {
//           // Logger.getLogger(PaisBean.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ConnectionFactory.closeConnection(con, stmt, rs);
//        }
//        return historico_list;
//    }
//

//
//    public static ArrayList lista(String aluno, int idAluno, String data, String turno, String status) {
//        int inicioAula = 0;
//       
//        switch (turno) {
//            case "Matutino":
//                inicioAula = 7;
//                horaInicio = 7;
//                a = 7;
//                break;
//            case "Vespertino":
//                inicioAula = 13;
//                horaInicio = 13;
//                a = 13;
//                break;
//            case "Noturno":
//                inicioAula = 19;
//                horaInicio = 19;
//                a = 19;
//                break;
//        }  
//          if (data.equals("")) {
//            parar(inicioAula);
//            
//            data = time();
//        }
//        ListaHistorico l = new ListaHistorico();
//        for (HistoricoBean h : l.readHistorico(idAluno, data)) {                 
//            for (int i = 0; i <= 4; i++) {                                               
//                A = true;
//                if (percorerAulas(h.getHora()) == (inicioAula+i)) {                   
//                    if (localizacao(Float.parseFloat(h.getLatitude()), Float.parseFloat(h.getLongitude()))) {                                            
//                        switch (i) {
//                            case 0:h1++;break;
//                            case 1:h2++;break;
//                            case 2:h3++;break;
//                            case 3:h4++;break;
//                            case 4:h5++;break;
//                        }
//                    }
//                }   
//            }
//        } 
//        ArrayList retor = new ArrayList();
//        retor.add(aluno);
//        retor.add(data);
//       
//        
//        if (h1 >= 3) {
//            retor.add("Presente");
//        }else if(a >= fluxo && data.equals(time())){
//            retor.add("");
//        }else{retor.add("Alsente");}
//        
//        if (h2 >= 3) {
//            retor.add("Presente");
//        }else if(a+1 >= fluxo && data.equals(time())){
//            retor.add("");
//        }else{retor.add("Alsente");}
//        
//        if (h3 >= 3) {
//            retor.add("Presente");
//        }else if(a+2 >= fluxo && data.equals(time())){
//            retor.add("");
//        }else{retor.add("Alsente");}
//        
//        if (h4 >= 3) {
//            retor.add("Presente");
//        }else if(a+3 >= fluxo && data.equals(time())){
//            retor.add("");
//        }else{retor.add("Alsente");}
//        
//        if (h5 >= 3) {
//            retor.add("Presente");
//        }else if(a+4 >= fluxo && data.equals(time())){
//            retor.add("");
//        }else{retor.add("Alsente");
//        
//        }
//        
//        h1 = 0;h2 = 0;h3 = 0;h4 = 0;h5 = 0;
//       
//           return retor;
//       
//    }
//
//    public static int percorerAulas(String t) {
//        int minutos = 0;        
//        if(A){
//            A = false;
//            horaInicio = a;
//            
//        }
//        for (int i = 0; i <= 5; i++) {
//            for (int j = 0; j <= 5; j++) {
//                //System.out.println(horaInicio + ":" + minutos);
//                if (t.equals(horaInicio + ":" + minutos)) {
//                    return horaInicio;
//                    
//                }
//                minutos += 10;
//
//            }
//            if (minutos > 40) {
//                minutos = 0;
//            }
//
//            horaInicio++;
//
//        }
//        //return 0;
//        return 100;
//    }
//    
//    
//    public static void parar(int inicio){          
//          Calendar horas = Calendar.getInstance();
//          int h = horas.get(Calendar.HOUR_OF_DAY);          
//         // System.out.println("hora:"+h);
//          for (int i = inicio; i <= inicio+5; i++) {
//              if(h-1 == i){
//                  fluxo = i;
//                  break;
//              }
//        }
//    }
//
//    public static void main(String[] args) {
//        lista("Raul Gabriel", 5, "3/02/2020", "Matutino", "1");
//       //horaInicio = 7;
//       // System.out.println(percorerAulas("7:10"));
//        //parar(7);
//
//    };
}
