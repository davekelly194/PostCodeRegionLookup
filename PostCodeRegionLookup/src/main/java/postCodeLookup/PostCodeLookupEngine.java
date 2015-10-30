package postCodeLookup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class PostCodeLookupEngine {
  private String fieldSeperator=",";  
  private FileReader postCodesFileReader;
  private HashMap<String, String> postCodesMap;
  //To find the current working directory, print this into the logs..
  //System.out.println(new File(".").getAbsolutePath());    
  private String postCodesFileLocation="./src/main/resources/static/postCodes.csv";
  
  public PostCodeLookupEngine(){
    postCodesMap = new HashMap<String,String>();    
    loadCSVFileIntoHashmap();
  }
  
  /**Load the CSV file into memory, this will be called on startup by the constructor(which in turn is called on startup
  by the request controller**/
  private void loadCSVFileIntoHashmap(){
    try {
      postCodesFileReader = new FileReader(postCodesFileLocation);
      String line;
      //Open up the buffered reader so we can start looping through our CSV file
      BufferedReader br = new BufferedReader(postCodesFileReader);
      while ((line = br.readLine()) != null) {
        String[] country = line.split(fieldSeperator);
        if(country.length<=3){
          //Add the region code and matching country to the hashmap
          postCodesMap.put(country[0].trim(), country[2].trim());
        }
      }
      br.close();
    } 
    catch (Exception e) {
      //Ideally we would use a custom log file, but for the sake of a quick demo use the stderr  
      e.printStackTrace();
    }     
  }
  
  public String getPostCodeCountry(String pPostCode){
	String foundCountry=null;
    try {
      if(pPostCode==null || pPostCode.length()==0){
          return null;
      }

      //Strip out all trailing whitespace, and any white space within the postcode
      pPostCode = pPostCode.trim();
      pPostCode = pPostCode.replaceAll("\\s+","");
      //Strip out the incode, which will always be an alphanumeric number(length 3)
      pPostCode = pPostCode.substring(0, pPostCode.length()-3);
      //Strip out the numeric part of the outcode
      pPostCode = pPostCode.replaceAll("[^A-Za-z]","");
      foundCountry = postCodesMap.get(pPostCode);
      
	}  
	catch (Exception e) {
	  //Ideally we would use a custom log file, but for the sake of a quick demo use the stderr
	  e.printStackTrace();
	}
	return foundCountry;
  }
}
