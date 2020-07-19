package com.Greeting.server;

import io.grpc.stub.StreamObserver;
import org.proto.greet.GreetRequest;
import org.proto.greet.GreetResponse;
import org.proto.greet.GreetServiceGrpc;
import org.proto.greet.Greeting;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {


    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        Greeting greeting = request.getGreeting();
        String first_name=greeting.getFirstName();

        String result = "Hellow" + first_name;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();



        //super.greet(request, responseObserver);
    }
}
