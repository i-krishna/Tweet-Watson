Read [SDD doc](/Solution%20design%20document.pdf) & [Presentation](/Final_Presentation%20_v1.3_final.pdf). This Tweet watson application demonstrates how to use Watson Machine Translation and Language Identification services (powered by WebSphere Liberty) and deploy it on Bluemix.

The Tweet watson application contains the following contents:
    
*   WebContent/

    This directory contains the client side code (HTML/CSS/JavaScript) of the Translate Tweets application as well as compiled server   side java classes and necessary JAR libraries. The content inside this directory is all you need to generate the final WAR file.
    
    Fork this project & change the Twitter credentials in the twitter4j.properties file to your own credentials. Once the project is up & running with Liberty for Java runtime, Watson Machine & Language Identification Services, enter a topic to search on twitter as specified in Final presentation.pdf. The application will then display the Tweets translated into the language you selected.
    
*   src/

    This directory contains the server side code (JAVA) of the Translate Tweets application. 
    
*   build.xml

    This file allows builds the Translate Tweets application using Ant.

