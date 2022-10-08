package com.ibm;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TweetListener implements Runnable{

	TwitterStream twitterStream;
	public TweetListener() {
		// TODO Auto-generated constructor stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("V0Qpoydtp6M262sTisu2y8SxJ");
        cb.setOAuthConsumerSecret("RSa1xz8vSV4oXCbpIsWhNSrJq1Qzbz53vxF8R9HGavoS9puO6c");
        cb.setOAuthAccessToken("3308014853-II0xQCB3tCRJznqy4AxQ6AmwTUifUKUSlClPmU7");
        cb.setOAuthAccessTokenSecret("80m0FxwyF7cSQ9wjxbfKvIaVSDh31cakCGJEHBgvGUA6C");
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		StatusListener listener = new StatusListener() {
	        public void onStatus(Status status) {
	        	//Fetches question from user
	            String tweetQues=status.getText();
	            System.out.println("the user question is:"+tweetQues);
	            TwitterAsyncService tas=new TwitterAsyncService();
	            tas.appService(tweetQues);
	        }

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}

	      
	    };
	    twitterStream.addListener(listener);
	    //Listens to tweet from user account only
	    FilterQuery query = new FilterQuery();
	    query.follow(new long[]{3308076790L});
	    twitterStream.filter(query);        
	}

}