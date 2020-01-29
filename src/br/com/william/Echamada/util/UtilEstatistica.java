/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.william.Echamada.util;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

/**
 *
 * @author willi
 */
public class UtilEstatistica {
    public static void intervalo(){
        DateTime start = new DateTime(2019, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2020, 1, 10, 0, 0, 0, 0);
        Interval interval = new Interval(start, end);
                
        Chronology chrono = interval.getChronology();
        Duration duration = interval.toDuration();
        Period period = interval.toPeriod();
        listaData(start,end,(int)interval.toDuration().getStandardDays());
        if(period.getMonths() > 0){
            //Intervalo de um  mês
            System.out.println("Intervalo de um mês");
        }else if(period.getWeeks() > 0){
            //Intervalo de uma semana
            System.out.println("Intervalo de uma semana");
        }else{
            //intervalo de dias
            System.out.println("Intervalo de dias");
        }
        //System.out.println("chrono:"+chrono.dayOfWeek().getAsText(0)+"\n duration:"+duration.getStandardDays()+"\n period:"+period);
    }
    public static void listaData(DateTime ini,DateTime end,int dias){
        String dia,mes = null,ano = null;
        System.out.println("dias:"+dias);
        int d = 0,m=0,a =0;
        DateTime data = null;
        boolean fluxo = true;
        for (int i = 0; i <= dias; i++) {
            try{
                if(fluxo){
                    data = new DateTime(ini.getYear(), ini.getMonthOfYear()+m, ini.getDayOfMonth()+d, 0, 0, 0, 0);
                }else{
                    data = new DateTime(ini.getYear(), ini.getMonthOfYear()+m, d, 0, 0, 0, 0);
                }
               
                dia = data.dayOfMonth().getAsText();
                mes = data.getMonthOfYear()+"";
                if(mes.equals("12") && dia.equals("31")){
                    ano = data.getYear()+a+"";
                    a++;m = 1; d = 1;
                }else{
                    ano = data.getYear()+a+"";
                }
                d++;
            }catch(Exception ex){
                System.err.println("********************************************");                                
                fluxo = false;                
                m++;
                mes = (data.getMonthOfYear()+1)+"";
                d=2;
                dia = "1"; 
                
                
            }
           
            System.out.println("Data: "+dia+"/"+mes+"/"+ano);
            
        }
        
    }
    public static void main(String[] args){
       intervalo();
    }
}
