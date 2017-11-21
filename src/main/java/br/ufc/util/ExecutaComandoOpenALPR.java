package br.ufc.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufc.model.Foto;
import br.ufc.model.RetornoOpenALPR;

@Component
public class ExecutaComandoOpenALPR {

	@Autowired
	private SalvaImagemLocal salvaImagemLocal;

	public RetornoOpenALPR exec(Foto foto) {

		RetornoOpenALPR retornoOpenALPR = new RetornoOpenALPR();

		String imagem = salvaImagemLocal.salvarArquivoLocal(foto);

		String command = "/usr/local/bin/alpr -j -n 2 -c br " + imagem;

		String retornoALPR = this.executaComando(command);

		String placa = retornoALPR.split(",")[10].split(":")[2].replaceAll("\"", "");
		String precisao = retornoALPR.split(",")[11].split(":")[1];

		retornoOpenALPR.setPlaca(placa);
		retornoOpenALPR.setPrecisao(precisao);

		return retornoOpenALPR;
	}

	private String executaComando(String comando) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(comando);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
}
