package com.example.sajib.firebaseinsertptwo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Spinner spinner;
    Button button;
    Button buttonDelete;
    ListView listViewArtists;


    DatabaseReference databaseReference;
    DatabaseReference mDatabase;
    ArrayList<Artist> artistList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=(EditText)findViewById(R.id.editTextid);
        spinner=(Spinner)findViewById(R.id.spinnerid);
        button=(Button)findViewById(R.id.button2id);

        artistList=new ArrayList<>();
        listViewArtists=(ListView)findViewById(R.id.listviewid);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("dataset");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("AlLocation");

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               artistList.clear();
               for (DataSnapshot dp : dataSnapshot.getChildren()){

                   Artist Mod = dp.getValue(Artist.class);
                   Mod.id = dp.getKey();
                   artistList.add(Mod);

               }
               adapter=new CustomAdapter(MainActivity.this,artistList);
               listViewArtists.setAdapter(adapter);



//               listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                   @Override
//                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                       Artist model = artistList.get(i);
//                       artistList.remove(i);
//                       databaseReference.child(model.id).removeValue();
//
//                   }
//               });


           listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   Artist artist=artistList.get(i);
                   updatshoDialog(artist.getId(),artist.getName());
               }
           });


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }
    private  void updatshoDialog(final String artistid, String artistname){
        AlertDialog.Builder aleartDialogbuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final  View dialoView=inflater.inflate(R.layout.update_layout,null);
        aleartDialogbuilder.setView(dialoView);
        final  EditText editText=dialoView.findViewById(R.id.editetextid);
        final  Spinner genres=dialoView.findViewById(R.id.spinnerid);
        final Button button=dialoView.findViewById(R.id.buttonid);
        final Button buttondelete=dialoView.findViewById(R.id.deleteid);
        aleartDialogbuilder.setTitle("Update"+" "+ artistname);
        final AlertDialog alertDialog=aleartDialogbuilder.create();
        alertDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name=editText.getText().toString().trim();
            String spinnergenres=genres.getSelectedItem().toString();
            if(TextUtils.isEmpty(name)){
                editText.setError("name required");
                return;
            }
            updateartist(artistid,name,spinnergenres);
            alertDialog.dismiss();

            }
        });
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(artistid);
            }
        });

    }
    private  void deleteArtist(String artistid){
DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("dataset").child(artistid);
databaseReference.removeValue();
        Toast.makeText(this, "delete successfully", Toast.LENGTH_SHORT).show();
    }
    private  boolean updateartist(String id,String name,String genre){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("dataset").child(id);
        Artist artist=new Artist(id,name,genre);
        databaseReference.setValue(artist);
        Toast.makeText(this, "update successfully", Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public void Addbutton(View view) {
        final String name=editText.getText().toString().trim();
        final String genre=spinner.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name)){
            final DatabaseReference newpost=databaseReference.push();
//            String id = newpost.getKey();
//            newpost.child("id").setValue(id);
//            newpost.child("name").setValue(name);
//            newpost.child("artistGenre").setValue(genre);

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String id = newpost.getKey();
                    newpost.child("id").setValue(id);
                    newpost.child("name").setValue(name);
                    newpost.child("artistGenre").setValue(genre);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Toast.makeText(this, "insert successfull", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"enter a name",Toast.LENGTH_SHORT).show();
        }
    }
}
