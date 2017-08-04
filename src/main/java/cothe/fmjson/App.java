package cothe.fmjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Enter the directory.");
            return;
        }

        subDirList(args[0]);


    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void subDirList(String source) {

        boolean skipAsking = false;
        File dir = new File(source);

        File[] fileList = dir.listFiles();

        try {

            for (int i = 0; i < fileList.length; i++) {

                File file = fileList[i];

                if (file.isFile()) {

                    FileReader fr = new FileReader(file);

                    if (file.getName().endsWith(".json")) {
                        System.out.println("\t 파일 이름 = " + file.getAbsolutePath());

                        String path = file.getAbsolutePath();
                        String beforeJson = readFile(path, StandardCharsets.UTF_8);
                        System.out.println("before-" + beforeJson);

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        String afterJson = "";


                        try {
                            if (beforeJson.trim().startsWith("[")) {
                                afterJson = gson.toJson(gson.fromJson(beforeJson, List.class));
                            } else if (beforeJson.trim().startsWith("{")) {
                                afterJson = gson.toJson(gson.fromJson(beforeJson, Map.class));
                            } else {
                                throw new JsonSyntaxException("else");
                            }
                        } catch (JsonSyntaxException e) {
                            System.out.println("fail to convert");
                            continue;
                        }

                        System.out.println("after-" + afterJson);
                        System.out.print("change file(y/n/ya(yes for all files in this directory))?");

                        if (skipAsking) {

                            backupAndWrite(file, path, afterJson);
                        } else {

                            Scanner scanner = new Scanner(System.in);
                            String answer = scanner.nextLine();

                            if (answer.toUpperCase().equals("Y")) {
                                System.out.println("yes");

                                backupAndWrite(file, path, afterJson);

                            } else if (answer.toUpperCase().equals("YA")) {
                                System.out.println("yes");

                                backupAndWrite(file, path, afterJson);

                                skipAsking = true;
                            } else {
                                System.out.println("no");
                            }
                        }


                    }
                    // 파일이 있다면 파일 이름 출력


                } else if (file.isDirectory()) {


                    // 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색

                    subDirList(file.getCanonicalPath().toString());

                }

            }

        } catch (IOException e) {

            System.out.println("error");

        }

    }

    private static void backupAndWrite(File orgFile, String path, String afterJson) throws IOException {
        orgFile.renameTo(new File(path + ".bak"));

        try (FileWriter fw = new FileWriter(path)) {
            fw.write(afterJson);
        }
    }


}
