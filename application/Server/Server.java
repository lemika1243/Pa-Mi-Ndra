package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import mpanampy.Auto;
import operation.Binaire;

public class Server {
    static List<String> fileNames;
    static List<byte[]> files;
    static String downloadName = "Pa-MI-ndra";
    static ObjectInputStream objstream = null;

    public static void main(String[] args) {
        while (true) {
            try {
                String ip = getMyIpAddress();
                System.out.println("Waiting for Client...");
                System.out.println("\n"+ip);
                ServerSocket server = new ServerSocket(5000);
                Socket client = server.accept();
                System.out.println("\nClient connected");
                while (true) {
                    try {
                        objstream = new ObjectInputStream(client.getInputStream());
                        fileNames = (List<String>) objstream.readObject();
                        files = (List<byte[]>) objstream.readObject();
                        createFiles(fileNames, files);
                    } catch (Exception e) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Client lost because " + e.getMessage());
            }
        }
    }

    static void createFiles(List<String> fileNames, List<byte[]> files) throws Exception {
        for (int i = 0; i < fileNames.size(); i++) {
            String string = fileNames.get(i);
            byte[] file = files.get(i);
            Auto.createFolders(downloadName, string);
            Binaire.debinarisation(file, downloadName + "/" + string);
        }
    }

    static String getMyIpAddress() throws Exception{
        String valiny = "";
        List<String> allip = Auto.grep("settings/ip.pa", "ipv4");
        try {
            valiny = allip.get(0);
        } catch (Exception e) {
            try {
                allip = Auto.grep("settings/ip.pa", "inet");
                String[] split = allip.get(0).split("/");
                valiny = split[0];
            } catch (Exception ex) {
                throw new Exception("There is no ip address found");
            }
        }
        return valiny;
    }
}