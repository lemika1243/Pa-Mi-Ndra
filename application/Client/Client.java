package socket;

import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import mpanampy.Auto;
import operation.Binaire;
import java.io.*;

public class Client {
    static String mainNameFolder = "";

    public static void main(String[] args) {
        try {
            String ip = getIpAddress();
            Socket server = new Socket(ip, 5000);
            ObjectOutputStream outputStream;
            String path = "";
            List<String> fileDirectory;
            List<byte[]> files;
            List<String> fileNames;
            while (true) {
                try {
                    outputStream = new ObjectOutputStream(server.getOutputStream());
                    path = pathFinder();
                    if (path == null)
                        break;
                    fileDirectory = Auto.getAllFileNames(path);
                    files = Binaire.binarisation("", fileDirectory);
                    fileNames = Auto.getLastsByElement(fileDirectory, mainNameFolder + "/");
                    Auto.writeObjects(outputStream, fileNames, files);
                } catch (Exception e) {
                    Auto.showMessage(e.getMessage());
                }
            }
        } catch (Exception e) {
            Auto.showMessage(e.getMessage());
        }
    }

    static String pathFinder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.isFile())
                mainNameFolder = selectedFile.getName();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    static String getIpAddress() {
        return Auto.getAllIn("settings/ip.pa").get(0);
    }
}
