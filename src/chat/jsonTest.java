/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Tauwab Uz Zahir
 */
public class jsonTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, IOException {
        // TODO code application logic here
        JSONParser parser = new JSONParser();
        String fullPath = "userInfo.json";
        //BufferedReader reader = new BufferedReader(new FileReader(fullPath));
    
      Object obj = parser.parse(new FileReader("userInfo.json"));
      JSONObject jsonObject = (JSONObject) obj;
    /*while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }*/
      JSONArray lang= (JSONArray) jsonObject.get("userInfo");
       Iterator i = lang.iterator();

            // take each value from the json array separately
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                System.out.println("username "+ innerObj.get("username") +
                        " with password " + innerObj.get("password"));
            }


    }
    
}
