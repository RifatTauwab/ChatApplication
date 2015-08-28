package chat;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
    
    public String filePath;
    JSONParser parser = new JSONParser();
       
    
      
    public Database(String filePath){
        this.filePath = filePath;
        
    }
    
    public boolean userExists(String username){
        
        try{
            
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray lang= (JSONArray) jsonObject.get("userInfo");
       Iterator i = lang.iterator();

            // take each value from the json array separately
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                 if(innerObj.get("username").equals(username)){
                        return true;
                    }
            }
                   
                
            return false;
        }
        catch(Exception ex){
            System.out.println("Database exception : userExists()");
            return false;
        }
    }
    
    public boolean checkLogin(String username, String password){
        
        if(!userExists(username)){ return false; }
        
        try{
           Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray lang= (JSONArray) jsonObject.get("userInfo");
       Iterator i = lang.iterator();

            // take each value from the json array separately
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                 if(innerObj.get("username").equals(username) && innerObj.get("password").equals(password)){
                        return true;
                    }
            }
                   
                
            return false;
       
        }
        catch(Exception ex){
            System.out.println("Database exception : userExists()");
            return false;
        }
    }
    
    
}
