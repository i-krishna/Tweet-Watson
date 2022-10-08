package com.ibm;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class TweetContextListener implements ServletContextListener {

	public TweetContextListener() {
		super();
		System.out.print("In Constructor");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("TweetContextListener started");
		Thread t=new Thread(new TweetListener());
		t.start();
	}

}
