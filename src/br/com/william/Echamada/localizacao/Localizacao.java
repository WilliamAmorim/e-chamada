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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  
 * @author willi
 */
public class Localizacao {
    
    /**
     * Metodo que retorna True se o aluno Estiver Presente e false se não estiver
     * @param latitude
     * @param longitude
     * @return 
     */
   public  static boolean localizacao(float latitude, float longitude) {
        int ok = 0;
        if (latitude > -17.563999 && latitude < -17.563001) {
            ok = 1;
        } else if (latitude > -17.562999 && latitude < -17.562001) {
            ok = 1;
        }

        if (longitude > -52.547999 && longitude < -52.547001) {
            ok += 1;
        } else if (longitude > -52.546999 && longitude < -52.546600) {
            ok += 1;
        }
        if (ok == 2) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Metodo que e extansiado por outra classe.
     * Retorna True se o aluno Estiver Presente e false se não estiver
     * @param latitude
     * @param longitude
     * @param status
     * @return 
     */
    public static boolean presente(double latitude,double longitude,String status){
        float l = (float)latitude;
        float Lon = (float)longitude;
        if(localizacao(l,Lon) && "1".equals(status)){
            return true;
        }else{
            return false;
        }                 
    }
    
    public HistoricoBean PegarLocalizacao(int id,String data){
        
        String sql = "SELECT * FROM `historicolocalizacao` WHERE id_aluno = ? AND data = ?";
        Connection con = ConnectionFactory.getConnection();
        ResultSet resultado = null;
        PreparedStatement his = null;
        
        try {
            his = con.prepareStatement(sql);
            his.setInt(1, id);
            his.setString(2,data);
            resultado = his.executeQuery();
            if(resultado == null){
                return null;
            }
            HistoricoBean re = new HistoricoBean();
            while(resultado.next()) {     
                
                
                re.setId_aluno(resultado.getInt("id_aluno"));
                re.setLatitude(resultado.getString("latitude"));
                re.setLongitude(resultado.getString("longitude"));
                re.setHora(resultado.getString("hora"));
                re.setData(resultado.getString("data"));
               
            }
            return re;
        } catch (SQLException ex) {
            Logger.getLogger(Localizacao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con, his);
        }
        return null;
        
  
    }
    
    /**
     *  Metodo main usado para testes e não na versão final
     */
    public static void main(String[] args){
         System.out.println(presente(-17.54683,-52.53959,"1"));
        
         
    }
}
