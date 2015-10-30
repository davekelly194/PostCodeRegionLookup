package postCodeLookup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*Handles requests, forwarding users onto pages or handling form submissions on pages*/

@Controller
public class RequestController {
    PostCodeLookupEngine engine;
	
    public RequestController(){
      //Initiaise the lookup engine, will be initialised on server startup
      engine = new PostCodeLookupEngine();
	}
	
    //Main controller, brings the user to the home page
    @RequestMapping("/home")
    public String homeRequest(@RequestParam(value="name", required=false, defaultValue="home") String name, Model model) {
    	return name;
    }
    
    //Handles the AJAX request for country search
    @RequestMapping(value = "/getPostCodeCountry")
    @ResponseBody
    //If I needed to expand the parameters to this method I would pass "@RequestBody FormObject pForm" And create a new class for FormObject
    //But for now its just a string...
    public String submitPostCode(@RequestParam("pPostCode") String postCode) {  
        String result = engine.getPostCodeCountry(postCode);
        //If result is null, set it to be not found. We could do this in the getPostCodeCountry func but that might
        //hamper reusability
        if(result==null){
            result="Not found...";
        }
        return result;
    }
}