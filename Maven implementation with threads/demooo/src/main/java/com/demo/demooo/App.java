package com.demo.demooo;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;

import org.apache.naming.java.javaURLContextFactory;
import org.apache.tomcat.util.json.ParseException;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonReader;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.Assert;
//
//import com.example.demo.detail.Sdetails;
//import com.example.demo.detail.details;
//import com.example.demo.detail.multi_hall;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import springfox.documentation.spring.web.json.Json;

public class App {
	 private static Map<String, Integer> hallCapacities = new HashMap<>();
	 
    public static void main(String[] args ) {
        if (args.length < 1) {
            System.out.println("Please provide task no. and relevant inputs");
            return;
        }

        
        
        MongoCollection<Document> collection  = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        
        String task = args[0];
        if(args.length >1) {
        String jsonString = args[1];
        System.out.println("input string is "+jsonString);
        
//        Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy HH:mm:ss").create();

        
//        JsonObject j = JsonParser.parseString("string").getAsJsonObject();
//        detail jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
       
//        System.out.println("converted: "+convertedObject[0].getAsString());

        try {
        jsonArray = new JSONArray(jsonString);
        jsonObject = jsonArray.getJSONObject(0);
        }
        catch(JSONException e) {
        System.out.println("exc. is"+e);	
        }
        }
//        Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy HH:mm:ss").create();
//        detail convertedObject = gson.fromJson(jsonString, detail.class);
//        System.out.println("working "+convertedObject.getStartDate());
        
        try {
        MongoDBSingleton mongoDBSingleton = MongoDBSingleton.getInstance();

        // Use the MongoDB client to perform database operations
        MongoClient mongoClient = mongoDBSingleton.getMongoClient();

        
        MongoDatabase database = mongoClient.getDatabase("College");
         collection = database.getCollection("SH");
        }
        catch(IllegalArgumentException e) {
        	System.out.println(e);
        }
//          Bson filter = Filters.gte("age", 25);
//          try(MongoCursor<Document> cursor =  collection.find().iterator())
//          {
//              while(cursor.hasNext()) {
//                  System.out.println(cursor.next().toJson());
//              }
//          }
        
        details d = new details();
        
        
        
        hallCapacities.put("Hall-A", 50);
        hallCapacities.put("Hall-B", 80);
        hallCapacities.put("Hall-C", 100);
        hallCapacities.put("Hall-D", 60);
        hallCapacities.put("Hall-E", 120);
        hallCapacities.put("Hall-F", 150);
        
        if(args.length >1) {
        String a = jsonObject.getString("startDate");
        String b = jsonObject.getString("endDate");
        d.capacity = jsonObject.getString("capacity");
        d.hallName = jsonObject.getString("hallName");
        
        LocalDateTime sd = LocalDateTime.parse(a, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
        LocalDateTime ed = LocalDateTime.parse(b, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
        

        d.startDate = sd;
        d.endDate = ed;
        
        
        System.out.println("startDate: " + d.startDate);
        System.out.println("endDate: " + d.endDate);
        System.out.println("capacity: " + d.capacity);
        System.out.println("hallName: " + d.hallName);
        }
        
       
       
        
        switch (task) {
        case "Task-1":
			System.out.println( viewVacantOnDatee(d.startDate , d.endDate,d.capacity, collection));
            break;
        case "Task-2":
            System.out.println( bookHall(d.startDate , d.endDate,d.capacity, d.hallName,collection));
            break;
       
        case "Task-3":
        	System.out.println(bookedHallOnDate(d.startDate , d.endDate,collection));
            break;
            
        case "Task-4":
        	bookmultiple(collection);
        	break;
        	
        	
        	
       
        default:
            System.out.println("Invalid choice. Please try again.");
    }

    System.out.println();


        }
    
    
   
    private static void bookmultiple(MongoCollection<Document> collection) {
    	System.out.println("multiple hall bookine is running");
    	Sdetails[] detaillist = readjson();
		
        int jarray_length = detaillist.length;
 		System.out.println("length of json array : "+jarray_length);
 		details det[] = new details[jarray_length];
 		for(int i = 0; i < jarray_length;  i++ ) {
 			det[i] = new details();
 			
// 			jsonObject
 			
 			String a = detaillist[i].getStartDate();
 	        String b = detaillist[i].getEndDate();
 	        det[i].capacity = detaillist[i].getCapacity();
 	        det[i].hallName = detaillist[i].getHallName();
 	        
 	       LocalDateTime sd = LocalDateTime.parse(a, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
 		   LocalDateTime ed = LocalDateTime.parse(b, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
 	        

 	        det[i].startDate = sd;
 	        det[i].endDate = ed;
 	        }
		

		int batchsize_of_thread = 10;
 		int mult_val = jarray_length/10;
 		 
 		System.out.println("length is  :"+det.length);
 		System.out.println("mult+val :"+mult_val);
// 		
//// 		List<Thread> lot  = new ArrayList<Thread>;
 		Thread[][] lot = new Thread[mult_val][batchsize_of_thread];
// 		
// 		//fill batch
 		for(int i = 0; i < mult_val ;i++ ) 
 		{
 			for(int j = 0;j < batchsize_of_thread; j++) 
 			  {
 			     if(det[i*batchsize_of_thread+j] != null) 
 			     {
// 			    	System.out.println(i*batchsize_of_thread+j);
 			       lot[i][j] = (new Thread(new multi_hall(det[i*batchsize_of_thread+j], collection)));
 			     }
 				int index = i*batchsize_of_thread+j;
 				System.out.println("i is "+i);
 				System.out.println("j is "+j);
 			    System.out.println("index is "+index);
// 			  
 			
 		}
 			}
 			
// 		System.out.println(lot[1][5]);
// 		
 			for(int a = 0; a < mult_val ; a++ ) 
	 		{
	 			for(int b = 0;b < batchsize_of_thread; b++) 
	 			{
	 				System.out.println("a is "+a);
	 				System.out.println("b is "+b);
	 				
	 		      lot[a][b].start();
//	 		      try 
//	 		         {
//	 				  Thread.sleep(10);
//	 				  System.out.println(a*batchsize_of_thread+b);
//	 			     } 
//	 		      catch (InterruptedException e) 
//	 		      {
//	 				// TODO Auto-generated catch block
//	 				e.printStackTrace();
//	 			  }
	 			
	 				
	 			}
	 		}
}	
			
		
		
		
	
    
    
    
	private static com.demo.demooo.Sdetails[] readjson() {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
        Sdetails[] detaillist = null ;
        try {
            File jsonFile = new File("C:\\Users\\akadi\\OneDrive\\Documents\\sts_workspace\\semi_rest\\src\\main\\resources\\detail.json");
             detaillist = objectMapper.readValue(jsonFile, Sdetails [].class);
//            List<details> people = objectMapper.readValue(jsonFile, new TypeReference<List<details>>() {});

//            System.out.println("start date is : " + detail.length);
//            System.out.println("Age: " + person.getAge());
//            System.out.println("Email: " + person.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return detaillist;
		
	}



	private static JSONArray bookedHallOnDate(LocalDateTime startDate, LocalDateTime endDate,MongoCollection<Document> collection) {
		// TODO Auto-generated method stub
		Bson filt = Filters.and(Filters.gte("startDate",startDate ), Filters.lte("endDate", endDate));
		System.out.println("start date is "+startDate);
		System.out.println("end date is "+endDate);
		MongoCursor<Document> cursor = collection.find(filt).iterator();
//		List<String> al = new ArrayList<Document>();
		JSONArray al = new JSONArray();
		Gson gso = new Gson();
		 while(cursor.hasNext()) {
//			 String ab = cursor.next().getString("hallName");
			 String ne = cursor.next().toJson();
			 
//			    JSONObject temp = 
			
//		        System.out.println("ne is "+ne);
//			 String jsonString = gso.toJson(cursor.next());
		        al.put(ne);
		        
		    
		    }
//		 System.out.println(al.toString());
//		 System.out.println(al.toString());
//		 JSONArray jA = new JSONArray(al);
//	        JSONObject jo = al.getJSONObject(0);
//	        String capacit = jo.getString("capacity");
//	        System.out.println(capacit);
		 return al;
//

//		
	}
	
	 static BsonValue bookHall(LocalDateTime startDate, LocalDateTime endDate, String capacity, String hallName,MongoCollection<Document> collection) {
		// TODO Auto-generated method stub
		int capacityy = Integer.parseInt(capacity);  
		System.out.println("startDate is : "+startDate);
		System.out.println("hallName2: " );
		Bson filter = Filters.and(Filters.and(Filters.gte("startDate",startDate ), Filters.lte("endDate",endDate)),Filters.eq("hallName",hallName));
		MongoCursor<Document> cursor = collection.find(filter).iterator();
		BsonValue id = null;
		
//		while(cursor.hasNext()) {
//	        System.out.println(cursor.next().toJson());
//	    }
		if(cursor.hasNext() ) {
			
			System.out.println("Try again with differnt input" );
//			System.out.println(cursor.next().toJson());
		}
		
		else{
//			System.out.println("Try again with differnt input" );
			if(capacityy <= hallCapacities.get(hallName)  )
			{
			Document doc = new Document().append("startDate",startDate).append("endDate",endDate).append("capacity",capacity).append("hallName",hallName);
			InsertOneResult result = collection.insertOne((Document) doc);
		
			id = result.getInsertedId();
			System.out.println("booked:"+ hallName);
//			return id;
//			System.out.println("id created: " +id);
			}
			else {
				System.out.println("Required hall capacity is less than you demanded");
			}
			
		}
		
		return id;
		
	}
//	private static void viewVacantOnDate(LocalDateTime startDate, LocalDateTime endDate, String capacity,MongoCollection<Document> collection) {
//		// TODO Auto-generated method stub
//		
//		JSONObject start = new JSONObject();
//		start.put("startDate",startDate.toString());
//		
//		JSONObject end = new JSONObject();
//		end.put("endDate",endDate.toString());
//		
//		JSONObject capacit = new JSONObject();
//		capacit.put("capacity",capacity.toString());
//		
//		Map<String, Boolean> status = new HashMap<>();
//		Map<String, String>  res = new HashMap<>();
//		
//		for(String hall : hallCapacities.keySet()) {
//		status.put(hall,false);
//		}
//		
//		
//		
//		ArrayList<String> hallList = new ArrayList<String>();
//		
//		Bson filt = Filters.and(Filters.eq("startDate",startDate ), Filters.eq("endDate", endDate));
//		System.out.println("start date is "+startDate);
//		System.out.println("end date is "+endDate);
//		MongoCursor<Document> cursor = collection.find(filt).iterator();
//		
//		 while(cursor.hasNext()) {
//			    String j = (String) cursor.next().get("hallName");
//			    if(Integer.parseInt(capacity) <= hallCapacities.get(j)) {
////			    hallList.add(j);
//			    status.replace(j, true);
//			    
////		        System.out.println(cursor.next().toJson().getString("hallName"));
////			    System.out.println("Booked room is  "+ j+" and ");
//			    }
//		        
//		    }
//		 
////		 System.out.println("Rest of the halls are vacant for this date " );
////		 System.out.println(status);
//		 for(String hall : hallCapacities.keySet()) {
//			 if(status.get(hall) == false)
//				hallList.add(hall);
//				}
//		 JsonObject rest = new JsonObject();
////		 res.addProperty("startDate",startDate.toString());
////		 res.addProperty("endDate",endDate.toString());
////		 res.addProperty("capacity",capacity);
//		 
////		 res.put("startDate",startDate.toString());
////		 res.put("endDate",endDate.toString());
////		 res.put("capacity",capacity);
////		 res.put();
//		 
//
//		 
//		 Gson gson = new Gson();
//		 String jsonArray = gson.toJson(hallList);
//		 JsonObject arr = new JsonObject();
//		 arr.addProperty("available halls are : ", hallList.toString());
//		 
////		 Assert.assertEquals(expectedJsonArray, jsonArray);
//		 rest.addProperty("available",jsonArray);
////		 String ress = gson.toJson(res);
//		 
//		 
////	     JsonArray j = new JsonArray(ress);
//		 JSONArray j = new JSONArray();
////		 
////		 j.put(jsonObject);
//		 j.put("start date :"+String.format(start.getString("startDate")));
//		 j.put(end);
//		 j.put(capacit);
//		 j.put(arr);
////		 j.put(jsonArray);
//		 System.out.println(j);
//		
//		
////		with Json representation of string
////		 System.out.println(rest);
//		 
//		
//		
//	}
	    
	
	private static JSONArray viewVacantOnDatee(LocalDateTime startDate, LocalDateTime endDate, String capacity,MongoCollection<Document> collection) {
		// TODO Auto-generated method stub
		
		
		
		Map<String, Boolean> status = new HashMap<>();
		Map<String, String>  res = new HashMap<>();
		
		for(String hall : hallCapacities.keySet()) {
		status.put(hall,false);
		}
		
		
		
		ArrayList<String> hallList = new ArrayList<String>();
	
		
		Bson filt = Filters.and(Filters.eq("startDate",startDate ), Filters.eq("endDate", endDate));
		System.out.println("start date is "+startDate);
		System.out.println("end date is "+endDate);
		MongoCursor<Document> cursor = collection.find(filt).iterator();
		
		 while(cursor.hasNext()) {
			    String j = (String) cursor.next().get("hallName");
			    if(Integer.parseInt(capacity) <= hallCapacities.get(j)) {

			    status.replace(j, true);
			    
			    }
		        
		    }
		 
		 JSONArray hl = new JSONArray();

		 for(String hall : hallCapacities.keySet()) {
			 if(status.get(hall) == false) {
				hallList.add(hall);
				hl.put(hall);}
				}
	
		 JSONObject obj = new JSONObject();
		 obj.put("startDate ",startDate.toString());
		 obj.put("endDate", endDate.toString());
		 obj.put("capacity : ", capacity);
//		 System.out.println(obj);
		 obj.put("available halls :", hallList.toString());
//		 System.out.println(obj);
		 
//		 GSON a = new GSON();
		 Gson gson = new Gson();
		 String jsonArray = gson.toJson(hallList);
//		 details deserializedUser = gson.fromJson(obj, details.class);
		 
//		 obj.put("json one",jsonArray);
		 
		 JSONArray ress = new JSONArray();
		 ress.put(obj);
	
		 
		 JSONObject ob = new JSONObject();
		 
		 String s = "slash";
		 ob.put(s,jsonArray.toString());
//		 System.out.println(obj);
//		 System.out.println(hl);
//		 System.out.println(ress);
		 return ress;

		
		
	}
	
	
	
        
    }


