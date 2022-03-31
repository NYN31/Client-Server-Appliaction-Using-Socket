package com.socket.clients;

public class PingClientOne {
    Socket clientSocket;

    public void connect(String ip, int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
    }

    public void send(String message) throws IOException {
        PrintWriter request = new PrintWriter(clientSocket.getOutputStream(), true);
        request.println(message);
        BufferedReader response = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        );

        String line = response.readLine();
        System.out.println("RESPONSE: " + line.toUpperCase());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        PingClientOne client = new PingClientOne();
        client.connect("127.0.0.1", 6000);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("bye")) break;
            client.send(line);
        }
    }
}
