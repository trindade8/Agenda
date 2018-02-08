package br.com.alura.agenda;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.location.LocationListener;
/**
 * Created by wesley on 10/01/18.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {


    private GoogleApiClient client;
    private GoogleMap mapa;
    private MapaFragment mapaFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public Localizador(Context context, MapaFragment mapaFragment) {
        this.mapa = mapa;
        this.mapaFragment = mapaFragment;

        this.client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        this.client.connect();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.mapaFragment.getActivity());

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(50);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {

            //LocationServices.FusedLocationApi.requestLocationUpdates(client,request,this);



                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //fusedLocationProviderClient.requestLocationUpdates(this.mapaFragment, request, this);



        }catch (Exception e )
        {

        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        mapaFragment.centralizaEm(latLng);



    }


    private boolean PossuiPermissao(Context context, String permissao){
        Boolean status = false;
        status = ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == 0 ? false : true;
        return  status;
    }

    private void SolicitaPermissaoAoUsuario(Activity activity, String permissao, int requestCode)
    {

        ActivityCompat.requestPermissions(activity,new String[]{permissao},requestCode);
    }

    private void PersistePermissaoUsuario( Activity activity, String permissao , int requestCode)
    {
        if(!PossuiPermissao(activity,permissao))
        {
            SolicitaPermissaoAoUsuario(activity,permissao,requestCode);
        }
    }
}
