package ie.ucd.hello;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NoSuchPhoneEntryException extends RuntimeException {

}
