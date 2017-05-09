package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import util.Util;

public class SessionController extends Controller {
	
	public Result startAthleteSession(int athleteNo) {
		if (athleteNo != 0) {
			session().clear();
			session("athleteNo", String.valueOf(athleteNo));
		
			return ok(Util.buildJsonResponse("athleteNo", String.valueOf(athleteNo)));
		}
		else {
			return badRequest(Util.buildJsonResponse("error", "athleteNo cannot be 0"));
		}
	}
	
	public  Result endAthleteSession() {
		session().clear();
		return ok(Util.buildJsonResponse("success", "Logged out successfully"));
  }

	public Result athleteIsSelected() {
		if(session().get("athleteNo") == null) {
			return unauthorized();
		} else {
			return ok(Util.buildJsonResponse("athleteNo", session().get("athleteNo")));
		}
	}
}
