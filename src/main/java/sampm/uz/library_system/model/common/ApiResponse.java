package sampm.uz.library_system.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;

    private boolean success;

    private Object info;

    public ApiResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }
    public ApiResponse(boolean success, Object info){
        this.success = success;
        this.info = info;
    }
    public ApiResponse(String message, Object info){
        this.message = message;
        this.info = info;
    }
}
