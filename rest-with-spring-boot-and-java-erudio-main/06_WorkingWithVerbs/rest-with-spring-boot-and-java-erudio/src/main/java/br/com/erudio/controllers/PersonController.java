package br.com.erudio.controllers;

import br.com.erudio.PersonServices;
import br.com.erudio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired // injeção de dependencia
    private PersonServices service;
    // private PersonServices service = new PersonServices();

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET, //tipo de method
        produces = MediaType.APPLICATION_JSON_VALUE //ele gera um json da saida
    )
    public Person findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE, //ele recebe/usa um json da saida[body]
        produces = MediaType.APPLICATION_JSON_VALUE //ele gera um json da saida
    )
    public Person create(@RequestBody Person person) {
        return service.create(person);
    }

    @RequestMapping(
        method = RequestMethod.PUT, //tipo de method
        consumes = MediaType.APPLICATION_JSON_VALUE, //ele recebe/usa um json da saida[body]
        produces = MediaType.APPLICATION_JSON_VALUE //ele gera um json da saida
    )
    public Person update(@RequestBody Person person) {
        return service.update(person);
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE //tipo de method
    )
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
