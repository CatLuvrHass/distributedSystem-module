package ie.ucd.hello;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public @ResponseBody String bob() { 
        return "Hello, World!";
    }
}
    