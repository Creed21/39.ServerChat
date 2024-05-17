package server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BufferedReader keyboard;

    /**
     * ****** napomena ******
     *      AKO NA SERVERSKOJ STRANI PORVO INICIJAZIUJETE
     *
     *      BufferedReader pa onda PrintWriter
     *      NA KLIJENTSKOJ STRANI MORA BITI OBRNUTO
     *
     *      PrintWriter pa onda BufferedReader
     */
    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ovde se nalazi kod koji se izvrsava za klijentsku nit
     * => proces (kao mini program)
     */
    @Override
    public void run() {
        handleCommunication();
    }

    private void handleCommunication() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Uspavao sam metodu cisto da prodje inicijalizacija I/O streamova (similacija cekanja)");
        }

        try {
            // poruka koju klijent salje
            String msgFromClient = reader.readLine();
            System.out.println("Msg from client: " + msgFromClient);
            // poruka koju klijent salje - zapamcena

            // send successful reading status
            sendMsg("Msg read!");

            // enter message from keyboard and send to client
            sendMsgFromKeyboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgFromKeyboard() {
        try {
            System.out.print("Enter message for server: ");
            sendMsg(keyboard.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String message) {
        writer.println(message);
        writer.flush();
    }
}
