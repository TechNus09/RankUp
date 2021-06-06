package com.technus.rankup;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.technus.rankup.MainActivity.userName;
import static com.technus.rankup.MainActivity.limit;

public class Fetch extends AsyncTask<Void,Integer,Void> {

    String[] example = new String[]{"skill","lvl","xp","rank","%"};
    public static String[] combat= new String[]{"Combat","1","0","0","0"};
    public static String[] mining= new String[]{"Mining","1","0","0","0"};
    public static String[] smithing= new String[]{"Smithing","1","0","0","0"};
    public static String[] woodcutting= new String[]{"Woodcutting","1","0","0","0"};
    public static String[] crafting= new String[]{"Crafting","1","0","0","0"};
    public static String[] fishing= new String[]{"Fishing","1","0","0","0"};
    public static String[] cooking= new String[]{"Cooking","1","0","0","0"};
    public static  String[][] results = new String[][]{combat,mining,smithing,woodcutting,crafting,fishing,cooking};


    String[] skills={"","-mining","-smithing","-woodcutting","-crafting","-fishing","-cooking"};

        @Override
        protected Void doInBackground(Void... voids) {
            JSONParser parser = new JSONParser();
            int i,j;
            for(i=0; i<7; i++) { //loop through skills
                int rank ;
                for (j=0;j<limit;j++) //loop through pages
                {
                    try {
                        URL url = new URL("https://www.curseofaros.com/highscores"+skills[i]+".json?p="+ j); // URL to Parse
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        String inputLine;
                        while ((inputLine = bufferedReader.readLine()) != null) {
                            JSONArray page = (JSONArray) parser.parse(inputLine); //get page
                            int k =0;
                            for (Object player : page) { // loop through players in the page
                                k++;
                                JSONObject playerData = (JSONObject) player; //get player data
                                String name = (String) playerData.get("name"); //get player name
                                if (name.equals(userName)) { //check if player is the user

                                    rank = 20 * j + k; //get rank
                                    Long xp = (Long) playerData.get("xp"); //get xp amount
                                    short lvl = Get.Lvl(xp); //get lvl
                                    double perc = Get.Percent(xp, lvl); //get percentage
                                    results[i][1] = String.valueOf(lvl);
                                    results[i][2] = String.valueOf(xp);
                                    results[i][3] = String.valueOf(rank);
                                    results[i][4] = String.valueOf(perc);
                                    break;
                                }

                            }
                        }
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                        }
                }
                    }

                return null;
            }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
