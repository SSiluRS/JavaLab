import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread {
    static Lab1 lab1;
    boolean isEnabled = false;
    DataOutputStream oos;
    DataInputStream ois;
    static Socket socket;
    public ActionListener ButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!socket.isClosed()) {
                try {
                    String clientCommand = null;
                    var lines = lab1.clientDialog.jTextField.getText().split("\\n");
                    clientCommand = lines[lines.length - 1];
                    try {
                        oos.writeUTF(clientCommand);
                        oos.flush();
                    } catch (SocketException ee) {
                        ee.printStackTrace();
                    }
                    Thread.sleep(1000);

// пишем данные с консоли в канал сокета для сервера

// ждём чтобы сервер успел прочесть сообщение из сокета и ответить

                } catch (InterruptedException | IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    };

    public void disable() {
        isEnabled = false;
        System.out.println("disabled");
    }

    public void enable() {
        isEnabled = true;
        System.out.println("enabled");
    }

    @Override
    public void run() {
        super.run();
// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try {
            socket = new Socket("localhost", 3345);
            try (DataOutputStream oos1 = new DataOutputStream(socket.getOutputStream());
                 DataInputStream ois1 = new DataInputStream(socket.getInputStream())) {
                ois = ois1;
                oos = oos1;

                lab1.jFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        try {
                            oos.writeUTF("quit");
                            oos.flush();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });

                lab1.isConnected = true;
                lab1.clientDialog.consoleField.append("Client connected to socket.\n");
                lab1.clientDialog.consoleField.append("Client writing channel = oos & reading channel = ois initialized.\n");

                String msgin = "";

                while (!msgin.equalsIgnoreCase("quit")) {
                    //if (ois.read() > -1) {
                    msgin = ois.readUTF();
                    lab1.clientDialog.consoleField.append("\n" + msgin);
                    //}
                }
                disable();

                lab1.isConnected = false;
                lab1.clientDialog.consoleField.append("\nClosing connections & channels on client Side - DONE.\n");


            } catch (IOException ee) {
                // TODO Auto-generated catch block
                ee.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Can't connect to server!");
            interrupt();
        }

    }
}
