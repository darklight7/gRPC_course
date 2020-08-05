package com.Greeting.server;

import io.grpc.stub.StreamObserver;
import org.proto.greet.*;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {


    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        Greeting greeting = request.getGreeting();
        String first_name=greeting.getFirstName();

        String result = "Hellow " + first_name;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {

        String firstName = request.getGreeting().getFirstName();
        try {
            for (int i=0;i<10;i++)
            {
                String result= "Hellow " + firstName +" response number=" +i;
                GreetManyTimesResponse response= GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }

    }
}
