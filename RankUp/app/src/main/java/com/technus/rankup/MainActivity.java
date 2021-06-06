package com.technus.rankup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.technus.rankup.Fetch.results;

public class MainActivity extends AppCompatActivity {

    String currentSkill;
    public static String userName;
    String result;
    public static  String resultOutput;
    String strLimit;
    public static int limit;
    int lim;
    EditText ipUserName;
    EditText ipLimit;
    TextView outputResult;
    Button btnSearch,btnShow,btnHelp;
    String helpTxt = "*Make sure to write your in-game name correctly.\n" +
            "*Search limit must be greater than 0.\n" +
            "*Search limit is the max rank limit the app will search for, if the data of one of the skills remain 0 that mean your rank is lower than the limit, try to set it bigger.\n" +
            "*The app check the players names one by one , the lower your rank is ,the longer the search will be(10s for each 1000 ranks).\n"+
            "*You have to update the results manually, either let the app run for some minutes then update them or spam-click the button.\n"+
            "*The app extract the data from the official website, if the app doesn't work:\n"+
                        "     **Check your internet connection.\n"+
                        "     **Check if the website is working.\n"+
                        "     **Check for new updates on github.com/TechNus09/RankUp/releases/.\n"+
            "Note:The website update itself every few minutes (around 5mn) so its data will be 5mn later than the in-game data.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipUserName = findViewById(R.id.ipUserName);
        ipLimit = findViewById(R.id.ipLimit);
        outputResult = findViewById(R.id.outputResult);
        btnSearch = findViewById(R.id.btnSearch);
        btnShow = findViewById(R.id.btnShow);
        btnHelp = findViewById(R.id.btnHelp);

        //show help instructs
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CreateAlertDialog();

            }
        });


        // search for player data
        btnSearch.setOnClickListener(v -> {
            try {
                userName = ipUserName.getText().toString();
            }
            catch (NullPointerException ex) {
                showToast("please enter a UserName");
                return;
            }
            if (userName.equals(""))
                showToast("please enter a UserName");
            try {
                strLimit = ipLimit.getText().toString();
                lim = Integer.parseInt(strLimit);
                limit = (lim / 20) + 1;
            }
            catch (NumberFormatException ex) {
                showToast("please enter a valid number");
                return;
            }
            showToast("Searching...");
            Fetch process = new Fetch();
            process.execute();

        });

        //     update the search results
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k;

                resultOutput = "-------RESULT-------\n"+
                               " Skill : LVL | XP | Rank \n";
                for (k=0;k<7;k++) {
                resultOutput = resultOutput + " "+results[k][0] +" : "+results[k][1]+"-"+results[k][4]+"%"+" | " + results[k][2] + " | " + results[k][3] + "\n";
                }
                outputResult.setText(resultOutput);

            }
        });



    }

    private void CreateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("how to use me");
        builder.setMessage(helpTxt);
        builder.show();
    }




    public void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

}