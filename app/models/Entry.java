package models;

import java.util.List;

import scala.util.parsing.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

/**
 * Entries Model
 */
public class Entry {
	
	public int athleteNo;
	public int eventPointer;
	public int eventNumber;
	public String eventSex;
	public float eventDistance;
	public String eventStroke;
	public boolean scratch;
	
	//Athlete Constructor
	public Entry() {
		athleteNo = 0;
		eventPointer = 0;
		eventNumber = 0;
		eventSex = null;
		eventDistance = 0;
		eventStroke = null;
		scratch = false;
	}
	
	//Returns all entries for a given athlete
	public List<Entry> getAthleteEntries(int athleteNo){
	    Connection dbconnection = null;
	    Statement stmt = null;
	    
	    try{
		    dbconnection = play.db.DB.getConnection("default");
		    stmt = dbconnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    
			String athleteEntriesSql = "SELECT Entry.Ath_no, Entry.Event_ptr, Event_no, Event_sex," +
			" Event_dist, Event_stroke, Scr_stat FROM Event JOIN Entry where Entry.Ath_no = " +
			"\"" + athleteNo + "\"" + "AND Entry.Event_ptr = Event.Event_ptr";
					
			
			ResultSet resultSet = stmt.executeQuery(athleteEntriesSql);
			
			List<Entry> list = new ArrayList <>();
	        while (resultSet.next()){
	        	Entry e = new Entry();
	            e.athleteNo = resultSet.getInt(1);
	    		e.eventPointer = resultSet.getInt(2);
	    		e.eventNumber = resultSet.getInt(3);;
	    		e.eventSex = resultSet.getString("Event_sex");
	    		e.eventDistance = resultSet.getFloat(5);
	    		e.eventStroke = resultSet.getString("Event_stroke");
	    		e.scratch = resultSet.getBoolean(7);
	            if (e.athleteNo != 0) {
		            list.add(e);
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
	
	//Returns all entries for a given athlete that they have NOT scratched
	public List<Entry> getAthleteCheckedInEntries(int athleteNo){
	    Connection dbconnection = null;
	    Statement stmt = null;
	    
	    try{
		    dbconnection = play.db.DB.getConnection("default");
		    stmt = dbconnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    
			String athleteEntriesSql = "SELECT Entry.Ath_no, Entry.Event_ptr, Event_no, Event_sex," +
			" Event_dist, Event_stroke, Scr_stat FROM Event JOIN Entry where Entry.Ath_no = " +
			"\"" + athleteNo + "\"" + "AND Entry.Event_ptr = Event.Event_ptr AND Scr_stat = false";
					
			
			ResultSet resultSet = stmt.executeQuery(athleteEntriesSql);
			
			List<Entry> list = new ArrayList <>();
	        while (resultSet.next()){
	        	Entry e = new Entry();
	            e.athleteNo = resultSet.getInt(1);
	    		e.eventPointer = resultSet.getInt(2);
	    		e.eventNumber = resultSet.getInt(3);;
	    		e.eventSex = resultSet.getString("Event_sex");
	    		e.eventDistance = resultSet.getFloat(5);
	    		e.eventStroke = resultSet.getString("Event_stroke");
	    		e.scratch = resultSet.getBoolean(7);
	            if (e.athleteNo != 0) {
		            list.add(e);
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
	
	//Sets the scratch status for an athlete's entry
	public static void setEntryScratchStatus(int athleteNo, int eventPointer, boolean scratch) {
		Connection dbconnection = null;
	    Statement stmt = null;
	    
	    try{
		    dbconnection = play.db.DB.getConnection("default");
		    stmt = dbconnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    
			String setEntryScratchStatusSql = "UPDATE Entry SET Scr_stat = " + scratch +
			" WHERE Ath_no = " + "\"" + athleteNo + "\"" + "AND Event_ptr = " + "\"" + eventPointer + "\"";
			
			stmt.executeUpdate(setEntryScratchStatusSql);
	        
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