package models;

import java.util.List;
import java.sql.*;
//import play.db.ebean.Model;
import java.util.ArrayList;

/**
 * Athlete Model
 */
public class Athlete {
	
	public int athleteNo;
	public int athleteAge;
	public String firstname;
	public String lastname;
	public String birthdate;
	public String teamname;
	
	//Athlete Constructor
	public Athlete() {
		athleteNo = 0;
		firstname = null;
		lastname = null;
		athleteAge = 0;
		teamname = null;
		birthdate = null;
	}
	
	public List<Athlete> findAthleteByLastNameBirthdate(String lastname, String birthdate){
	    Connection dbconnection = null;
	    Statement stmt = null;
	    
	    try{
		    dbconnection = play.db.DB.getConnection("default");
		    stmt = dbconnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    
			String findathleteSql = "SELECT Ath_no, First_name, Last_name, Ath_age, Team_name FROM " +
			"Athlete JOIN Team WHERE Athlete.Team_no = Team.Team_no AND Last_name = " + "\"" + lastname + "\"" + " AND " +
			"Birth_date = " + "\"" + birthdate + "\"";
			
			ResultSet resultSet = stmt.executeQuery(findathleteSql);
			
			List<Athlete> list = new ArrayList <>();
	        while (resultSet.next()){
	        	Athlete a = new Athlete();
	            a.athleteNo = resultSet.getInt(1);
	            a.firstname = resultSet.getString("First_name");
	            a.lastname = resultSet.getString("Last_name");
	            a.athleteAge = resultSet.getInt(4);
	            a.teamname = resultSet.getString("Team_name");
	            if (a.athleteNo != 0) {
		            list.add(a);
	            }
	        }
	        
	        if (list.isEmpty()) {
	        	return null;
	        }
	        else return list;
	        
	    } catch(SQLException e){
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally{ //close resources
	        try{
	            if(stmt!=null)
	            dbconnection.close();
	        }catch(SQLException e){
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }try{
	            if(dbconnection!=null)
	            dbconnection.close();
	        }catch(SQLException se){
	            se.printStackTrace();
	            throw new RuntimeException(se);
	        }
	    }
	}
	
	public Athlete findAthleteByAthleteNumber(int athleteNo){
	    Connection dbconnection = null;
	    Statement stmt = null;
	    
	    try{
		    dbconnection = play.db.DB.getConnection("default");
		    stmt = dbconnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    
			String findathleteSql = "SELECT Ath_no, First_name, Last_name, Ath_age, Team_name, Birth_date FROM " +
			"Athlete JOIN Team WHERE Athlete.Team_no = Team.Team_no AND Ath_no = " + "\"" + athleteNo + "\"";
			
			ResultSet resultSet = stmt.executeQuery(findathleteSql);
			
			Athlete a = new Athlete();
	        while (resultSet.next()){
	            a.athleteNo = resultSet.getInt(1);
	            a.firstname = resultSet.getString("First_name");
	            a.lastname = resultSet.getString("Last_name");
	            a.athleteAge = resultSet.getInt(4);
	            a.teamname = resultSet.getString("Team_name");
	            a.birthdate = resultSet.getString(String.valueOf("Birth_date"));
	        }
	        
	        return a;
	        
	    } catch(SQLException e){
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally{ //close resources
	        try{
	            if(stmt!=null)
	            dbconnection.close();
	        }catch(SQLException e){
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }try{
	            if(dbconnection!=null)
	            dbconnection.close();
	        }catch(SQLException se){
	            se.printStackTrace();
	            throw new RuntimeException(se);
	        }
	    }
	}
}