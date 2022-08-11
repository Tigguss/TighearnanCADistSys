package ds.service3;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import ds.service3.Service3Grpc.Service3ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class Service3Server extends Service3ImplBase {

    public static void main(String[] args) throws InterruptedException, IOException {

        Service3Server service3 = new Service3Server();

        service3.registerService();

        int port = 50053;

        Server server = ServerBuilder.forPort(port)
                .addService(service3)
                .build()
                .start();

        System.out.println("Service-3 Storm Water Control started, listening on " + port);

        server.awaitTermination();
    }


// Method 1 - Storm Predicting - Server Streaming

    // *** SERVER-STREAMING RPC ***
    @Override
    public void fibonacci(airSupply request, StreamObserver<airSupplyExternal> responseObserver) {

        // Receiving request
        System.out.println("Checking for storms" + request.getText() + request.getText());

        // Get request number
        string stormName = request.getText();
        string stormLocation = request.getText();
        // Create number variables
        int a = 0;
        int b = 1;

        for (int i = 1; i <= number; i++) {
            int id = a + b;
            a = b;
            b = id;

            airId response = airIdExternal.newBuilder()
                    .setNumber(id)
                    .build();

            responseObserver.onNext(response);

            try {
                //wait for one second
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();

    }


// Method 2 - Contaminate Checking in Storm Water Overflow - Unary

    @Override
    public void check(StormContaminateLimit request, StreamObserver<StormContaminateCheck> responseObserver) {


        // Request the level of contaminates
        System.out.println("Request Storm Water Contaminate level: " + request.getText());

        int level = request.getNumber();
        int limit = 25;
        if (level >= limit) {
            System.out.println("Storm Water Contaminate level is too high, sending pipe shutoff command.");
            //Send command to close the pipe.


        } else {
            System.out.println("Storm Water Contaminate level is under the limit.");
        }


        // Build the response message
        StormContaminateCheck response = StormContaminateCheck.newBuilder()
                .setNumber(level)
                .setText("Limit is 25. Storm Water Contaminate level is " + request.getNumber())
                .build();

        // Send the response
        responseObserver.onNext(response);

        // Complete the call
        responseObserver.onCompleted();
    }




// Method 3 - Distribution of Water - Client Streaming

    @Override
    public StreamObserver<Pipe> sum(StreamObserver<PipeShutdown> responseObserver) {

        return new StreamObserver<StromPipe>() {

            ArrayList <Integer> StormPipeList = new ArrayList<>();
            ArrayList <Integer> waterSupplyList = new ArrayList<>();

            @Override
            public void onNext(StormPipe value) {

                // Receiving number
                System.out.println("Enter Storm Pipe ID.);
                int stormPipeId = value.getNumber();
                System.out.println("Enter Water Supply ID.);
                int waterSupplyId = value.getNumber();
                // Display number
                System.out.println("Storm Pipe ID is: " + number);
                System.out.println("Status of Storm Pipe: " + StormPipeShutdown);

                System.out.println("Water Supply ID is: " + number);
                System.out.println("Status of Water Supply: " + );
                // Add the number to the pipeList
                pipeList.add(StormPipeId);
                waterSupplyList.add(waterSupplyId);

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

                System.out.println("Done!");

                int totalStormPipes = 0;
                int totalWaterSupply = 0;

                for (int number : StormPipeList) {
                    totalStormPipes = totalStormPipes + 1;
                }

                // Build the response message
                PipeShutdown response = StormPipeShutdown.newBuilder()
                        .setPipeId(total)
                        .setText("Status of Storm Pipe - On or Off. Stats:" + PipeShutdown)
                        .setText("Number of Storm Pipes: " + totalStormPipes)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

        };
    }
    private void registerService() {

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            String service_type = "_service3._tcp.local.";
            String service_name = "Service 3 Server";
            int service_port = 50053;
            String service_description = "Storm Water Managament operations";

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_description);
            jmdns.registerService(serviceInfo);

            System.out.printf("registrering service with type %s and name %s \n", service_type, service_name);
W
            // Wait a bit
            Thread.sleep(1000);

            // Unregister all services
            //jmdns.unregisterAllServices();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}