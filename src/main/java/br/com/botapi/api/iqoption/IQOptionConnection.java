package br.com.botapi.api.iqoption;

import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import br.com.botapi.Core;
import br.com.botapi.file.FileManager;
import br.com.botapi.file.FileType;
import br.com.botapi.formatter.FormattedLogger;

public class IQOptionConnection {

	protected final Core core;

	protected final FormattedLogger logger;

	protected boolean isAuthenticated = false;
	protected String email = null;
	protected String password = null;

	public IQOptionConnection(Core core) {
		this.core = core;
		this.logger = new FormattedLogger("CONNECTION");
	}

	public final void startConnection() {
		FileManager file = new FileManager("iqOption", FileType.TXT);

		try {
			if (file.hasValue("iqoption-email")) {
				this.email = file.getStringValue("iqoption-email");
				logger.log("Configuração 'iqoption-email' encontrada! Obtendo endereço de email...");
			} else {
				logger.error("Configuração 'iqoption-email' não encontrada!");
			}

			if (file.hasValue("iqoption-password")) {
				this.password = file.getStringValue("iqoption-password");
				logger.log("Configuração 'iqoption-password' encontrada! Obtendo senha para autenticação...");
			} else {
				logger.error("Configuração 'iqoption-password' não encontrada!");
			}

			if (this.email == null || this.password == null) {
				logger.error("Não foi possível iniciar a autenticação com o IQOption!");
				return;
			}

			Future<HttpResponse<JsonNode>> future = Unirest.post(IQOptionURL.URL_AUTH).field("email", this.email)
					.field("password", this.password).asJsonAsync();
			HttpResponse<JsonNode> response = future.get();
			if (response.getStatus() != 200) {
				logger.error("Não foi possível iniciar a autenticação com o IQOption! Mais detalhes: "
						+ response.getStatusText());
				return;
			}
			
			this.isAuthenticated = true;
			logger.log("Usuário autenticado no IQOption com sucesso!");
		} catch (Exception e) {
			logger.error("Ocorre um erro ao inicializar a conexão com o IQOption! Mais detalhes: " + e.getMessage());
		} finally {
			logger.log(!this.isAuthenticated() ? "Não foi possível iniciar a conexão com o IQOption."
					: "Conexão com o IQOption iniciada com sucesso!");
		}
	}

	public final boolean isAuthenticated() {
		return isAuthenticated;
	}
}
