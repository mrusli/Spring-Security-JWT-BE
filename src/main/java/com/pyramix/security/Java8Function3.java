package com.pyramix.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Java8Function3 {

	public static void main(String[] args) {
		
		// Need to create this class, otherwise you will get
		// Cannot make a static reference to the non-static method when calling
		// 		convertListToMap(List<String>, Function<String,Integer>) 
		// later on
		Java8Function3 obj = new Java8Function3();

		List<String> list = Arrays.asList("node", "c++", "java", "javascript");
		
		// using lambda expression: x -> x.length() 
		// x -> x.length() refers to each (i.e. 'x') of the 'string item' in the list
		// to return the length of the string item
		Map<String, Integer> map =
				obj.convertListToMap(list, x -> x.length());
		
		System.out.println("using lambda expression: "+map);
		
		// using method reference: getLength()
		Map<String, Integer> map2 =
				obj.convertListToMap(list, obj::getLength);
		
		System.out.println("using method reference: "+map2);
		
	}

    public <T, R> Map<T, R> convertListToMap(List<T> list, Function<T, R> func) {
        Map<T, R> result = new HashMap<>();
        
        for (T t : list) {
        	// func.appy(t) refers to the 'string item' in the list, 
        	// upon which applied with x -> x.length() or obj::getLength
            result.put(t, func.apply(t));
        }
        return result;

    }

    public Integer getLength(String str) {
    	
        return str.length();
    }	
	
}
