package com.example.nevzat.semesterproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.nevzat.semesterproject.R;
import com.example.nevzat.semesterproject.models.Person;
import java.util.List;

public class SerialAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Person> personList;

    public SerialAdapter(Activity activity,List<Person> p){
        layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        personList = p;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = layoutInflater.inflate(R.layout.list_view,null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView surname = (TextView) view.findViewById(R.id.surname);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView id = (TextView) view.findViewById(R.id.id);

        Person person = personList.get(position);
        name.setText(person.getName());
        surname.setText(person.getSurname());
        email.setText(person.geteMail());
        id.setText(person.getPid());

        return view;
    }
}
