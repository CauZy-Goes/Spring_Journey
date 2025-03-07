package io.github.cursodsousa.arquiteturaspring.montadora.api;

import io.github.cursodsousa.arquiteturaspring.montadora.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carros")
public class TesteFabricaController {

    @Autowired //INSTANCIADOR, VAI NO CONTAINER O DO SPPRING E PEGA
//    @Qualifier("eletrico") tb funciona  fala o nome do bean especifco, existem varios motores
    @Eletrico // FALA QUE QUER O ELETRICO
    private Motor motor;

    @PostMapping
    public CarroStatus ligarCarro(@RequestBody Chave chave){
        var carro = new HondaHRV(motor);
        return carro.darIgnicao(chave);
    }
}
