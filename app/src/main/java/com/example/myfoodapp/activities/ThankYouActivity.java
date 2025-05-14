package com.example.myfoodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.MainActivity;
import com.example.myfoodapp.R;

public class ThankYouActivity extends AppCompatActivity {

    private TextView counterText;
    private CountDownTimer countDownTimer;
    private int secondsLeft = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        TextView message = findViewById(R.id.thank_you_message);
        counterText = findViewById(R.id.counter_text);
        Button goHomeNowBtn = findViewById(R.id.go_home_now_btn);

        startCountdown();

        goHomeNowBtn.setOnClickListener(v -> navigateToHome());
    }

    private void startCountdown() {
        counterText.setText("Going back to home in " + secondsLeft + " seconds");

        countDownTimer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsLeft--;
                counterText.setText("Going back to home in " + secondsLeft + " seconds");
            }

            public void onFinish() {
                navigateToHome();
            }
        };
        countDownTimer.start();
    }

    private void navigateToHome() {
        if (countDownTimer != null) countDownTimer.cancel();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
