package ie.ucd.hello;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PhoneBookController {
    Map<String, PhoneBookEntry> directory = new HashMap<>();

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public @ResponseBody Collection<PhoneBookEntry> listEntries(@RequestParam(defaultValue = "") String firstname,
            @RequestParam(defaultValue = "") String surname) {
        ArrayList<PhoneBookEntry> list = new ArrayList<>();
        for (PhoneBookEntry entry : directory.values()) {
            if (!firstname.isEmpty() && !entry.getFirstname().equals(firstname))
                continue;
            if (!surname.isEmpty() && !entry.getSurname().equals(surname))
                continue;
            list.add(entry);
        }
        return list;
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity<PhoneBookEntry> createEntry(@RequestBody PhoneBookEntry entry) throws URISyntaxException {
        directory.put(entry.getPhone(), entry);
        
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/book/"+entry.getPhone();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(entry, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/book/{phone_number}", method=RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    public PhoneBookEntry getEntity(@PathVariable String phone_number) {
        PhoneBookEntry entry = directory.get(phone_number);
        if (entry == null) throw new NoSuchPhoneEntryException();
        return entry;

    }
    
    @RequestMapping(value="/book/{phone_number}", method=RequestMethod.PUT)
    public ResponseEntity<PhoneBookEntry> replaceEntity(@PathVariable String phone_number, @RequestBody PhoneBookEntry entry) {
        PhoneBookEntry phoneBookEntry = directory.get(phone_number);
        if (phoneBookEntry == null) throw new NoSuchPhoneEntryException();

        if (!entry.isValid(phone_number)) 
            throw new InvalidPhoneEntryException();
        
        directory.put(phone_number, entry);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/book/"+phone_number;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Location", path);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/book/{phone_number}", method=RequestMethod.PATCH)
    public ResponseEntity<PhoneBookEntry> updateEntity(@PathVariable String phone_number, @RequestBody PhoneBookEntry entry) {
        PhoneBookEntry phoneBookEntry = directory.get(phone_number);
        if (phoneBookEntry == null) throw new NoSuchPhoneEntryException();

        if (entry.getPhone() != null && !phone_number.equals(entry.getPhone())) 
            throw new InvalidPhoneEntryException();
        
        directory.get(phone_number).update(entry);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/book/"+phone_number;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Location", path);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/book/{phone_number}", method=RequestMethod.DELETE)
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void deleteEntity(@PathVariable String phone_number) {
        PhoneBookEntry entry = directory.remove(phone_number);
        if (entry == null) throw new NoSuchPhoneEntryException();
    }
}
