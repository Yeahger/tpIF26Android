package com.tpif26.pauphilet_romero.hanjaout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tpif26.pauphilet_romero.hanjaout.utils.ConnectionDetector;
import com.tpif26.pauphilet_romero.hanjaout.utils.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activité principale : connexion au service
 */
public class MainActivity extends Activity {

    // booléen déterminant si une erreur est apparue lors de la connexion
    private Boolean error = true;
    // champ texte de l'email
    private EditText emailText;
    // champ texte du mot de passe
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = (EditText) findViewById(R.id.user_email);
        passwordText = (EditText) findViewById(R.id.user_password);
    }

    public void login(View view) {

        // récupération de l'email
        final String email = emailText.getText().toString();
        // récupération du mot de passe
        final String password = passwordText.getText().toString();
        // création de l'intent
        final Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
        // création d'un toast pour afficher les erreurs
        final Toast toast = Toast.makeText(getApplicationContext(), R.string.error_empty_fields, Toast.LENGTH_SHORT);

        // on vérifie la connexion Internet
        ConnectionDetector connection = new ConnectionDetector(getApplicationContext());
        if(connection.isConnectingToInternet()) {
            // on vérifie que les champs ne sont pas vides
            if (!email.isEmpty() && !password.isEmpty()) {

                // nouveau thread pour la requête HTTP
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Requête http
                        HttpRequest request = new HttpRequest("http://train.sandbox.eutech-ssii.com/messenger/login.php?email=" + email + "&password=" + password);
                        try {
                            // on traduit la réponse en objet JSON
                            JSONObject json = new JSONObject(request.getResponse());
                            error = json.getBoolean("error");
                            // si il n'y a pas d'erreur
                            if (!error) {
                                // on envoie le token à l'activité ContactsActivity
                                intent.putExtra("token", json.getString("token"));
                                // changement d'activité
                                startActivity(intent);
                                // sinon on affiche le toast avec le message d'erreur correspondant
                            } else {
                                toast.setText(R.string.error_connect);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            } else {
                toast.show();
            }
        } else {
            toast.setText(R.string.error_internet);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
