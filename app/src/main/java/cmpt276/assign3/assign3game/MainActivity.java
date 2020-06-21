package cmpt276.assign3.assign3game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import cmpt276.assign3.assign3game.model.ItemsManager;

/**
 * Main menu
 * Displays play, options, and help buttons to navigate screens
 */
public class MainActivity extends AppCompatActivity {

    private ItemsManager manager = ItemsManager.getInstance();
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createItemsManager();
        playWelcomeScreen();
        setupButtons();
        setupMainBackground();
    }

    private void createItemsManager() {
        int numObjects = OptionsActivity.getNumObjects(this);
        manager.setTotalItems(numObjects);
        int rows = OptionsActivity.getNumRows(this);
        manager.setRows(rows);
        int columns = OptionsActivity.getNumColumns(this);
        manager.setCols(columns);
    }

    private void playWelcomeScreen() {
        // Implement welcome screen
    }

    private void setupButtons() {
        final Button btnPlay = findViewById(R.id.buttonPlay);
        btnPlay.setBackground(this.getResources().getDrawable(R.drawable.button_shadow));
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.button_border));
                /*if (flag == 0)
                {
                    manager.setRows(4);
                    manager.setCols(6);
                    manager.setTotalItems(6);
                }*/
                Intent intent = GameActivity.makeLaunchIntent(MainActivity.this);
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
                flag++;
                Intent intent = OptionsActivity.makeLaunchIntent(MainActivity.this);
                startActivity(intent);
                /*OptionsActivity options = new OptionsActivity();
                manager.setTotalItems(options.savedNumObjects);
                manager.setCols(options.savedColumns);
                manager.setRows(options.savedRows);*/
            }
        });

        final Button btnHelp = findViewById(R.id.buttonHelp);
        btnHelp.setBackground(this.getResources().getDrawable(R.drawable.button_shadow));
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setup to help screen
                btnHelp.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.button_border));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case GameActivity.RESULT_CANCELED:
                // Reset buttons
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
