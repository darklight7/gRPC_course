package com.Greeting.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.proto.dummy.DummyServiceGrpc;
import org.proto.greet.*;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hi i am a gRPC client");

        GreetingClient main = new GreetingClient();
        main.run();

        System.out.println("Creating Stub");



    }
    public void run() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        doUnaryCall(channel);
        doServerStreamingCall(channel);

        System.out.println("Shutting Down channel");
        channel.shutdown();
    }

    private void doUnaryCall(ManagedChannel channel){
        GreetServiceGrpc.GreetServiceBlockingStub greetClient=GreetServiceGrpc.newBlockingStub(channel);

        //Unary
        Greeting greeting= Greeting.newBuilder()
                .setFirstName("Siddhant")
                .setLastName("Nagelia")
                .build();
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        GreetResponse response = greetClient.greet(greetRequest);
        System.out.println(response.getResult());

    }
    private void doServerStreamingCall(ManagedChannel channel){
        GreetServiceGrpc.GreetServiceBlockingStub greetClient=GreetServiceGrpc.newBlockingStub(channel);

        // Stream
        GreetManyTimesRequest greetManyTimesRequest= GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Siddhant")).build();

        greetClient.greetManyTimes(greetManyTimesRequest).forEachRemaining(greetManyTimesResponse -> System.out.println(greetManyTimesResponse.getResult()));

    }

}
