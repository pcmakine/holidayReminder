package eagleapp.com.holidaynotify.testhttprequest.testenrico;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;

import static org.junit.Assert.*;

/**
 * Created by Pete on 4.11.2015.
 */
public class EnricoParamsTest {

    @Test
    public void testFindValue(){
        List<String> keys = Arrays.asList("key1", "key2", "key3");
        List<String> values = Arrays.asList("value1", "value2", "value3");
        List<String> list = Arrays.asList(keys.get(0), values.get(0), keys.get(1), values.get(1), keys.get(2), values.get(2));
        for(int i = 0; i < keys.size(); i++){
            String result = (String) invokeMethod(
                    getClassForName("eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams"),
                    "findValue",
                    null,
                    new Object[] {keys.get(i), list},
                    String.class,
                    List.class
            );
            String expected = values.get(i);
            assertEquals(expected, result);
        }
    }

    //This doesn't work because android classes can't be used from unit tests. The method uses textutils
  /* @Test
    public void testBuildParamsString(){
        Map map = new LinkedHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        String expected = "key1=value1&key2=value2&key3=value3";

        assertEquals(expected, EnricoParams.buildParamsString(map));
    }*/


    private Object invokeMethod(Class clazz, String methodName, Object object, Object[] params, Class... paramsClasses){
        Object result = null;
        try {
            Method method = clazz.getDeclaredMethod(methodName, paramsClasses);
            method.setAccessible(true);
            result = method.invoke(object, params);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Class getClassForName(String name){
        try {
            return Class.forName(name);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
