package com.tpif26.pauphilet_romero.hanjaout.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Contact {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Message message;

    // Constructeur permettant de convertir un objet JSON en une instance Java
    public Contact(JSONObject object){
        try {
            JSONObject contact = object.getJSONObject("contact");
            JSONObject message = object.getJSONObject("message");

            this.id = object.getInt("id");
            this.firstName = contact.getString("first_name");
            this.lastName = contact.getString("last_name");
            this.email = contact.getString("email");
            this.message = new Message(message.getString("message"), message.getString("date"), message.getBoolean("sent") );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Conversion d'un tableau d'objets JSON en liste d'objets
    public static ArrayList<Contact> fromJson(JSONArray jsonObjects) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                contacts.add(new Contact(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }

    public int getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public Message getMessage() {
        return this.message;
    }
}
