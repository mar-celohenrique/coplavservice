package br.ufc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.model.DenunciaSinistro;
import br.ufc.repository.DenunciaSinistroRepository;

import java.util.Date;
import java.util.List;

@Service
public class DenunciaSinistroService {

    @Autowired
    private DenunciaSinistroRepository denunciaSinistroRepository;

    public DenunciaSinistro salvar(DenunciaSinistro denuncia) {
        this.denunciaSinistroRepository.save(denuncia);
        return denuncia;
    }

    public List<DenunciaSinistro> buscarAtualizacoes(Date date){
        return denunciaSinistroRepository.buscarAtualizacao(date);
    }
}
