# link-mobility

Repository for the LINK mobility assignments.

# Messaging App 📧

## Description

This app, composed of `producer` and `composer` microservices, illustrates the asynchronous flow of messages from
producers to consumers using Java with Spring Boot and RabbitMQ.
Both parts use internal thread pools to create (producers) and consume (consumers) messages.
Producers are automatically and continuously creating messages when running the ProducerApp app, but there also exists a
REST API endpoint (i.e. api/v1/messages/publish) with which the messages can be created manually.
After the `producer` microservice is up and running, the messages can be automatically consumed by
running the `consumer` microservice.

## Prerequisite

A Docker environment, such as [Docker Desktop](https://www.docker.com/products/docker-desktop/), should be already
installed to use this app.

## Tech stack

- Java 17+;
- Spring Boot 3.4.2;
- RabbitMQ;
- Log4j2;
- Lombok.

## Structure

- `producer`:
    - creates messages;
    - sends them to the configured queues via exchanges and bindings.
- `consumer`:
    - receives and consumes messages;
    - has three thread pools to process the messages based on the recipient.
- `commons`:
    - shareable module;
    - defined constants used both in `producer` and `consumer`.

## Run

- Make sure `Docker Desktop` is up and running.
- Execute the microservices from an IDE in this order:
    1. `producer` -> com.alex.linkmobility.messagingapp.ProducerApp
    2. `consumer` -> com.alex.linkmobility.messagingapp.ConsumerApp

## Known issues

- Currently, if `consumer` is executed before `producer`, then ShutdownSignalException is thrown with the following
  message:
  reply-code=404, reply-text=NOT_FOUND - no queue 'message-queue-recipients-a' in vhost '/'... This happens due to how
  the queues were declared (`Auto-declaring a non-durable, auto-delete, or exclusive Queue...`).



