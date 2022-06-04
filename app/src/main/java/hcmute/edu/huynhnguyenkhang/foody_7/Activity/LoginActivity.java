package hcmute.edu.huynhnguyenkhang.foody_7.Activity;

import static hcmute.edu.huynhnguyenkhang.databaselogin.SQLiteHelper.TABLE_NAME;
import static hcmute.edu.huynhnguyenkhang.databaselogin.SQLiteHelper.Table_Column_2_Email;
import static hcmute.edu.huynhnguyenkhang.databaselogin.SQLiteHelper.Table_Column_3_Password;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.huynhnguyenkhang.databaselogin.SQLiteHelper;
import hcmute.edu.huynhnguyenkhang.foody_7.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

 public class LoginActivity extends AppCompatActivity {
     String EmailHolder, PasswordHolder;
     Boolean EditTextEmptyHolder;
     SQLiteDatabase sqLiteDatabaseObj;
     SQLiteHelper sqLiteHelper;
     Cursor cursor;
     String TempPassword = "NOT_FOUND" ;
     public static final String UserEmail = "";
     Button btnAccept;
     EditText edtUsername, edtPassword;
     CheckBox cbRemember;
     TextView txtSignUp;
     SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        References();

        sqLiteHelper = new SQLiteHelper(this);
        btnAccept.setOnClickListener(view -> {

            CheckEditTextStatus();

            Login();

        });
        txtSignUp.setOnClickListener(view ->{
            Intent intent =new Intent(this,SignUpActivity.class);
            startActivity(intent);
        });

    }
    @SuppressLint("Range")
    private void Login(){
        if(EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(TABLE_NAME, null, " " + Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(Table_Column_3_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();
        }
        else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(this,"Please Enter User name or Password.",Toast.LENGTH_LONG).show();

        }
    }
     public void CheckEditTextStatus(){

         // Getting value from All EditText and storing into String Variables.
         EmailHolder = edtUsername.getText().toString();
         PasswordHolder = edtPassword.getText().toString();
         // Checking EditText is empty or no using TextUtils.
         EditTextEmptyHolder = !TextUtils.isEmpty(EmailHolder) && !TextUtils.isEmpty(PasswordHolder);
     }

     // Checking entered password from SQLite database email associated password.
     public void CheckFinalResult(){

         if(TempPassword.equalsIgnoreCase(PasswordHolder))
         {

             Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();

             // Going to Dashboard activity after login success message.
             Intent intent = new Intent(this, MainActivity.class);

             // Sending Email to Dashboard Activity using intent.
             intent.putExtra(UserEmail, EmailHolder);
             startActivity(intent);



         }
         else {

             Toast.makeText(this,"User name or Password is wrong, Please Try Again.",Toast.LENGTH_LONG).show();

         }
         TempPassword = "NOT_FOUND" ;

     }

     private void References(){
        btnAccept = (Button) findViewById(R.id.buttonXacNhan);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);
        edtUsername = (EditText) findViewById(R.id.editTextUserName);
        txtSignUp=(TextView) findViewById(R.id.txtSignUp);
    }

}