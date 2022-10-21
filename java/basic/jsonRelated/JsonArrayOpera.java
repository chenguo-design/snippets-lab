package jsonRelated;


import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class JsonArrayOpera {
    @Test
    public void testJsonArray(){
        Function<String,Integer> function1 = a-> 1;
        Function<Integer,String> function2 = a->"1";
        Function<Integer, Integer> compose = function1.compose(function2);


        List<Object> list = new ArrayList<>();
        list.add(new Node("a", "a"));
        list.add(new Node("b", "b"));


        JSONArray jsonArray = new JSONArray(list);
        Node[] nodes = new Node[0];


        Object[] objects = jsonArray.stream().map(item -> ((Node) item).getName()).toArray();

        System.out.println(Arrays.toString(objects));


    }




    private class Node{
        String name;
        String age;

        public Node(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
