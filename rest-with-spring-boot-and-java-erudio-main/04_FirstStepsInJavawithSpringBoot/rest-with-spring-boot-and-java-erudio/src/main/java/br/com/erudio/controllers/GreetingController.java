package br.com.erudio.controllers;

import br.com.erudio.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

//    template de string %s! substitui uma strin no string format
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong(); // mocka o id, gera um id automaticamente

    // http://localhost:8080/greeting?name=Leandro
    @RequestMapping("/greeting") // legado
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "Word") String name){ // se n passar anda sera word
        return  new Greeting(counter.incrementAndGet(), String.format(template, name)); // invoca o id a cada requisição aumenta esse valor
    }
}

//