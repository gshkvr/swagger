package by.epam.kvirykashvili.javalabtask.rest.error;

import org.springframework.validation.ObjectError;

import java.util.Date;
import java.util.List;

public class ErrorDetails {

    private Date timestamp;
    private String message;

    public ErrorDetails(Date timestamp, List<ObjectError> errors) {
        super();
        this.timestamp = timestamp;
        this.message = setMessage(errors);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    private String setMessage(List<ObjectError> errors){
        StringBuilder ret = new StringBuilder();
        for(ObjectError e : errors){
            ret.append(e.getDefaultMessage());
        }
        return ret.toString();
    }
}
