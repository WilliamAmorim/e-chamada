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
        DateTime end = new DateTime(2019, 12, 25, 0, 0, 0, 0);
        Interval interval = new Interval(start, end);
                
        Chronology chrono = interval.getChronology();
        Duration duration = interval.toDuration();
        Period period = interval.toPeriod();
        listaData(start,end,duration.toStandardDays().getDays());
        if(period.getYears() > 0 ){
            //Intervalo  de um ano
            System.out.println("Intervalo de um ano");
        }else if(period.getMonths() > 0){
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
        int d = 0,m=0,a =1;
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
                if(ini.getMonthOfYear()+m == 12 && ini.getDayOfMonth()+d == 31){
                    ano = data.getYear()+a+"";
                    a++;m = 1; d = 1;
                }else{
                    ano = data.getYear()+"";
                }
                d++;
            }catch(Exception ex){
                System.err.println("********************************************");
                //System.err.println("Erro:"+ex);
                mes = (data.getMonthOfYear()+m)+"";
                fluxo = false;                
                m++;
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
