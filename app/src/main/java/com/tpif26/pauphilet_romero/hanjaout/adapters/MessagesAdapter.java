package com.tpif26.pauphilet_romero.hanjaout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tpif26.pauphilet_romero.hanjaout.R;
import com.tpif26.pauphilet_romero.hanjaout.models.Message;
import com.tpif26.pauphilet_romero.hanjaout.utils.DateFunctions;

import java.util.ArrayList;

public class MessagesAdapter extends ArrayAdapter<Message> {

    /**
     * Cache permettant d'améliorer les performances lors de l'affichage sur la vue
     */
    private static class ViewHolder {
        TextView message;
        TextView date;
    }

    /**
     * Constructeur de l'adapter
     * @param context
     * @param messages Liste des messages
     */
    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    /**
     * Retourne la vue à l'écran
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // retourne les données de l'objet à une position donnée
        Message message = getItem(position);

        // vérifie si une vue existante est utilisée
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            viewHolder.message = (TextView) convertView.findViewById(R.id.message);
            viewHolder.date = (TextView) convertView.findViewById(R.id.messageDate);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // attribue les données de l'objet aux éléments de la vue
        viewHolder.message.setText(message.getMessage());
        viewHolder.date.setText(DateFunctions.diffDate(message.getDate()));

        // retourne la vue à l'écran
        return convertView;
    }
}
