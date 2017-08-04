package cothe.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ConvertingTest {
    String mapStyle = "{object:\"123\",key:\"3333\"}";
    String listStyle = "[{id:1,name:3},{id:2,name:4}]";

    @Test
    public void stringToJsonMap() {
        Gson gson = new Gson();

        System.out.println(gson.toJson(gson.fromJson(mapStyle, Map.class)));

        try {
            System.out.println(gson.toJson(gson.fromJson(listStyle, Map.class)));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }


        System.out.println(gson.toJson(gson.fromJson(listStyle, List.class)));
        try {
            System.out.println(gson.toJson(gson.fromJson(mapStyle, List.class)));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }


    }
}
