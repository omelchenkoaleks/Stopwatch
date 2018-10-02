package com.omelchenkoaleks.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    // хранится текущее количество секунд
    private int seconds = 0;
    // хранит значение работает ли секундомер в настоящий момент = Флаг работы
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        runTimer();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        // останавливаем секундомер
        running = false;
        // обнуляем секундомер
        seconds = 0;
    }

    // чтобы обновить секундомер планируем многократное выполнение кода с использованием Handler
    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        // вызов метода post() обеспечивает выполнение без задержки
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                // код передается на повторное исполнение через 1 секунду
                handler.postDelayed(this, 1000);
            }
        });
    }
}
