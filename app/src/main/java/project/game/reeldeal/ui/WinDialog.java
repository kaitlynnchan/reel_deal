package project.game.reeldeal.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import project.game.reeldeal.R;

/**
 * Win Dialog
 * Displays congratulations message
 */
public class WinDialog extends AppCompatDialogFragment {

    private int score;
    private int highScore;

    public WinDialog(int scans, int highScore) {
        this.score = scans;
        this.highScore = highScore;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_win, null);

        TextView textScore = view.findViewById(R.id.text_score);
        String strScore = getString(R.string.score);
        strScore += "  " + score;
        textScore.setText(strScore);

        if(score < highScore || highScore == -1){
            TextView textBest = view.findViewById(R.id.text_best);
            textBest.setVisibility(View.VISIBLE);
        }

        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }
}
