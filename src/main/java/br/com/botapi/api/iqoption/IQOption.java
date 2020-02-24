package br.com.botapi.api.iqoption;

import java.util.concurrent.Future;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class IQOption {

	public static final String EMAIL = "userEmail";
	public static final String PASSWORD = "userPassword";

	public static final String URL_AUTH = "https://auth.iqoption.com/api/v1.0/login";
	
	public static final String URL_WEBSOCKET = "wss://iqoption.com/echo/websocket";

	public static final String URL_GET_PROFILE = "https://iqoption.com/api/getprofile";
	
	public static final String LINE = "============================";

	public static void main(String[] args) {
		try {
			try {
				Future<HttpResponse<JsonNode>> future = Unirest.post(URL_AUTH).field("email", EMAIL)
						.field("password", PASSWORD).asJsonAsync();
				HttpResponse<JsonNode> response = future.get();
				if (response.getStatus() != 200)
					return;

				JsonNode body = response.getBody();
				JSONObject object = body.getObject();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();

				System.out.println("AUTENTICAÇÃO: ");
				System.out.println(gson.toJson(object));
				System.out.println(LINE);
			} finally {
				Future<HttpResponse<JsonNode>> future = Unirest.get(URL_GET_PROFILE).asJsonAsync();
				HttpResponse<JsonNode> response = future.get();
				if (response.getStatus() != 200)
					return;

				JsonNode body = response.getBody();
				JSONObject object = body.getObject();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				System.out.println("DADOS DO USUÁRIO: ");
				System.out.println(gson.toJson(object));
				System.out.println(LINE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(1);
		}
	}
}
