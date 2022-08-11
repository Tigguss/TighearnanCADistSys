package ds.service1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import ds.service1.Service1Grpc.Service1ImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;


public class Service1Server extends Service1ImplBase {

	public static void main(String[] args) throws InterruptedException, IOException {

		Service1Server service1 = new Service1Server();

		service1.registerService();

		int port = 50051;

		Server server = ServerBuilder.forPort(port)
				.addService(service1)
				.build()
				.start();

		System.out.println("Service-1 Water Pollution started, listening on " + port);

		server.awaitTermination();
	}


	// *** Service 1 - Method 1 Contaminate level in the water. UNARY RPC ***
	@Override
	public void check(ContaminateLimit request, StreamObserver<ContaminateCheck> responseObserver) {



		// Request the level of contaminates
		System.out.println("Request contaminate level: " + request.getText());

		int level = request.getNumber();
		int limit = 25;
		if (level >= limit) {
			System.out.println("Contaminate level is too high, sending pipe shutoff command.");
			//Send command to close the pipe.


		} else {
			System.out.println("Contaminate level is under the limit.");
		}


		// Build the response message
		ContaminateCheck response = ContaminateCheck.newBuilder()
				.setNumber(level)
				.setText("Limit is 25. Contaminate level is " + request.getNumber())
				.build();

		// Send the response
		responseObserver.onNext(response);

		// Complete the call
		responseObserver.onCompleted();
	}


	// *** CLIENT-STREAMING RPC *** Service 1, Method 2 - Stop Water Flow in Pipes
	@Override
	public StreamObserver<Pipe> sum(StreamObserver<PipeShutdown> responseObserver) {

		return new StreamObserver<Pipe>() {

			ArrayList <Integer> pipeList = new ArrayList<>();

			@Override
			public void onNext(Pipe value) {

				// Receiving number
				int pipeId = value.getNumber();

				// Display number
				System.out.println("Pipe ID is: " + number);
				System.out.println("Status of Pipe: " + PipeShutdown);
				// Add the number to the pipeList
				pipeList.add(pipeId);


			}
			// Error Handling
			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onCompleted() {

				System.out.println("Done!");

				int totalPipes = 0;

				for (int number : pipeList) {
					totalPipes = totalPipes + 1;
				}

				// Build the response message
				PipeShutdown response = PipeShutdown.newBuilder()
						.setPipeId(total)
						.setText("Status of Pipe - On or Off. Stats:" + PipeShutdown)
						.setText("Number of Pipes: " + totalPipes)
						.build();

				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}

		};
	}

//Deadline
	try {
		response = blockingStub.ContaminateCheck(100,TimeUnit.SECONDS).first(request);
	} catch (StatusRuntimeException e) {
		logger.log(Level.WARNING, "No Input Recieved",e.getStatus());
		return;
	}
      logger.info("Input recieved: " + response);

}
	//Service 1  - Method 3 - Compiling data of contaminates and levels - Unary
	@Override
	public void compile(Pipe request, StreamObserver<Pipe> responseObserver) {

		int32 pipeIdInput = 0;
		int32 contaminateLevel = 0;
		int32 contaminateName = null;
		//User inputs contaminate info
		System.out.println("Please enter Pipe ID: " + request.getNumber());
		pipeIdInput  = userNumber;
		System.out.println("Please enter name of contaminate " + request.getText());
		contaminateName = userText;
		System.out.println("Please enter the level of contaminate" + request.getNumber());
		contaminateLevel  = userNumber;





		// Build the response message
		Pipe response = Pipe.newBuilder()
				.setNumber(level)
				.setText("Report added")
				.build();

		// Send the response
		responseObserver.onNext(response);

		// Complete the call
		responseObserver.onCompleted();
	}



	// Naming Services , use of jmdns.
	private void registerService() {

		 try {
	            // Create a JmDNS instance
	            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

	            String service_type = "_service1._tcp.local.";
	            String service_name = "Service 1 Server";
	            int service_port = 50051;
	            String service_description = "Water Pollution operations";

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

	/* Attempt at aunthentication
	ManagedChannel channel = Grpc.newService1Builder(
					"localhost:50051", InsecureChannelCredentials.create())
			.build();
	Service1Grpc.GreeterStub stub = Service1Grpc.newStub(channel);
	*/

}
