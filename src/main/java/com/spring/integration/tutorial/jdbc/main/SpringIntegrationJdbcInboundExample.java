package com.spring.integration.tutorial.jdbc.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

public class SpringIntegrationJdbcInboundExample {

	public static void main(String args[]) throws InterruptedException, IOException, SQLException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbcInboundApplicationContext.xml");
		
		try {
			
			PollableChannel pollableChannel = (PollableChannel) context.getBean("dataChannel");
			@SuppressWarnings("unchecked")
			Message<List<Map<String, Object>>> msg = (Message<List<Map<String, Object>>>) pollableChannel.receive(1000);
			
			List<Map<String, Object>> rows = msg.getPayload();
			for(Map<String, Object> row : rows) {
				
				String name = (String) row.get("name");
				String category = (String) row.get("category");
				String author = (String) row.get("author");
				System.out.println(name + " - " + category + " - " + author);
				
			}
			
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		finally {
			
			context.close();
			
		}
	}
	
}
