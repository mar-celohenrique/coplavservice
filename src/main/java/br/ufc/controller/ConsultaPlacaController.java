package br.ufc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.model.DenunciaSinistro;
import br.ufc.model.Foto;
import br.ufc.model.RetornoPlaca;
import br.ufc.service.DenunciaSinistroService;
import br.ufc.service.RetornoPlacaService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConsultaPlacaController {

    @Autowired
    private RetornoPlacaService retornoPlacaService;

    @Autowired
    private DenunciaSinistroService denunciaSinistroService;

    @PostMapping(value = "/foto")
    @ResponseBody
    public RetornoPlaca consultaImagem(@RequestBody Foto foto) throws Exception {
        return retornoPlacaService.buscar(foto, null, false);
    }

    @PostMapping(value = "/denunciar")
    @ResponseBody
    public DenunciaSinistro denunciarSinistro(@RequestBody DenunciaSinistro denuncia) {
        return denunciaSinistroService.salvar(denuncia);
    }

    @PostMapping(value = "/atualizacao")
    @ResponseBody
    public ResponseEntity<List<DenunciaSinistro>> atualizacoes(@RequestBody Long data) {
        return new ResponseEntity<>(denunciaSinistroService.buscarAtualizacoes(new Date(data)), HttpStatus.OK);
    }

    @GetMapping(value = "/sinistros")
    @ResponseBody
    public ResponseEntity<List<DenunciaSinistro>> sinistros() {
        return new ResponseEntity<>(denunciaSinistroService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping(value = "/hello")
    public String main() {
        return "olá";
    }

    @PostMapping(value = "/manual", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public RetornoPlaca consultaManual(@RequestBody String placa) throws Exception {
        return retornoPlacaService.buscar(null, placa, true);
    }

}
