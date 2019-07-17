package com.abcsoft.paisesbanderas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.paisesbanderas.R;
import com.abcsoft.paisesbanderas.model.Country;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CountriesAdapters extends BaseAdapter {

    private List<Country> countires;
    private LayoutInflater inflater;

    public CountriesAdapters(Context context, List <Country> countires){
        this.countires = countires;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countires.size();
    }

    @Override
    public Object getItem(int position) {
        return countires.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Indica como debe pintar la fila 'position'

        View view = inflater.inflate(R.layout.row_country, null);

        TextView pais = (TextView) view.findViewById(R.id.idPais);
        TextView poblacion = (TextView) view.findViewById(R.id.idPopulation);
        TextView capital = (TextView) view.findViewById(R.id.idCapital);
        TextView gentilicio = (TextView) view.findViewById(R.id.idDemonym);
        TextView frontera = (TextView) view.findViewById(R.id.idBorders);
        ImageView bandera = (ImageView) view.findViewById(R.id.idBandera);

        Country country = countires.get(position);

        pais.setText(country.getName());
        poblacion.setText(String.valueOf(country.getPopulation()));
        capital.setText(country.getCapital());
        gentilicio.setText(country.getDemonym());
        frontera.setText(country.getBorders().toString());

        String imageURL = "https://www.countryflags.io/" + country.getAlpha2Code() + "/flat/64.png";

        //Uso el método estatico get() de la clase picasso para mostrar las imagenes
        //placeholder -> imagen que se muestra mientras no carguen las otras
//        Picasso.get().load(imageURL).placeholder(R.drawable.).into(bandera);
        Picasso.get().load(imageURL).into(bandera);


        return view;
    }
}
