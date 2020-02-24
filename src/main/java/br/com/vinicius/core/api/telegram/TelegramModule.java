package br.com.vinicius.core.api.telegram;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.vinicius.core.api.telegram.modules.*;
import br.com.vinicius.core.api.telegram.modules.extend.TelegramThumbModule;

public class TelegramModule {

	private TelegramMessageModule messageModule;

	private TelegramFromModule fromModule;
	private TelegramChatModule chatModule;
	private TelegramChatParticipantModule participantModule;
	private TelegramChatMemberModule memberModule;
	private List<TelegramChatMembersModule> membersModule;

	private List<TelegramPhotoModule> photoModule;
	private TelegramDocumentModule documentModule;
	private TelegramAudioModule audioModule;

	public TelegramModule() {
		this.membersModule = new ArrayList<TelegramChatMembersModule>();
		this.photoModule = new ArrayList<TelegramPhotoModule>();
	}

	public final void initializeModules(JSONObject message) {
		this.messageModule = new TelegramMessageModule(message.getInt("message_id"));
		this.messageModule.setDate(message.getInt("date"));
		this.messageModule.setText(message.has("text") ? message.getString("text") : null);

		JSONObject chatObject = message.getJSONObject("chat");
		TelegramChatModule chatModule = new TelegramChatModule(chatObject.getInt("id"));
		if (chatModule.getId() <= 0) {
			chatModule.setTitle(chatObject.getString("title"));
			if (chatObject.has("all_members_are_administrators"))
				chatModule.setAllMembersAreAdministrators(chatObject.getBoolean("all_members_are_administrators"));
		} else {
			chatModule.setFirstName(chatObject.getString("first_name"));
			chatModule.setLastName(chatObject.getString("last_name"));
			chatModule.setUsername(chatObject.getString("username"));
		}

		chatModule.setType(chatObject.getString("type"));
		this.chatModule = chatModule;
		if (message.has("from")) {
			JSONObject fromObject = message.getJSONObject("from");
			TelegramFromModule fromModule = new TelegramFromModule(fromObject.getInt("id"));

			fromModule.setBot(fromObject.getBoolean("is_bot"));
			fromModule.setFirstName(fromObject.getString("first_name"));
			fromModule.setLastName(fromObject.getString("last_name"));
			fromModule.setUsername(fromObject.getString("username"));
			fromModule.setLanguageCode(fromObject.getString("language_code"));
			this.fromModule = fromModule;
		}

		if (message.has("new_chat_participant")) {
			JSONObject participantObject = message.getJSONObject("new_chat_participant");
			TelegramChatParticipantModule participantModule = new TelegramChatParticipantModule(
					participantObject.getInt("id"));
			participantModule.setBot(participantObject.getBoolean("is_bot"));
			participantModule.setFirstName(participantObject.getString("first_name"));
			participantModule.setUsername(participantObject.getString("username"));
			this.participantModule = participantModule;
		}

		if (message.has("new_chat_member")) {
			JSONObject memberObject = message.getJSONObject("new_chat_member");
			TelegramChatMemberModule memberModule = new TelegramChatMemberModule(memberObject.getInt("id"));
			memberModule.setBot(memberObject.getBoolean("is_bot"));
			memberModule.setFirstName(memberObject.getString("first_name"));
			memberModule.setUsername(memberObject.getString("username"));
			this.memberModule = memberModule;
		}

		if (message.has("new_chat_members")) {
			JSONArray members = message.getJSONArray("new_chat_members");
			for (int i = 0; i < members.length(); i++) {
				JSONObject member = members.getJSONObject(i);
				TelegramChatMembersModule memberModule = new TelegramChatMembersModule(member.getInt("id"));

				memberModule.setBot(member.getBoolean("is_bot"));
				memberModule.setFirstName(member.getString("first_name"));
				memberModule.setUsername(member.getString("username"));
				if (!this.membersModule.contains(memberModule))
					this.membersModule.add(memberModule);
			}
		}

		if (message.has("photo")) {
			JSONArray photos = message.getJSONArray("photo");
			for (int i = 0; i < photos.length(); i++) {
				JSONObject photo = photos.getJSONObject(i);
				TelegramPhotoModule photoModule = new TelegramPhotoModule(photo.getString("file_id"));

				photoModule.setFileUniqueId(photo.getString("file_unique_id"));
				photoModule.setFileSize(photo.getInt("file_size"));
				photoModule.setWidth(photo.getInt("width"));
				photoModule.setHeight(photo.getInt("height"));
				if (!this.photoModule.contains(photoModule))
					this.photoModule.add(photoModule);
			}
		}

		if (message.has("document")) {
			JSONObject documentObject = message.getJSONObject("document");
			TelegramDocumentModule documentModule = new TelegramDocumentModule(documentObject.getString("file_id"));

			documentModule.setFileName(documentObject.getString("file_name"));
			documentModule.setMimeType(documentObject.getString("mime_type"));
			if (documentObject.has("thumb")) {
				JSONObject thumbObject = documentObject.getJSONObject("thumb");
				TelegramThumbModule thumbModule = new TelegramThumbModule(thumbObject.getString("file_id"));

				thumbModule.setFileUniqueId(thumbObject.getString("file_unique_id"));
				thumbModule.setFileSize(thumbObject.getInt("file_size"));
				thumbModule.setWidth(thumbObject.getInt("width"));
				thumbModule.setHeight(thumbObject.getInt("height"));
				documentModule.setThumbModule(thumbModule);
			}
			documentModule.setFileUniqueId(documentObject.getString("file_unique_id"));
			documentModule.setFileSize(documentObject.getInt("file_size"));
			this.documentModule = documentModule;
		}

		if (message.has("audio")) {
			JSONObject audioObject = message.getJSONObject("audio");
			TelegramAudioModule audioModule = new TelegramAudioModule(audioObject.getString("file_id"));

			audioModule.setDuration(audioObject.getInt("duration"));
			audioModule.setMimeType(audioObject.getString("mime_type"));
			audioModule.setFileUniqueId(audioObject.getString("file_unique_id"));
			audioModule.setFileSize(audioObject.getInt("file_size"));
			this.audioModule = audioModule;
		}
	}

	public final TelegramMessageModule getMessageModule() {
		return messageModule;
	}

	public final TelegramFromModule getFromModule() {
		return fromModule;
	}

	public final TelegramChatModule getChatModule() {
		return chatModule;
	}

	public final TelegramChatParticipantModule getParticipantModule() {
		return participantModule;
	}

	public final TelegramChatMemberModule getMemberModule() {
		return memberModule;
	}

	public final List<TelegramChatMembersModule> getMembersModule() {
		return membersModule;
	}

	public final TelegramAudioModule getAudioModule() {
		return audioModule;
	}

	public final TelegramDocumentModule getDocumentModule() {
		return documentModule;
	}

	public final List<TelegramPhotoModule> getPhotoModule() {
		return photoModule;
	}

	public final TelegramPhotoModule getPhotoQualtiyModule() {
		if (this.photoModule.isEmpty() || this.photoModule.size() < 0)
			return null;
		return this.photoModule.stream().sorted((a, b) -> Integer.valueOf(a.getFileSize()).compareTo(b.getFileSize()))
				.findFirst().orElse(null);
	}
}
