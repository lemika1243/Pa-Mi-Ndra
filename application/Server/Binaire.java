package operation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.ArrayList;
import mpanampy.*;

public class Binaire {
    public static byte[] binarisation(File file) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");
                FileChannel channel = raf.getChannel()) {
            long fileSize = channel.size();
            int chunkSize = 81902; // Adjust the chunk size as needed
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[chunkSize];
            int bytesRead;
            while ((bytesRead = channel.read(ByteBuffer.wrap(buffer))) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

    public static List<byte[]> binarisation(String path) throws Exception {
        List<byte[]> result = new ArrayList<byte[]>();
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                byte[] b = binarisation(file);
                result.add(b);
            } else {
                result.addAll(binarisation(file.getName()));
            }
        }

        return result;
    }

    public static List<byte[]> binarisation(String mom, List<String> paths) throws Exception {
        List<byte[]> result = new ArrayList<byte[]>();
        if (paths.size() == 0)
            throw new Exception("None");
        File folder;
        for (String string : paths) {
            folder = new File(mom + "/" + string);
            result.add(binarisation(folder));
        }
        return result;
    }

    public static void debinarisation(byte[] bytes, String filePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bytes);
        fos.close();
    }

}
