import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 3, 5, 125.5);
        GameProgress progress2 = new GameProgress(80, 2, 3, 75.2);
        GameProgress progress3 = new GameProgress(50, 1, 1, 25.0);

        saveGame("C://Games//savegames//save_1.dat", progress1);
        saveGame("C://Games//savegames//save_2.dat", progress2);
        saveGame("C://Games//savegames//save_3.dat", progress3);

        List<String> filesToZip = new ArrayList<>();
        filesToZip.add("C://Games//savegames//save_1.dat");
        filesToZip.add("C://Games//savegames//save_2.dat");
        filesToZip.add("C://Games//savegames//save_3.dat");

        zipFiles("C://Games//savegames//zip.zip", filesToZip);

        for (String filePath : filesToZip) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Файл " + filePath + " удален");
            } else {
                System.out.println("Ошибка удаления файла " + filePath);
            }
        }
    }

    public static void saveGame(String filePath, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : filesToZip) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry entry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("Ошибка при добавлении файла в архив: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания архива: " + e.getMessage());
        }
    }
}
