package com.chatgpt.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.chatgpt.web.dto.Message;
import com.chatgpt.web.utility.PropertyUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("")
public class ChatAjaxController {
	private static final String URL = PropertyUtil.getProperty("api.url");
	private static final String API_KEY = PropertyUtil.getProperty("api.key");

	@PostMapping("/openAiChat")
    public ResponseEntity<String> openAiChat(@RequestBody Message messages) {
    	String model = "gpt-3.5-turbo";
    	String message = "{\"role\": \"system\", \"content\": \"返答は日本語で\"},{\"role\": \"user\", \"content\": \" "+ messages.getMessage() +" \"}";
    	String content =  "";
    	try {
    		// HTTPリクエストの作成
    		URL obj = new URL(URL);
    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    		con.setRequestMethod("POST");
    		con.setRequestProperty("Content-Type", "application/json");
    		con.setRequestProperty("Authorization", "Bearer " + API_KEY);
    		con.setDoOutput(true);

    		// リクエストの送信
    		OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
    		out.write("{\"model\": \"" + model + "\", \"messages\": [" + message + "]}");
    		out.close();

    		// レスポンスの取得
    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		Gson gson = new Gson();
    		JsonObject jsonResponse = gson.fromJson(in, JsonObject.class);
    		JsonArray choicesArray = jsonResponse.getAsJsonArray("choices");
    		JsonObject messageObject = choicesArray.get(0).getAsJsonObject().get("message").getAsJsonObject();
    		content = messageObject.get("content").getAsString();
    
    		System.out.println(content);

    	} catch (Exception e) {
    		System.out.println(e);
    	}  
    	return new ResponseEntity<>(content, HttpStatus.OK);
    }
}
