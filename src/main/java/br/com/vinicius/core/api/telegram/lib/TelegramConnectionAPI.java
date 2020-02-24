package br.com.vinicius.core.api.telegram.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import br.com.vinicius.core.formatter.FormattedLogger;

public abstract class TelegramConnectionAPI {

	private final String url;
	private final HashMap<Integer, Long> cooldownList;

	protected final FormattedLogger logger;

	public TelegramConnectionAPI(String url) {
		this.url = url;
		this.cooldownList = new HashMap<Integer, Long>();

		this.logger = new FormattedLogger("CONNECTION API");
	}

	public final Future<HttpResponse<JsonNode>> getUpdates(Integer offset) {
		return Unirest.post(this.url + "/getUpdates").field("offset", offset).asJsonAsync();
	}

	public final Future<HttpResponse<JsonNode>> getUpdatesByChatId(Integer offset, String chat_id) {
		return Unirest.post(this.url + "/getUpdates").field("offset", offset).field("chat_id", chat_id).asJsonAsync();
	}

	public final boolean inCooldown(int chat_id) {
		return !this.cooldownList.containsKey(chat_id) ? false
				: this.cooldownList.get(chat_id) > System.currentTimeMillis() ? true : false;
	}

	public final void sendCooldownMessage(int chat_id, String message) {
		if (inCooldown(chat_id))
			return;
		long cooldown = Long.valueOf(System.currentTimeMillis() + 1500);
		this.cooldownList.put(chat_id, cooldown);
		this.sendMessage(chat_id, message);
	}

	public final void sendMessage(Object chat_id, String text) {
		this.unirestAsync(this.url + "/sendMessage", Arrays.asList(chat_id, text), getChatId(chat_id),
				TelegramPostType.TEXT);
		logger.log("/sendMessage:" + chat_id + ":" + text);
	}

	public final void sendPhoto(Object chat_id, String file_id) {
		this.unirestAsync(this.url + "/sendPhoto", Arrays.asList(chat_id, file_id), getChatId(chat_id),
				TelegramPostType.PHOTO);
		logger.log("/sendPhoto:" + chat_id + ":" + file_id);
	}

	public final void sendDocument(Object chat_id, String file_id) {
		this.unirestAsync(this.url + "/sendDocument", Arrays.asList(chat_id, file_id), getChatId(chat_id),
				TelegramPostType.DOCUMENT);
		logger.log("/sendDocument:" + chat_id + ":" + file_id);
	}

	public final void sendAudio(Object chat_id, String file_id) {
		this.unirestAsync(this.url + "/sendAudio", Arrays.asList(chat_id, file_id), getChatId(chat_id),
				TelegramPostType.AUDIO);
		logger.log("/sendAudio:" + chat_id + ":" + file_id);
	}

	public final void sendVideo(Object chat_id, String file_id) {
		this.unirestAsync(this.url + "/sendVideo", Arrays.asList(chat_id, file_id), getChatId(chat_id),
				TelegramPostType.VIDEO);
		logger.log("/sendVideo:" + chat_id + ":" + file_id);
	}

	public final void sendVoice(Object chat_id, String file_id) {
		this.unirestAsync(this.url + "/sendVoice", Arrays.asList(chat_id, file_id), getChatId(chat_id),
				TelegramPostType.VOICE);
		logger.log("/sendVoice:" + chat_id + ":" + file_id);
	}

	public final TelegramPostType getChatId(Object chat_id) {
		return isInteger(chat_id) ? TelegramPostType.CHAT_ID : TelegramPostType.CHAT_ID_STRING;
	}

	public final boolean isInteger(Object object) {
		try {
			Integer.valueOf(String.valueOf(object));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public final void unirestAsync(String url, List<Object> objects, TelegramPostType... types) {
		List<String> fields = new ArrayList<String>();

		try {
			int id = 0;
			for (TelegramPostType type : types) {
				Object object = objects.get(id);
				if (object == null)
					continue;
				String format = type.format(object);
				fields.add(format);
				id += 1;
			}
		} finally {
			this.unirest(url, fields);
		}
	}

	private final void unirest(String url, List<String> fields) {
		Thread thread = new Thread(new Runnable() {
			Map<String, Object> parameters = new HashMap<String, Object>();

			public void run() {
				try {
					for (String s : fields) {
						String[] split = s.split(";");

						String method = split[0];
						Object parament = split[1];
						if (!parameters.containsKey(method))
							parameters.put(method, parament);
					}
				} catch (Exception e) {
					logger.error("Ocorreu um erro ao enviar um comando! Mais detalhes: " + e.getMessage());
				} finally {
					Unirest.post(url).fields(parameters).asJsonAsync();
					logger.log("sended!:" + url + ":" + parameters.toString());
				}
			}
		});

		thread.start();
	}
}
