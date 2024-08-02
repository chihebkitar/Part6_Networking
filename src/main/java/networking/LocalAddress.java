package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalAddress {
    public static void main(String[] args) {

        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

// give the name of ur local machine and the ip address of it
    }
}