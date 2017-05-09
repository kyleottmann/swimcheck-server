package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Athlete;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import util.Util;

import java.util.ArrayList;
import java.util.List;

/*
 * This controller contains the SwimCheck app's common logic
 */
public class AthleteController extends Controller {
	
	private final FormFactory formFactory;

    @Inject
    public AthleteController(final FormFactory formFactory) {
        this.formFactory = formFactory;
    }

	public Result findathlete() {
		DynamicForm requestData = formFactory.form().bindFromRequest();
		String lastname = requestData.get("lastname");
		String birthdate = requestData.get("birthdate");
		
		Athlete a = new Athlete();
		List<Athlete> l = new ArrayList<Athlete>();
		
		JsonNode athleteJson = Json.toJson(a.findAthleteByLastNameBirthdate(lastname, birthdate));
	
		if (athleteJson == null) {
			return notFound(Util.createResponse(
					"Sorry! We couldn't find an athlete with the last name " + lastname + " with a birthdate of " + birthdate + ".", false));
		}

		return ok(Util.createResponse(athleteJson, true));
	}
	
	public Result getAthleteInfo(int athleteNo) {
		
		Athlete a = new Athlete();
		
		JsonNode athleteJson = Json.toJson(a.findAthleteByAthleteNumber(athleteNo));
	
		if (athleteJson == null) {
			return notFound(Util.createResponse(
					"Sorry! We couldn't find any athlete's with Athlete ID:" + athleteNo, false));
		}

		return ok(Util.createResponse(athleteJson, true));
	}
	
//
//  public 	static Result getPosts() {
//    return ok(Json.toJson(BlogPost.find.findList()));
//  }
//
//  public static Result getPost(Long id) {
//    BlogPost blogPost = BlogPost.findBlogPostById(id);
//    if(blogPost == null) {
//      return notFound(buildJsonResponse("error", "Post not found"));
//    }
//    return ok(Json.toJson(blogPost));
//  }
//
//  public static class UserForm {
//    @Constraints.Required
//    @Constraints.Email
//    public String email;
//  }
//
//  public static class SignUp extends UserForm {
//    @Constraints.Required
//    @Constraints.MinLength(6)
//    public String password;
//  }
//
//  public static class Login extends UserForm {
//    @Constraints.Required
//    public String password;
//  }


}
