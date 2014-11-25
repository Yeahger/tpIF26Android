package com.tpif26.pauphilet_romero.hanjaout.models;

import android.util.Log;

import com.tpif26.pauphilet_romero.hanjaout.utils.DateFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Message {

    private String message;
    private Date date;
    private boolean sent;

    public Message(String message, String date, boolean sent) {
        this.date = DateFunctions.dateConvert(date);
        this.message = message;
        this.sent = sent;
    }

    // Constructeur permettant de convertir un objet JSON en une instance Java
    public Message(JSONObject object){
        try {
            this.message = object.getString("message");
            this.date = DateFunctions.dateConvert(object.getString("date"));
            this.sent = object.getBoolean("sent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Conversion d'un tableau d'objets JSON en liste d'objets
    public static ArrayList<Message> fromJson(JSONArray jsonObjects) {
        ArrayList<Message> messages = new ArrayList<Message>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                JSONObject obj = jsonObjects.getJSONObject(i);
                // on affiche le message seulement si il est considéré comme "sent"
                if (obj.getBoolean("sent")) {
                    messages.add(new Message(jsonObjects.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getDate() {
        return this.date;
    }
}
