package ds.service2;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import ds.service2.Service2Grpc.Service2ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class Service2Server extends Service2ImplBase {

    public static void main(String[] args) throws InterruptedException, IOException {

        Service2Server service2 = new Service2Server();

        service2.registerService();

        int port = 50052;

        Server server = ServerBuilder.forPort(port)
                .addService(service2)
                .build()
                .start();

        System.out.println("Service-2 Air Pollution started, listening on " + port);

        server.awaitTermination();
    }

    // *** Service 2 - Method 1 Contaminate level in the air. UNARY RPC ***
    @Override
    public void check(AirLimit request, StreamObserver<AirContaminateCheck> responseObserver) {


        // Request the level of contaminates
        System.out.println("Request contaminate level: " + request.getText());

        int level = request.getNumber();
        int limit = 30;
        if (level >= limit) {
            System.out.println("Contaminate level is too high, air supply shutoff command.");


        } else {
            System.out.println("Contaminate level is under the limit.");
        }


        // Build the response message
        AirContaminateCheck response = AirContaminateCheck.newBuilder()
                .setNumber(level)
                .setText("Limit is 25. Contaminate level is " + request.getNumber())
                .build();

        // Send the response
        responseObserver.onNext(response);

        // Complete the call
        responseObserver.onCompleted();
    }

    // Service 2 - Method 2 - Air Comparison - Server Streaming
// *** SERVER-STREAMING RPC ***
    @Override
    public void fibonacci(airSupply request, StreamObserver<airSupplyExternal> responseObserver) {

        // Receiving request
        System.out.println("Please enter the two air supplies you would like to compare: " + request.getText() + request.getText());

        // Get request number
        int airId = request.getNumber();
        int airIdExternal = request.getNumber();
        // Create number variables
        int a = 0;
        int b = 1;

        for (int i = 1; i <= number; i++) {
            int num = a + b;
            a = b;
            b = num;

            airId response = airIdExternal.newBuilder()
                    .setNumber(num)
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

    // Method 3 - Passive Monitoring Diffusion Tubes - BIDIRECTIONAL RPC ***
    @Override
    public StreamObserver<AirContaminateCheck> total(StreamObserver<Diffusion> responseObserver) {

        return new StreamObserver<AirContaminateCheck>() {

            int total = 0;

            @Override
            public void onNext(AirContaminateCheck value) {

                // Get the request number
                int number = value.getNumber();

                // Add the number to the total
                total = total + number;

                // Build the response message
                Diffusion response = Diffusion.newBuilder()
                        .setNumber(total)
                        .build();

                // Send the message
                responseObserver.onNext(response);
            }

            // Trigger specific diffusion
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {

                // Message call completed
                System.out.println("Done!");

                // End the message call
                responseObserver.onCompleted();
            }

        };
    }

    private void registerService() {

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            String service_type = "_service2._tcp.local.";
            String service_name = "Service 2 Server";
            int service_port = 50052;
            String service_description = "Air Pollution operations";

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_description);
            jmdns.registerService(serviceInfo);

            System.out.printf("registrering service with type %s and name %s \n", service_type, service_name);

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