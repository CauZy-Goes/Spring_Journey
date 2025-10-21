package br.com.erudio.file.exporter.contract;

import br.com.erudio.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

//    retorna um arquivo e recebe uma lista de usuários
    Resource exportFile(List<PersonDTO> people) throws Exception;
}
