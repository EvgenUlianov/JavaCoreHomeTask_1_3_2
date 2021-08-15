package EvgenUlianov;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String PATH = "C:\\Users\\EUlyanov\\Documents\\Учеба JAVA\\JavaCore\\Games";

    public static void main(String[] args) {
        System.out.println("Задача 2: Сохранение");
        System.out.println("Работаем в папке:");
        System.out.println(PATH);
        File catalog = new File(PATH);
        if (!catalog.exists()) {
            System.out.println("указанная папка не существует");
            return;
        }
        String savegames = PATH + "\\" + "savegames";
        File catalogSavegames = new File(PATH);
        if (!catalogSavegames.exists()) {
            System.out.println("папка savegames не существует");
            return;
        }

        GameProgress[] saves = {
                new GameProgress(23, 43, 22, 54.4545),
                new GameProgress(25, 45, 24,154.4545),
                new GameProgress(28, 48, 25,254.4545)};

        int index = 0;

        List<String> files = new ArrayList<>();

        for (GameProgress save :saves) {
            String fileName = savegames + "\\" +  String.format("autosave%d.dat", ++index);
            try (FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(save);
                files.add(fileName);
                //
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
//            File test = new File(fileName);
//            System.out.println(test.getName());
//            System.out.println(test.);
//            try {
//                System.out.println(test.getCanonicalPath());
//            } catch (IOException ex){
//                System.out.println(ex.getMessage());
//
//            }

        }

        boolean zippedSuccessfully = zipFiles(savegames, files);

        if (zippedSuccessfully) {
            for (String fileName: files) {
                try {
                    File deletingFile = new File(fileName);
                    deletingFile.deleteOnExit();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }


    }

    public static boolean zipFiles(String savegames, List<String> files) {

        boolean itWasSuccessfully = true;

        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream(savegames + "\\" +  "saves.zip"))) {

            //for (int i = 1; i <= index; i++)
            for (String fileName: files) {
                String shortName = (new File(fileName)).getName())
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    ZipEntry entry = new ZipEntry(shortName);
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    itWasSuccessfully = false;
                }
            }
            // закрываем текущую запись для новой записи
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            itWasSuccessfully = false;
        }
        return itWasSuccessfully;
    }
}
