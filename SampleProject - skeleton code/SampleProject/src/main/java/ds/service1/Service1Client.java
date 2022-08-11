package ds.service1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import ds.service1.Service1Grpc.Service1BlockingStub;
import ds.service1.Service1Grpc.Service1Stub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Service1Client {
	
	private static Service1BlockingStub blockingStub;
	private static Service1Stub asyncStub;
	
	private ServiceInfo service1Info;
	
	public static void main(String[] args) {

		Service1Client s1client = new Service1Client();
		
		// Discover the jmDNS service
		String service_type = "_service1._tcp.local.";
		s1client.discoverService1(service_type);
		
		ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 50051)
				.usePlaintext()
				.build();

		// Create stubs (generate from proto)
		blockingStub = Service1Grpc.newBlockingStub(channel);
		asyncStub = Service1Grpc.newStub(channel);

		// Call RPC methods
		check();
		
		sum();
		
		fibonacciAsync();
		
		total();

	}
	
	
	// *** UNARY RPC ***
	public static void check() {

		// Display a message to show what method has been called
		System.out.println("check() has been called:");
		
		Random rand = new Random();
		
		// Create a random number
		int randomNumber = rand.nextInt(25);
		
		// Build the request message
		ContaminateLimit request = ContaminateLimit.newBuilder()
				.setNumber(randomNumber)
				.build();
		
		// Send the message via the blocking stub and store the response
		ContaminateCheck response = blockingStub.check(request);

		// Display the result
		System.out.println(randomNumber + " check is: " + response.getNumber() + "\n\n");
		System.out.println("check() unary call has finished");
		
	}
	
	
	// *** CLIENT-STREAMING RPC ***
	public static void sum() {
		
		// Display a message to show what method has been called
		System.out.println("sum() has been called:");
		
		StreamObserver<SumResponse> responseObserver = new StreamObserver<SumResponse>() {

			@Override
			public void onNext(SumResponse value) {

				// Receive sum value returned from server
				int sum = value.getNumber();
				
				// Display sum
				System.out.println("Sum is: " + sum);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();				
			}

			@Override
			public void onCompleted() {

				// Stream is completed
				System.out.println("sum() client-streaming has finished\n\n");
			}
		};
			
			// Send the client data here
			StreamObserver<NumberRequest> requestObserver = asyncStub.sum(responseObserver);
			
			try {

				NumberRequest request = NumberRequest.newBuilder()
						.setNumber(1)
						.build();
				
				requestObserver.onNext(request);
				Thread.sleep(500);
				
				request = NumberRequest.newBuilder()
						.setNumber(2)
						.build();
				
				requestObserver.onNext(request);
				Thread.sleep(500);
				
				request = NumberRequest.newBuilder()
						.setNumber(3)
						.build();
				
				requestObserver.onNext(request);
				Thread.sleep(500);
				
				// The requests have ended
				requestObserver.onCompleted();
				
				// Wait for 2 seconds
				Thread.sleep(2000);
		
			}
			  catch (RuntimeException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
			
	}
	
	
	// *** SERVER-STREAMING RPC ***
	public static void fibonacciAsync() {
		
		// Display a message to show what method has been called
		System.out.println("fibonacciAsync() has been called:");

		NumberRequest request = NumberRequest.newBuilder()
				.setNumber(10)
				.build();
		
		StreamObserver<NumberResponse> responseObserver = new StreamObserver<NumberResponse>() {

			@Override
			public void onNext(NumberResponse value) {

				// Display received number
				System.out.println(value.getNumber());
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onCompleted() {

				// Server-streaming is completed
				System.out.println("fibonacciAsync() server-streaming has finished\n\n");
			}
			
		};
		
		// Client sends the request here via the asynchronous stub
		asyncStub.fibonacci(request, responseObserver);
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	// *** BIDIRECTIONAL RPC ***
	public static void total() {
		
		// Display a message to show what method has been called
		System.out.println("total() has been called:");
		
		StreamObserver<NumberResponse> responseObserver = new StreamObserver<NumberResponse>() {

			@Override
			public void onNext(NumberResponse value) {

				// Display received number
				System.out.println(value.getNumber());
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();	
			}

			@Override
			public void onCompleted() {

				// Bidirectional-streaming is completed
				System.out.println("total() bidirectional-streaming has finished");	
			}
			
		};
		
		// Client sends the requests here via the asynchronous stub
		StreamObserver<NumberRequest> requestObserver = asyncStub.total(responseObserver);
		
		try {
			
			// Style #1
			int num = 50;

			NumberRequest request = NumberRequest.newBuilder()
					.setNumber(num)
					.build();
			
			requestObserver.onNext(request);
			
			 // Style #2
			request = NumberRequest.newBuilder()
				.setNumber(num + num)
				.build();
			
			requestObserver.onNext(request);
			
			// Style #3
			requestObserver.onNext(NumberRequest.newBuilder().setNumber(num * num).build());
			requestObserver.onNext(request);
			
			// End the requests
			requestObserver.onCompleted();
			
			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);
			
		} catch (RuntimeException e) { 
			e.printStackTrace(); 
		}  catch (InterruptedException e) { 
			e.printStackTrace(); 
		}


		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	//Discovery
	private void discoverService1(String service_type) {
		
		
		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

				
			jmdns.addServiceListener(service_type, new ServiceListener() {
				
				@Override
				public void serviceResolved(ServiceEvent event) {
					System.out.println("Math Service resolved: " + event.getInfo());

					service1Info = event.getInfo();

					int port = service1Info.getPort();
					
					System.out.println("resolving " + service_type + " with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type:"+ event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + service1Info.getNiceTextString());
					System.out.println("\t host: " + service1Info.getHostAddresses()[0]);
				
					
				}
				
				@Override
				public void serviceRemoved(ServiceEvent event) {
					System.out.println("Math Service removed: " + event.getInfo());

					
				}
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					System.out.println("Math Service added: " + event.getInfo());

					
				}
			});
			
			// Wait a bit
			Thread.sleep(2000);
			
			jmdns.close();

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
