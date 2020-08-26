import java.io.*;

public class FileTool {
    public static String readFile(String path){
        File file = new File(path);
        if (file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String text = null;
                while ((text = bufferedReader.readLine()) != null){
                    stringBuilder.append(text);
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
