package fail.minesweeper;

import android.app.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

import AppLogic.GameLogic.GameConfig;
import AppLogic.RecordsLogic.GameRecord;
import AppLogic.RecordsLogic.RecordController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordMapFragment extends Fragment implements OnMapReadyCallback {
    private RecordController recCon;
    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;
    private Map<Marker, GameRecord> gameRecordMap;


    private OnFragmentInteractionListener mListener;

    public RecordMapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecordMapFragment newInstance(int currentLevel) {
        RecordMapFragment fragment = new RecordMapFragment();
        Bundle args = new Bundle();
        args.putInt("level", currentLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_record_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.mapView);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setRecordController(RecordController recCon) {
        this.recCon = recCon;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });


        ArrayList<GameRecord> arr = recCon.getRecordsArray(GameConfig.BEGINNER_LEVEL);
        CameraPosition cPos = null;
        for(GameRecord gr : arr){
            double longitude = gr.getLongitude();
            double latitude  = gr.getLatitude();
            LatLng pos = new LatLng(latitude,longitude);
            cPos = CameraPosition.builder().target(pos).zoom(15).build();
            mMap.addMarker(new MarkerOptions().position(pos).title(gr.getName()).snippet("Score: "+gr.recordTimeToString()));
        }
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cPos));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
