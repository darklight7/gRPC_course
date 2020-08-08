package com.Greeting.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.proto.dummy.DummyServiceGrpc;
import org.proto.greet.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

       // doUnaryCall(channel);
       // doServerStreamingCall(channel);
        doClientStreamingCall(channel);
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
    private void doClientStreamingCall(ManagedChannel channel){

        // Create Async client
        GreetServiceGrpc.GreetServiceStub asyncClient= GreetServiceGrpc.newStub(channel);
        CountDownLatch latch =new CountDownLatch(1);
        StreamObserver<LongGreetRequest> requestObserver =  asyncClient.longGreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse value) {
                System.out.println("Received a response");
                System.out.println(value.getResult());

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("Server completed ");
        latch.countDown();
            }
        });

        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Siddhant").build())
                .build());
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Nagelia").build())
                .build());
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Sida").build())
                .build());
        requestObserver.onCompleted();
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
