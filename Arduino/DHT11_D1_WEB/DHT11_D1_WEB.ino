#include <ESP8266WiFi.h>
#include <DHT.h>

#define DHT11SensorPin D5

DHT dht(DHT11SensorPin, DHT22);

const char* ssid = "VIVACOM_IVAN";
const char* password = "20162016";
 
WiFiServer server(80);
 
void setup() {
  Serial.begin(9600);
  delay(10);
 
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
 
  WiFi.begin(ssid, password);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
 
  server.begin();
  Serial.println("Server started");
 
  Serial.print("Use this URL : ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");

  Serial.println("Init DHT");
  dht.begin();
  delay(3000);
}
 
void loop() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
 
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
 
  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();
 
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println(""); 
 
   float h = dht.readHumidity();
   float t = dht.readTemperature();

   Serial.print("Humidity: ");
   Serial.print(h);
   Serial.print(" %\t");
   Serial.print("Temperature: ");
   Serial.print(t);
   Serial.print(" *C ");

   client.print(h);
   client.print(" ");
   client.print(t);
  
  delay(1);
  Serial.println("Client disconnected");
  Serial.println("");
 
}
