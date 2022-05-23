package com.alizard0;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.concurrent.TimeUnit;

public class Application {

  private static String TOPIC_NAME = "mqtt-topic";

  public static void main(final String[] args) throws Exception {
    BlockingConnection connection = connect();

    Topic[] topics = {
            new Topic(TOPIC_NAME, QoS.AT_LEAST_ONCE)
    };
    connection.subscribe(topics);

    // Publish Messages
    String payload = "100";
    publishMessage(connection, TOPIC_NAME, payload);

    // Consume messages
    Message message1 = connection.receive(5, TimeUnit.SECONDS);
    System.out.println("Received: " + new String(message1.getPayload()));
  }

  private static void publishMessage(final BlockingConnection connection, final String topicName, final String payload) throws Exception {
    connection.publish(topicName, payload.getBytes(), QoS.AT_MOST_ONCE, false);
  }

  private static BlockingConnection connect() throws Exception {
    MQTT mqtt = new MQTT();
    mqtt.setHost("tcp://localhost:1883");
    BlockingConnection connection = mqtt.blockingConnection();
    connection.connect();
    System.out.println("Connected!");
    return connection;
  }
}
