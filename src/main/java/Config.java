import java.io.*;
import java.util.ArrayList;

public class Config {
    private ArrayList<String> raw = new ArrayList<String>();

    public Config(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
        String oneLine;
        while((oneLine = br.readLine()) != null) {
            raw.add(oneLine);
        }
    }


    public String getString(String option){
        for(String str:raw){
            if(str.contains(option+": ")){
                return str.substring(option.length()+2);
            }
        }
        return null;
    }


}
