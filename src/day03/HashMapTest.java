package day03;

import java.util.*;

public class HashMapTest {


    /**
     * HashMap示例代码：
     */
    public void testHashMapAPIs() {
        Random r = new Random();
        HashMap<String, Integer> map = new HashMap();
        map.put("one", r.nextInt(10));
        map.put("two", r.nextInt(10));
        map.put("three", r.nextInt(10));
        System.out.println("map:" + map);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println("key : " + entry.getKey() + ",value:" + entry.getValue());
        }
        System.out.println("size:" + map.size());
        System.out.println("contains key two : " + map.containsKey("two"));
        System.out.println("contains key five : " + map.containsKey("five"));
        System.out.println("contains value 0 : " + map.containsValue(new Integer(0)));
        map.remove("three");
        System.out.println("map:" + map);
        map.clear();
        System.out.println((map.isEmpty() ? "map is empty" : "map is not empty"));
    }

    public static void main(String[] args) {
        HashMapTest hashMapTest = new HashMapTest();
        hashMapTest.testHashMapAPIs();
    }


}
