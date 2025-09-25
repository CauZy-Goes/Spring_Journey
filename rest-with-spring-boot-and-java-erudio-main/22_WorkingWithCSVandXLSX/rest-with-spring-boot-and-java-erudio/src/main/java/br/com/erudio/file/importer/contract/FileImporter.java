package br.com.erudio.file.importer.contract;

import br.com.erudio.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    // contrato de clase
    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
