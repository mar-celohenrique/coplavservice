package br.ufc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.model.DenunciaSinistro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DenunciaSinistroRepository extends JpaRepository<DenunciaSinistro, Integer>{

    @Query("SELECT denuncia FROM DenunciaSinistro denuncia WHERE denuncia.dataInformado >= :data")
    List<DenunciaSinistro> buscarAtualizacao(@Param("data")Date data);
}
