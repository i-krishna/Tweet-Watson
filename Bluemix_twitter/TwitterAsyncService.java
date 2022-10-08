package com.ibm;

import java.util.List;
import java.util.Locale;

import twitter4j.*;

public class TwitterAsyncService{
	
	private WatsonTranslate wt = new WatsonTranslate();
	Locale tweetLang=null;
	String ansEng=null,tweetLanguage=null;
	String userLang=null;
	String translatetoLang=null;
	String userQuestionEng=null;
	String ansUser=null;
	String ansUserPost[]=new String[100];
	int ansLen=0;
	
	public void appService(String tweetQues) 
	{	
		Twitter twitter = TwitterFactory.getSingleton();
        
        // To fetch latest tweet from user account
		List<Status> statuses=null;
		
        try
        {
        		statuses = twitter.getHomeTimeline();
        } 
        catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}           
            
        
        //Using language identification service to identify the user language
        tweetLanguage = wt.identify(tweetQues);
        wt.translate(tweetQues);
        
       /* 
        tweetLang = Locale.forLanguageTag(wt.identify(tweetQues));
        userLang=tweetLang.getDisplayLanguage().toString();
        System.out.println("The language of the question is:"+userLang);
        if(userLang!="English")
        {
        switch(userLang)
        {
        	case "Spanish":
        		translatetoLang="mt-eses-enus";
        		break;
        		
        	case "French":
        		translatetoLang="mt-frfr-enus";
        		break;
        		
        	case "Portuguese":
        		translatetoLang="mt-ptbr-enus";
        		break;
        }
        //Using machine translation service to translate the tweet to English
        //translatetoLang=new String("mt-eses-enus");
        System.out.println("translatetoLang Ques=:"+translatetoLang);
        userQuestionEng=wt.translate(tweetQues,translatetoLang).toString();
        System.out.println("The question in English:"+userQuestionEng);
        }
        else
        {
        	userQuestionEng=tweetQues;
        }*/
        //Using question answer service to fetch answer
        userQuestionEng=tweetQues;
        ansEng=wt.QuestionAnswer(userQuestionEng);
        ansEng=ansEng.substring(6);
        ansEng=ansEng.substring(0, ansEng.length()-1);
        //System.out.println("The question in English:"+userQuestionEng);
       /*if(userLang!="English")
        {
        switch(userLang)
        {
        	case "Spanish":
        		translatetoLang="mt-enus-eses";
        		break;
        		
        	case "French":
        		translatetoLang="mt-enus-frfr";
        		break;
        		
        	case "Portuguese":
        		translatetoLang="mt-enus-ptbr";
        		break;
        }
        
      //Using machine translation service to translate the tweet from English to User language
        //translatetoLang=new String("mt-enus-eses");
        System.out.println("translatetoLang Ans:"+translatetoLang);
        ansUser=wt.translate(ansEng,translatetoLang).toString();
        }
        else
        {
        	ansUser=ansEng;
        }*/
        ansUser=ansEng;
        System.out.println("The answer in "+userLang+" is:"+ansUser);
        //To post tweet to user account
        ansLen=ansUser.length();
        int j=0;
        int i=0;
        int k=0;
        for(i=0;i<ansLen;i+=139)
	    {
        	if(ansLen-i>=140)
        	{
        		ansUserPost[j]=ansUser.substring(i, i+139);
        		j=j+1;
        		
        	}
        	else
        	{
        		ansUserPost[j]=ansUser.substring(i);
        	}
	    }
        try 
        {
        	for(k=0;k<j+1;k++)
        	{
        		String ansPost=null;
        		ansPost=ansUserPost[k];
        		twitter.updateStatus(ansPost);
        	}
    		System.out.println("Successfully posted the answer");
    		
    	} 
        catch (TwitterException e1) 
        {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    	}
	}
}
