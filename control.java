/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleoppgave4;

/**
 *
 * @author staaleas
 */


import java.sql.*;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class control {
    public static control control = new control();
    String oracleurl = "jdbc:oracle:thin:@158.36.139.21:1521:ORCL";
    Connection conn = null;
    Statement setning;
    ResultSet resultat;
    CallableStatement callableStatement = null;
    
 public void initialiserDB() {
    try {
    conn = DriverManager.getConnection(oracleurl,"C##STAALE2","inF_313");
    }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
    }
 
 public control() {
     
    initialiserDB();
 }
 
 public void regNyEier(int personnr,String fornavn, String etternavn, String adresse, int postnr) throws SQLException{
 try {
 callableStatement = conn.prepareCall("{ call NYPERSON(?, ? , ? , ? ,?) }");
 callableStatement.setInt(1,personnr);
 callableStatement.setString(2,fornavn);
 callableStatement.setString(3,etternavn);
 callableStatement.setString(4,adresse);
 callableStatement.setInt(5,postnr);
 callableStatement.executeUpdate();

 }catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
} //Slutt metode
 
 public void regNyBil(String regnr, String bilmerke, String bilmodell, int regår, int eier){
     
     try{
     callableStatement = conn.prepareCall("{call NYBIL(?, ? , ? , ? ,?)}");
     callableStatement.setString(1,regnr);
     callableStatement.setString(2,bilmerke);
     callableStatement.setString(3,bilmodell);
     callableStatement.setInt(4,regår);
     callableStatement.setInt(5,eier);
     callableStatement.executeUpdate();
     
     }catch(Exception ex){JOptionPane.showMessageDialog(null, ex.getMessage());}
 
 }
  public String getNavn(long nr){
 try{
 callableStatement = conn.prepareCall("{ ? = call GETNAVN(?) }");
 callableStatement.registerOutParameter(1,Types.VARCHAR);
 callableStatement.setLong(2,nr);
 callableStatement.execute();
 String navn = callableStatement.getString(1);
 return navn;
 }catch(Exception ex){return ex.getMessage();}
} //Metode
  
  public ResultSet getBiler(long nr){
  try{
  String query = "{ ? = call GETBILER(?) }";
  callableStatement = conn.prepareCall(query);
  callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
  callableStatement.setLong(2,nr);
  callableStatement.execute();
   ResultSet rs = (ResultSet) callableStatement.getObject(1);
  return rs;
  }catch(Exception ex){return null;}
  }
  
}