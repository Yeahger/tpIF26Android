package com.tpif26.pauphilet_romero.hanjaout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tpif26.pauphilet_romero.hanjaout.adapters.ContactsAdapter;
import com.tpif26.pauphilet_romero.hanjaout.adapters.MessagesAdapter;
import com.tpif26.pauphilet_romero.hanjaout.models.Contact;
import com.tpif26.pauphilet_romero.hanjaout.models.Message;
import com.tpif26.pauphilet_romero.hanjaout.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activités listant les messages de l'utilisateur
 */
public class MessagesActivity extends Activity {

    // booléen déterminant si une erreur est apparue lors de la connexion
    private Boolean error = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // récupération de l'intent
        final Intent intent = getIntent();
        // création d'un toast pour afficher les erreurs
        final Toast toast = Toast.makeText(getApplicationContext(), R.string.error_general, Toast.LENGTH_SHORT);
        // liste des messages
        final ListView listView = (ListView) findViewById(R.id.listMessages);

        if (intent != null) {
            // création d'un nouveau thread pour exécuter la requête HTTP en parallèle
            Thread thread = new Thread()
            {
                @Override
                public void run() {
                    // on forme l'URL avec en paramètre le token
                    String url= "http://train.sandbox.eutech-ssii.com/messenger/messages.php"
                            + "?token=" + intent.getStringExtra("token")
                            + "&contact=" + intent.getStringExtra("id");

                    // on exécute la requête HTTP
                    HttpRequest request = new HttpRequest(url);

                    try {
                        // on traduit la réponse en objet JSON
                        final JSONObject json = new JSONObject(request.getResponse());
                        error = json.getBoolean("error");
                        // si il n'y a pas d'erreur
                        if (!error) {
                            // liste de messages
                            ArrayList<Message> messages = new ArrayList<Message>();
                            // création de l'adapter
                            final MessagesAdapter adapter = new MessagesAdapter(getApplicationContext(), messages);
                            // listView de messages

                            JSONArray jsonMessages = json.getJSONArray("messages");
                            final ArrayList<Message> newMessages = Message.fromJson(jsonMessages);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // on lie l'adapter à la ListView
                                    listView.setAdapter(adapter);
                                    adapter.addAll(newMessages);
                                }
                            });

                        } else {
                            toast.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
