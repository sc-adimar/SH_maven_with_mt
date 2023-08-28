package com.demo.demooo;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.client.MongoCollection;

public class multi_hall implements Runnable  {
	details d;
	MongoCollection<org.bson.Document> collection;

	
	
	public multi_hall(details d, MongoCollection<org.bson.Document> collection) {
		super();
		this.d = d;
		this.collection = collection;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		App a = new App();
		a.bookHall(d.startDate , d.endDate,d.capacity, d.hallName,collection);
		
	}

}
