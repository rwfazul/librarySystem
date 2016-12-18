/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author rhau
 */
public class OperacoesData {
    
    public static String dataSistema() {
        Date hoje = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(hoje);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(hoje.getTime());
    }
   
    public static String dataMaisDias(Integer diasParaSoma) {
        Date hoje = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(hoje);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + diasParaSoma);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(c.getTime());
    }
    
    public static String dataMaisDias(Calendar c, Integer diasParaSoma) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + diasParaSoma);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(c.getTime());
    }
    
}
