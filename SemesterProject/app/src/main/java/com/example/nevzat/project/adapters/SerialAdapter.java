package com.example.nevzat.project.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nevzat.project.models.Person;
import com.example.nevzat.semesterproject.R;

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
        TextView phone = (TextView) view.findViewById(R.id.phone);

        Person person = personList.get(position);
        name.setText(person.getName());
        surname.setText(person.getSurname());
        phone.setText(person.getPhone().get(0).getPhoneNumber());

        return view;
    }
}
