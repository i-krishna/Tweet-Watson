package com.ibm;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;





import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

public class WatsonTranslate {
	private static Logger logger = Logger.getLogger(WatsonTranslate.class.getName());

	private static String translationService = "machine_translation";
	private static String languageService = "language_identification";
	private static String questionAnswerService = "question_and_answer";

	// If running locally add the information from VCAP_SERVICES
	private static String baseURLTranslation = "put url here";
	private static String usernameTranslation = "put username here";
	private static String passwordTranslation = "put password here";

	private static String baseURLLanguage = "put url here";
	private static String usernameLanguage = "put username here";
	private static String passwordLanguage = "put password here";
	
	private static String baseURLQuestionAnswer = "put url here";
	private static String usernameQuestionAnswer = "put username here";
	private static String passwordQuestionAnswer = "put password here";

	static {
		processVCAP_Services();
	}

	private static void processVCAP_Services() {
    	logger.info("Processing VCAP_SERVICES");
        JSONObject sysEnv = getVcapServices();
        if (sysEnv == null) return;


				logger.info("Looking for: "+ translationService);

        if (sysEnv.containsKey(translationService)) {
					JSONArray services = (JSONArray)sysEnv.get(translationService);
					JSONObject service = (JSONObject)services.get(0);
					JSONObject credentials = (JSONObject)service.get("credentials");
					baseURLTranslation = (String)credentials.get("url");
					usernameTranslation = (String)credentials.get("username");
					passwordTranslation = (String)credentials.get("password");
					logger.info("baseURL  = "+baseURLTranslation);
					logger.info("username   = "+usernameTranslation);
					logger.info("password = "+passwordTranslation);
    		} else {
        	logger.warning(translationService + " is not available in VCAP_SERVICES, "
        			+ "please bind the service to your application");
        }

				logger.info("Looking for: "+ languageService);

				if (sysEnv.containsKey(languageService)) {
					JSONArray services = (JSONArray)sysEnv.get(languageService);
					JSONObject service = (JSONObject)services.get(0);
					JSONObject credentials = (JSONObject)service.get("credentials");
					baseURLLanguage = (String)credentials.get("url");
					usernameLanguage = (String)credentials.get("username");
					passwordLanguage = (String)credentials.get("password");
					logger.info("baseURL  = "+baseURLLanguage);
					logger.info("username   = "+usernameLanguage);
					logger.info("password = "+passwordLanguage);
				} else {
					logger.warning(languageService + " is not available in VCAP_SERVICES, "
							+ "please bind the service to your application");
				}
				
				logger.info("Looking for: "+ questionAnswerService);

				if (sysEnv.containsKey(questionAnswerService)) {
					JSONArray services = (JSONArray)sysEnv.get(questionAnswerService);
					JSONObject service = (JSONObject)services.get(0);
					JSONObject credentials = (JSONObject)service.get("credentials");
					baseURLQuestionAnswer = (String)credentials.get("url");
					usernameQuestionAnswer = (String)credentials.get("username");
					passwordQuestionAnswer= (String)credentials.get("password");
					logger.info("baseURL  = "+baseURLQuestionAnswer);
					logger.info("username   = "+usernameQuestionAnswer);
					logger.info("password = "+passwordQuestionAnswer);
				} else {
					logger.warning(questionAnswerService + " is not available in VCAP_SERVICES, "
							+ "please bind the service to your application");
				}

    }

	private static JSONObject getVcapServices() {
        String envServices = System.getenv("VCAP_SERVICES");
        if (envServices == null) return null;
        JSONObject sysEnv = null;
        try {
        	 sysEnv = JSONObject.parse(envServices);	
        } catch (IOException e) {
        	// Do nothing, fall through to defaults
        	logger.log(Level.SEVERE, "Error parsing VCAP_SERVICES: "+e.getMessage(), e);
        }
        return sysEnv;
    }

	public String QuestionAnswer(String text) {
		String tweetAns = null;
		String question = text;
		JSONObject questionJson = new JSONObject();
		questionJson.put("questionText",question);
		JSONObject evidenceRequest = new JSONObject();
		evidenceRequest.put("items",5);
		questionJson.put("evidenceRequest",evidenceRequest);
		JSONObject postData = new JSONObject();
		postData.put("question",questionJson);
		try {
			String dataset="travel";
			Executor executor = Executor.newInstance().auth(usernameQuestionAnswer, passwordQuestionAnswer);
    		URI serviceURI = new URI(baseURLQuestionAnswer+ "/v1/question/"+dataset).normalize();
    		String answersJson = executor.execute(Request.Post(serviceURI)
			    .addHeader("Accept", "application/json")
			    .addHeader("X-SyncTimeout", "30")
			    .bodyString(postData.toString(), ContentType.APPLICATION_JSON)
			    ).returnContent().asString();
    	    List<Map<String, String>> answers = formatAnswers(answersJson);
    	    tweetAns=answers.get(0).toString();
    	    System.out.println(answers.get(0).toString());
    	    	
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Watson error: "+e.getMessage(), e);
		}
		return tweetAns;
	}
	private List<Map<String,String>> formatAnswers(String resultJson) {
		List<Map<String,String>> ret = new ArrayList<Map<String,String>>();
		try {
			JSONArray pipelines = JSONArray.parse(resultJson);
			// the response has two pipelines, lets use the first one
			JSONObject answersJson = (JSONObject) pipelines.get(0);
			JSONArray answers = (JSONArray) ((JSONObject) answersJson.get("question")).get("evidencelist");

			for(int i = 0; i < answers.size();i++) {
				JSONObject answer = (JSONObject) answers.get(i);
				Map<String, String> map = new HashMap<String, String>();

				double p = Double.parseDouble((String)answer.get("value"));
				p = Math.floor(p * 100);
				map.put("confidence",  Double.toString(p) + "%");
				map.put("text", (String)answer.get("text"));

				ret.add(map);
			}
		} catch (IOException e) {
    	 logger.log(Level.SEVERE, "Error parsing the response: "+e.getMessage(), e);
       }
		return ret;
	}

	public String translate(String text, String sid) {
		String tweetTranslation = "";
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("txt",text ));
		qparams.add(new BasicNameValuePair("sid",sid ));
		qparams.add(new BasicNameValuePair("rt","text" ));
		try {
			Executor executor = Executor.newInstance();
    		URI serviceURI = new URI(baseURLTranslation).normalize();
    		String auth = usernameTranslation + ":" + passwordTranslation;
    	    byte[] response = executor.execute(Request.Post(serviceURI)
			    .addHeader("Authorization", "Basic "+ Base64.encodeBase64String(auth.getBytes()))
			    .bodyString(URLEncodedUtils.format(qparams, "utf-8"),
			    		ContentType.APPLICATION_FORM_URLENCODED)
			    ).returnContent().asBytes();
    	    tweetTranslation = new String(response, "UTF-8");
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Watson error: "+e.getMessage(), e);
		}
		return tweetTranslation;
		
	}



	public String identify(String text) {
		String language = "";
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("txt",text));
		qparams.add(new BasicNameValuePair("sid","lid-generic"));
		qparams.add(new BasicNameValuePair("rt","text"));

		try {
			Executor executor = Executor.newInstance();
				URI serviceURI = new URI(baseURLLanguage).normalize();
					String auth = usernameLanguage + ":" + passwordLanguage;
					byte[] response = executor.execute(Request.Post(serviceURI)
					.addHeader("Authorization", "Basic "+ Base64.encodeBase64String(auth.getBytes()))
					.bodyString(URLEncodedUtils.format(qparams, "utf-8"),
							ContentType.APPLICATION_FORM_URLENCODED)
					).returnContent().asBytes();

					language = new String(response, "UTF-8");
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Watson error: "+e.getMessage(), e);
		}

		return language;
	}
	
	
}
