package com.tpif26.pauphilet_romero.hanjaout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tpif26.pauphilet_romero.hanjaout.R;
import com.tpif26.pauphilet_romero.hanjaout.models.Contact;
import com.tpif26.pauphilet_romero.hanjaout.models.Message;
import com.tpif26.pauphilet_romero.hanjaout.utils.DateFunctions;

import java.util.ArrayList;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    /**
     * Cache permettant d'améliorer les performances lors de l'affichage sur la vue
     */
    private static class ViewHolder {
        TextView name;
        TextView email;
        TextView date;
        TextView message;
    }

    /**
     * Constructeur de l'adapter
     * @param context
     * @param contacts Liste des contacts
     */
    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    /**
     * Retourne la vue à l'écran
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // retourne les données de l'objet à une position donnée
        Contact contact = getItem(position);
        Message message = contact.getMessage();

        // vérifie si une vue existante est utilisée
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.email = (TextView) convertView.findViewById(R.id.contactEmail);
            viewHolder.date = (TextView) convertView.findViewById(R.id.contactMessageDate);
            viewHolder.message = (TextView) convertView.findViewById(R.id.contactMessage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // attribue les données de l'objet aux éléments de la vue
        viewHolder.name.setText(contact.getFirstName() + " " + contact.getLastName());
        viewHolder.email.setText(contact.getEmail());
        viewHolder.date.setText(DateFunctions.diffDate(message.getDate()));
        viewHolder.message.setText(message.getMessage());
        // retourne la vue à l'écran
        return convertView;
    }
}
