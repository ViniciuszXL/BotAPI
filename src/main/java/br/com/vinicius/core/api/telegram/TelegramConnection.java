package br.com.vinicius.core.api.telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import br.com.vinicius.core.Core;
import br.com.vinicius.core.api.telegram.lib.TelegramConnectionAPI;
import br.com.vinicius.core.formatter.FormattedLogger;

public class TelegramConnection extends TelegramConnectionAPI {

	protected final Core core;

	protected final String url;
	protected final String chat_id;
	protected final FormattedLogger logger;

	protected final List<TelegramConnectionCommand> commandList;

	public TelegramConnection(Core core, String url) {
		this(core, url, null);
	}

	public TelegramConnection(Core core, String url, String chat_id) {
		super(url);

		this.core = core;

		this.url = url;
		this.chat_id = chat_id;
		this.logger = new FormattedLogger("CONNECTION");
		this.commandList = new ArrayList<TelegramConnectionCommand>();
	}

	public final Core getCore() {
		return core;
	}

	public final void addConnectionCommand(TelegramConnectionCommand command) {
		if (this.commandList.stream().filter(s -> s.isSimilar(command.getName())).findFirst().orElse(null) != null)
			return;
		this.commandList.add(command);
	}

	public final void run() {
		Thread thread = new Thread(new Runnable() {
			int last_update_id = 0;

			public void run() {
				try {
					while (true) {
						Future<HttpResponse<JsonNode>> future = chat_id == null ? getUpdates(last_update_id++)
								: getUpdatesByChatId(last_update_id++, chat_id);
						HttpResponse<JsonNode> response = future.get();
						if (response.getStatus() != 200)
							continue;

						JsonNode body = response.getBody();
						JSONArray responses = body.getObject().getJSONArray("result");
						if (responses.isNull(0))
							continue;

						last_update_id = responses.getJSONObject(responses.length() - 1).getInt("update_id") + 1;
						for (int i = 0; i < responses.length(); i++) {
							JSONObject object = responses.getJSONObject(i);

							String type = object.has("message") ? "message" : "channel_post";
							JSONObject message = object.getJSONObject(type);
							TelegramModule telegramModule = new TelegramModule();

							try {
								telegramModule.initializeModules(message);
							} finally {
								for (TelegramConnectionCommand commands : commandList)
									commands.runCommand(telegramModule);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();

	}
}
