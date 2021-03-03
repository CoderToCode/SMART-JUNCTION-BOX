#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "smartjb-28df7-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "O1yDMVLoeb7tHj1mynXIo5UwQpJxh2SsYX8kBJkd"
#define WIFI_SSID "New Mahalakshmi Timbers"
#define WIFI_PASSWORD "12345678"

void setup() {
  Serial.begin(115200);
pinMode (D0,OUTPUT);
pinMode (D1,OUTPUT);
pinMode (D2,OUTPUT);
pinMode (D3,OUTPUT);
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.setString("Connection", "NodeMcu");
}
String a;
void loop() {
  a=Firebase.getString("Mobile");
  Serial.println(a);
 if(a=="True")
 {
  digitalWrite(D1,HIGH);
  }
  if(a=="False")
  {
    digitalWrite(D1,LOW);
  }

}

  
