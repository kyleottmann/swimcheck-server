package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Entry;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;

import util.Util;
import java.util.ArrayList;
import java.util.List;

/*
 * This controller contains the SwimCheck app's logic for entries
 */
public class EntryController extends Controller {
	
	private final FormFactory formFactory;

    @Inject
    public EntryController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    //Returns all entries for an athlete
	public Result getEntries(int athleteNo) {
		
		Entry e = new Entry();
		List<Entry> l = new ArrayList<Entry>();
		
		JsonNode entryJson = Json.toJson(e.getAthleteEntries(athleteNo));
	
		if (entryJson == null) {
			return notFound(Util.createResponse(
					"Sorry! We couldn't find any entries for athlete" + athleteNo, false));
		}

		//when using, the ng-repeat in checkin.html must be "e in entries.body"
		//return ok(Util.createResponse(entryJson, true));
		
		//returning without isSuccessful response as I am unable parse that out when checking entries back in
		return ok(entryJson);
	}
	
    //Returns checked in entries for an athlete
	public Result getCheckedInEntries(int athleteNo) {
		
		Entry e = new Entry();
		List<Entry> l = new ArrayList<Entry>();
		
		JsonNode entryJson = Json.toJson(e.getAthleteCheckedInEntries(athleteNo));
	
		if (entryJson == null) {
			return notFound(Util.createResponse(
					"Sorry! We couldn't find any checked in entries for athlete" + athleteNo, false));
		}

		return ok(entryJson);
	}
	
	//Sets scratch status for an entry individually 
	public Result setScratchStatus(int athleteNo, int eventPointer, boolean scratch) {
            
		if(athleteNo == 0) {
            return badRequest("Missing parameter [athleteNo]");
        }
        
        if(eventPointer == 0) {
            return badRequest("Missing parameter [eventPointer]");
        }
	
		Entry.setEntryScratchStatus(athleteNo, eventPointer, scratch);
		
		return ok("Scratch status updated");
	}
	
	//Updates the scratch status after the athlete has selected events on the checkin page
	public Result updateScratchStatus() {
		
		JsonNode entryJson = request().body().asJson();
		
		int athleteNo;
		int eventPointer;
		boolean scratch;
		
		for (int i = 0; i < entryJson.size(); i++) {
		
			athleteNo = entryJson.get(i).findPath("athleteNo").asInt();
			eventPointer = entryJson.get(i).findPath("eventPointer").asInt();
			scratch = entryJson.get(i).findPath("scratch").asBoolean();
			Entry.setEntryScratchStatus(athleteNo, eventPointer, scratch);
		}
				
		return ok("Scratch status updated");
	}
}