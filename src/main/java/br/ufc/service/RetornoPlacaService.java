package br.ufc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufc.model.Foto;
import br.ufc.model.RetornoOpenALPR;
import br.ufc.model.RetornoPlaca;
import br.ufc.model.RetornoPlaca.Identificacao;
import br.ufc.util.ExecutaComandoOpenALPR;

@Component
public class RetornoPlacaService {

	@Autowired
	private ExecutaComandoOpenALPR alpr;

	public RetornoPlaca buscar(Foto foto, String placa, boolean manual) throws Exception {

		RetornoPlaca resultado = new RetornoPlaca();
		if (!manual) {
			RetornoOpenALPR retornoOpenALPR = alpr.exec(foto);
			if (null != retornoOpenALPR.getPlaca()) {
				JSONObject retornoCompletoBusca = new JSONObject(
						this.enviaRequisicaoSinesp(retornoOpenALPR.getPlaca()));
				resultado = this.preparaRetorno(retornoCompletoBusca);
				resultado.setIdentificacao(Identificacao.SUCESSO);
				resultado.setPlaca(retornoOpenALPR.getPlaca());
			} else {
				resultado.setIdentificacao(Identificacao.INSUCESSO);
			}
		} else {
			JSONObject retornoCompletoBusca = new JSONObject(this.enviaRequisicaoSinesp(placa));
			resultado = this.preparaRetorno(retornoCompletoBusca);
			resultado.setIdentificacao(Identificacao.SUCESSO);
		}

		return resultado;
	}

	private String enviaRequisicaoSinesp(String placa) throws IOException {
		String link = "http://18.231.92.161:8090/api/consultaPlaca?placa=".concat(placa);

		StringBuilder result = new StringBuilder();
		URL url = new URL(link);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}

	private RetornoPlaca preparaRetorno(JSONObject retorno) throws JSONException {
		JSONObject retornoExcencialBusca = (JSONObject) retorno.get("retorno");
		RetornoPlaca resultado = new RetornoPlaca();
		resultado.setPlaca(retornoExcencialBusca.getString("placa"));
		resultado.setSituacao(retornoExcencialBusca.getString("situacao"));
		resultado.setAno(retornoExcencialBusca.getString("ano"));
		resultado.setAnoModelo(retornoExcencialBusca.getString("anoModelo"));
		resultado.setData(retornoExcencialBusca.getString("data"));
		resultado.setCor(retornoExcencialBusca.getString("cor"));
		resultado.setModelo(retornoExcencialBusca.getString("modelo"));
		resultado.setMarca(retornoExcencialBusca.getString("marca"));
		resultado.setUf(retornoExcencialBusca.getString("uf"));
		resultado.setCidade(retornoExcencialBusca.getString("municipio"));
		resultado.setChassi(retornoExcencialBusca.getString("chassi"));
		resultado.setCodigoSituacao(retornoExcencialBusca.getInt("codigoSituacao"));
		return resultado;
	}
}
