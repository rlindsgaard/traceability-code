package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    @RequestMapping(value="/greeting", method=RequestMethod.POST)
    public Greeting greetingSubmit(@ModelAttribute Greeting greeting) {
        return greeting;
    }

}