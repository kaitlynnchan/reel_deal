package cmpt276.assign3.assign3game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cmpt276.assign3.assign3game.model.GameConfigs;
import cmpt276.assign3.assign3game.model.ItemsManager;

/**
 * Main menu
 * Displays play, options, and help buttons to navigate screens
 */
public class MainActivity extends AppCompatActivity {

    private GameConfigs config = GameConfigs.getInstance();

    public static Intent makeLaunchIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        createItemsManager();
        playWelcomeScreen();
        setupButtons();
        setupMainBackground();
    }

    private void createItemsManager() {
        ItemsManager items = ItemsManager.getInstance();

        int numObjects = OptionsActivity.getNumObjects(this);
        manager.setTotalItems(numObjects);
        int rows = OptionsActivity.getNumRows(this);
        manager.setRows(rows);
        int columns = OptionsActivity.getNumColumns(this);
        manager.setCols(columns);


        int index = config.getIndex(items);
        if(index == -1){
            items.setHighScore(-1);
            config.add(items);
        } else{
            int highScore = config.get(index).getHighScore();
            items.setHighScore(highScore);
        }
    }

    private void playWelcomeScreen() {
        // Implement welcome screen
    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(GameActivity.SHARED_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(GameActivity.EDITOR_GAME_CONFIG, null);
        Type type = new TypeToken<ArrayList<ItemsManager>>() {}.getType();
        ArrayList<ItemsManager> arrTemp = gson.fromJson(json, type);
        if(arrTemp != null) {
            config.setConfigs(arrTemp);
        }

    }

    private void setupButtons() {
        final Button btnPlay = findViewById(R.id.buttonPlay);
        btnPlay.setBackground(this.getResources().getDrawable(R.drawable.button_shadow));
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.button_border));

                Intent intent = GameActivity.makeLaunchIntent(MainActivity.this, false);
                startActivityForResult(intent, 42);
            }
        });


        final Button btnOptions = findViewById(R.id.buttonOptions);
        btnOptions.setBackground(this.getResources().getDrawable(R.drawable.button_shadow));
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setup options screen
                btnOptions.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.button_border));
                Intent intent = OptionsActivity.makeLaunchIntent(MainActivity.this);
                startActivityForResult(intent, 1);
            }
        });

        final Button btnHelp = findViewById(R.id.buttonHelp);
        btnHelp.setBackground(this.getResources().getDrawable(R.drawable.button_shadow));
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setup to help screen
                btnHelp.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.button_border));

                Intent intent = HelpActivity.makeLaunchIntent(MainActivity.this);
                startActivityForResult(intent, 42);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case Activity.RESULT_CANCELED:
                // Reset buttons
                setupButtons();
                createItemsManager();
                break;
            case GameActivity.RESULT_OK:
                setupButtons();
                break;
            case OptionsActivity.RESULT_OK:
                createItemsManager();
                break;
            default:
                assert false;
        }
    }

    private void setupMainBackground() {
        // Implement background based on theme
    }
}
