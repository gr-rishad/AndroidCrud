package com.example.rishad.sqlitedatabasconnection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;
    private Button addButton,showButton,updateButton,deleteButton;
    private EditText nameEditText,ageEditText,genderEditText,idEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);
       SQLiteDatabase sqLiteDatabase= myDatabaseHelper.getWritableDatabase();

       addButton = (Button) findViewById(R.id.addButtonId);
       nameEditText=(EditText) findViewById(R.id.nameEditTextId);
       ageEditText =(EditText) findViewById(R.id.ageEditTextId);
       genderEditText=(EditText) findViewById(R.id.genderEditTextId);
        idEditText =(EditText) findViewById(R.id.idEditTextId);
       showButton = (Button) findViewById(R.id.showButtonId);
        updateButton = (Button) findViewById(R.id.updateButtonId);
        deleteButton = (Button) findViewById(R.id.deleteButtonId);

       addButton.setOnClickListener(this);
       showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name= nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();

        if(v.getId()== R.id.addButtonId){
             long rowId=myDatabaseHelper.insertData(name,age,gender);

             if(rowId== -1){
                 Toast.makeText(getApplicationContext()," Data can't Inserted",Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(getApplicationContext(),"Row "+rowId+" Successfully Inserted",Toast.LENGTH_SHORT).show();
             }
        }
        if(v.getId()==R.id.showButtonId){

           Cursor rsltSet= myDatabaseHelper.displayAllData();

           if(rsltSet.getCount() == 0){
               showData("Error","No Data Found");
               return;
           }
           StringBuffer stringBuffer=new StringBuffer();
           while (rsltSet.moveToNext()){
               stringBuffer.append("ID : "+rsltSet.getString(0)+"\n");
               stringBuffer.append("Name : "+rsltSet.getString(1)+"\n");
               stringBuffer.append("Age : "+rsltSet.getString(2)+"\n");
               stringBuffer.append("Gender : "+rsltSet.getString(3)+"\n \n \n");
           }
           showData("ResultSet",stringBuffer.toString());
        }
        else if(v.getId()==R.id.updateButtonId){
          Boolean isUpdated=  myDatabaseHelper.updateData(id,name,age,gender);

          if(isUpdated== true){
              Toast.makeText(getApplicationContext(),"Data is Updated",Toast.LENGTH_SHORT).show();
          }else {
              Toast.makeText(getApplicationContext()," Updated Failed",Toast.LENGTH_SHORT).show();
          }
        }

        if(v.getId()==R.id.deleteButtonId){
           int value= myDatabaseHelper.deleteData(id);

            if(value>0){
                Toast.makeText(getApplicationContext(),"Data is deleted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Data is not deleted",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void showData(String title,String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}
