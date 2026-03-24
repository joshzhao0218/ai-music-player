package com.josh.music.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * API Response Model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    
    public static ApiResponse ok(Object data) {
        return new ApiResponse(true, "Success", data);
    }
    
    public static ApiResponse ok(String message, Object data) {
        return new ApiResponse(true, message, data);
    }
    
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }
}
