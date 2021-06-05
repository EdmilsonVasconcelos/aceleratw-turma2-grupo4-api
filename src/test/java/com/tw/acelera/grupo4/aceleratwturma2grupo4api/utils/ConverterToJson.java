package com.tw.acelera.grupo4.aceleratwturma2grupo4api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterToJson {
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
