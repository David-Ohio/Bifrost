/*
 * Contact: ohio@ohiotech.com.br
 * This is a free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */
package bifrost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Ohio
 */
public class Bifrost {
    
    
    /**
     * @param args the command line arguments
     * args[0] ip to connect to
     * args[1] port to connect to
     */
    public static void main(String[] args) {
        try {
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            Socket Server = new Socket(ip, port);
        } catch (IOException ex) {
            Logger.getLogger(Bifrost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Socket bridge;
    int port;
    /**
     * Open the bridge to an ip and port
     * @param ip the ip to connect to
     * @param port the port to connect to
     */
    public void createBridge(String ip, int port){
        try {
            this.port = port;
            bridge = new Socket(ip, port);
        } catch (IOException ex) {
            Logger.getLogger(Bifrost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Send and object through the bridge
     * @param obj the object to be sent
     */
    public void sendObject(Object obj){
        try {
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(bridge.getOutputStream());
            oos.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(Bifrost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object receiveObject(){
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket client = ss.accept();
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Bifrost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean receiveBoolean(){
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket client = ss.accept();
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            return ois.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(Bifrost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
