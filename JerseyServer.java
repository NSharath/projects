package restservice.serv;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

@Path("/")
public class JerseyServer {
	
	

	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService1(InputStream incomingData) {
		String result = "Web Service Successfully started..";

		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
	
	
	
	@GET
	@Path("/clientsave")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "Client Service Successfully started..";
		try{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

	
		DB db = mongoClient.getDB("clientdb");
		String collectionName = "users";
		
		if(!db.collectionExists(collectionName))
		{
		  //I can confirm that the collection is created at this point.
		  DBCollection school = db.createCollection(collectionName, new BasicDBObject());      
		  //I would highly recommend you check the 'school' DBCollection to confirm it was actually created
		}
        return Response.status(200).entity(result).build();
		}
		catch(Exception e)
		{
			return Response.status(200).entity(e.toString()).build();
		}
		// return HTTP response 200 in case of success
		
	}
	
	
	
	
	
	@POST
	@Path("/factoryboot")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response chat2(InputStream incomingData) {
		System.out.println("Hii");
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());

		// return HTTP response 200 in case of success
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			DB db = mongoClient.getDB("clientdb");
			DBCollection col = db.getCollection("users");
			DBObject dbObject = (DBObject) JSON.parse(crunchifyBuilder.toString());
			WriteResult result = col.insert(dbObject);
			
			
			
			
	        return Response.status(200).entity(result).build();
			}
			catch(Exception e)
			{
				return Response.status(200).entity(e.toString()).build();
			}
		
	}
	
	
	
	
	
	@POST
	@Path("/clientboot")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response chat3(InputStream incomingData) {
		System.out.println("Hii");
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());

		
		// return HTTP response 200 in case of success
		try{
			JSONObject obj = new JSONObject(crunchifyBuilder.toString());
			String mfg = obj.getString("mfg");
			String hwid = obj.getString("hwid");
			String mod = obj.getString("mod");
			String regserver = obj.getString("regserver");
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			DB db = mongoClient.getDB("clientdb");
			DBCollection col = db.getCollection("users");
			
			BasicDBObject allQuery = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject();
			allQuery.put("hwid",hwid);
			
			DBCursor cursor = col.find(allQuery,fields);
			String rec="";
			while (cursor.hasNext()) {
				rec= cursor.next().get("regserver").toString();
				
			}
			
			DB dbc = mongoClient.getDB("clientdb");
			DBCollection colc = dbc.getCollection("users");
			
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("regserver", rec));

			BasicDBObject searchQuery = new BasicDBObject().append("hwid", hwid);

			colc.update(searchQuery, newDocument);
			
			
			System.out.println("red"+rec);
	        return Response.status(200).entity(rec).build();
			}
			catch(Exception e)
			{
				return Response.status(200).entity(e.toString()).build();
			}
		
	}
	
	
	@PUT
	@Path("/regupdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response chat4(InputStream incomingData) {
		System.out.println("Hii");
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());

		
		// return HTTP response 200 in case of success
		try{
			JSONObject obj = new JSONObject(crunchifyBuilder.toString());
			
			String hwid = obj.getString("hwid");
			String phone = obj.getString("phone");
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			DB db = mongoClient.getDB("clientdb");
			DBCollection col = db.getCollection("users");
			
			BasicDBObject newDoc = new BasicDBObject();
			newDoc.append("$set", new BasicDBObject().append("phone", phone));

			BasicDBObject searchQ = new BasicDBObject().append("hwid", hwid);

			col.update(searchQ, newDoc);

			
			DB dbc = mongoClient.getDB("clientdb");
			DBCollection colc = dbc.getCollection("users");
			
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("phone", phone));

			BasicDBObject searchQuery = new BasicDBObject().append("hwid", hwid);

			colc.update(searchQuery, newDocument);
		
	        return Response.status(200).build();
			}
			catch(Exception e)
			{
				return Response.status(200).entity(e.toString()).build();
			}
		
	}
	
	
	@POST
	@Path("/regserver")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response chat5(InputStream incomingData) {
		System.out.println("Hii");
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());

		// return HTTP response 200 in case of success
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			DB db = mongoClient.getDB("clientdb");
			DBCollection col = db.getCollection("users");
			DBObject dbObject = (DBObject) JSON.parse(crunchifyBuilder.toString());
			WriteResult result = col.insert(dbObject);
			
	        return Response.status(200).build();
			}
			catch(Exception e)
			{
				return Response.status(200).entity(e.toString()).build();
			}
		
	}
	
	
	
	@POST
	@Path("/dreg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response chat6(InputStream incomingData) {
		System.out.println("Hii");
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());

		// return HTTP response 200 in case of success
		try{
			JSONObject obj = new JSONObject(crunchifyBuilder.toString());
			String hwid = obj.getString("hwid");
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			DB db = mongoClient.getDB("clientdb");
			DBCollection col = db.getCollection("users");
			BasicDBObject document = new BasicDBObject();
			document.put("hwid", hwid);
			col.remove(document);
			
	        return Response.status(200).entity("Success").build();
			}
			catch(Exception e)
			{
				return Response.status(200).entity(e.toString()).build();
			}
		
	}
	
	
}
