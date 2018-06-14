# Kafka Streams workshop
## Prerequisites 
1. Install docker, CE (Community Edition). The installation instructions for Linux, Mac and Windows you can find [here](https://docs.docker.com/install/)
2. Verify the installation. Run *docker* or *docker-compose* command from your terminal
3. In our workshop we're going to use Twitter API for the real-time data. So everyone should generate API keys and token to access the API.

   Go to this [link](https://apps.twitter.com/app/new) Follow the instructions. 
   
   - In *Website* tab you can put any site URL, e.g. https://developer.twitter.com/
   - In *Callback URLs* click on *Add a Callback URL* button
   - Click on *Generate your Twitter application* button
   - Go to the *Keys and Access Tokens* tab and click on *Create my access tocken* button
   
   After this process you supposed to have 4 keys: 
   + API KEY (Consumer key)
   + API secret (Consumer secret)
   + Access token
   + Access token secret
   
   Save them in safe place on your laptop. We'll use all of them later on.
   
 4. In addition to Twitter API we'll use [this API](https://www.alphavantage.co/) for real-time stock data
 
    Open this link above and click on *Get your free API key today*. 
    
    Follow the instructions and get the key and save it.
     
   
