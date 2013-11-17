package com.histomon.api;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class SiteUtil {

	public static String convertMapToJson ( Map<String, String> map ) {
		if ( map == null ) map = new HashMap<String, String>();
		
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();
		try {
	    mapper.writeValue(strWriter, map);
    } catch (Exception e) {
    	return null;
    } 
		return strWriter.toString();
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> convertJsonToMap(String json) {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> result = null;
		if (StringUtils.isNotBlank(json)) {
			try {
				result = (HashMap<String, String>) mapper.readValue(json,
				    new TypeReference<HashMap<String, String>>() {
				    });
				return result;
			} catch (Exception e) {
			}
		}
		return new HashMap<String, String>();
	}
}
