package com.socket.useOfExecutorServices;



public class ExecutorPingServerAsync {

    private final int port;
    private int clientCount = 1;
    private ExecutorService executorService;

    public ExecutorPingServerAsync(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening at 127.0.0.1: " + port);
        executorService = Executors.newFixedThreadPool(10);
        listenAndRespondAsync(serverSocket);
    }

    public void listenAndRespondAsync(ServerSocket serverSocket) throws IOException{
        while(!serverSocket.isClosed()){
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client (%s) connected %n", clientCount++);
            startClientRequestHandlerThread(clientSocket);
        }
    }

    public void startClientRequestHandlerThread(Socket clientSocket){
//        Thread clientHandlerThread = new Thread(new SocketHandler(clientSocket));
//        clientHandlerThread.setName("Client " + clientCount++);
//        clientHandlerThread.start();

        executorService.execute(new SocketHandler(clientSocket));
    }

    public static void main(String[] args) throws IOException {
        ExecutorPingServerAsync executorPingServerAsync = new ExecutorPingServerAsync(6000);
        executorPingServerAsync.start();
    }
}
