package uz.muhandis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private String message;
    private boolean success;
    private User user;

    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

}
