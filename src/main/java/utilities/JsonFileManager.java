package utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonFileManager {
    private List<Map<String, String>> dataList;

    public JsonFileManager(String filepath) throws FileNotFoundException {
        Type type = new TypeToken<List<Map<String, String>>>() {}.getType();
        dataList = new Gson().fromJson(new FileReader(filepath), type);
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public Map<String, String> getRecord(int index) {
        return dataList.get(index);
    }
}
