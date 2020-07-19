package com.Greeting.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.proto.dummy.DummyServiceGrpc;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Hi i am a gRPC client");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",50051)
                .usePlaintext()
                .build();
        System.out.println("Creating Stub");

        DummyServiceGrpc.DummyServiceBlockingStub syncClient=DummyServiceGrpc.newBlockingStub(channel);
        //DummyServiceGrpc.DummyServiceFutureStub asyncClient=DummyServiceGrpc.newFutureStub(channel);


        System.out.println("Shutting Down channel");
        channel.shutdown();

    }
}
