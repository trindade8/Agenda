package br.com.alura.agenda;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by wesley on 06/01/18.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);


    }

    public GoogleMap mapa;
    private MarkerOptions currentMarcadorUsuario;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mapa = googleMap;
        //Rua Vergueiro 3185, Vila Mariana, São Paulo
        LatLng coordenada = this.pegaCoordenadaDoEndereco("Frei Francisco Sampaio 246, Embaré");

        if(coordenada != null)
        {
            centralizaEm(coordenada);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for(Aluno aluno : alunoDAO.buscaAlunos())
        {
            LatLng coordenadaAluno = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if(coordenadaAluno != null)
            {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(coordenadaAluno);
                markerOptions.title(aluno.getNome());
                markerOptions.snippet(aluno.getNota().toString());
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_mapa));
                this.mapa.addMarker(markerOptions);

            }
        }

        alunoDAO.close();

         //new Localizador(getContext(),googleMap);
    }

    private void carregaMarcadorUsuario(LatLng coordenada)
    {

    }


    // esse método é novo
    public void centralizaEm(LatLng coordenada) {
        if (mapa != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            mapa.moveCamera(update);
        }
    }
        @Override
    public void onResume() {
        super.onResume();
        getMapAsync(this);
    }

    private LatLng pegaCoordenadaDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder (getContext());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }




}
