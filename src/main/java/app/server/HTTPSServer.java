package app.server;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

/**
 * Created by ironman on 19.08.2017.
 */
public class HTTPSServer {
    private int port = 4567;
    private boolean isServerDone = false;

    public static void main(String[] args) {
       // HTTPSServer server = new HTTPSServer();
        //server.run();
        System.out.println(new File("auth.properties").getPath());
    }

    public void run() {
        SSLContext sslContext = this.createSSLContext();

        try {
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
            System.out.println("SSL server started");

            while (!isServerDone) {
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                new ServerThread(sslSocket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SSLContext createSSLContext() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("keystore.jks"), "passphrase".toCharArray());

            // key manager
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "passphrase".toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            // trust manager
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            // ssl context
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(keyManagers, trustManagers, null);

            return sslContext;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    static class ServerThread extends Thread {
        private SSLSocket sslSocket;

        ServerThread(SSLSocket sslSocket) {
            this.sslSocket = sslSocket;
        }

        @Override
        public void run() {
            sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());

            try {
                sslSocket.startHandshake();
                SSLSession sslSession = sslSocket.getSession();

                System.out.println("SSL session:");
                System.out.println("\tProtocol: " + sslSession.getProtocol());
                System.out.println("\tCipher suite: " + sslSession.getCipherSuite());

                InputStream inputStream = sslSocket.getInputStream();
                OutputStream outputStream = sslSocket.getOutputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Input: " + line);

                    if (line.trim().isEmpty()) {
                        break;
                    }
                }

                printWriter.print("HTTP/1.1 200\r\n");
                printWriter.flush();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
