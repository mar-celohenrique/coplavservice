package br.ufc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.ufc.model.Foto;

@Component
public class SalvaImagemLocal {

	@Value("${pasta.imagens}")
	private String PASTA_IMAGENS;

	public String salvarArquivoLocal(Foto foto) {

		String caminhoDiretorio = PASTA_IMAGENS;
		File diretorio = new File(caminhoDiretorio);

		diretorio.mkdirs();
		
		try {

			byte[] data = Base64.decodeBase64(foto.getBase64());
			File arquivo = new File(diretorio, foto.getNome());

			FileOutputStream fop = new FileOutputStream(arquivo);
			arquivo.createNewFile();
			fop.write(data);
			fop.close();

		} catch (IOException e) {

		}

		return caminhoDiretorio.concat(foto.getNome());
	}

	public void deletarFoto(String caminho) {
		File file = new File(caminho);
		file.delete();
	}

}
