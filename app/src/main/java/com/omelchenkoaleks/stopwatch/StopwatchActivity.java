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
    // хранит информацию о том, работал ли секундомер перед вызовом метода onStop()
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        // восстанавливаем состояние активности по значениям, прочитанным из Bundle
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

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

    // метод для сохранения значений переменных для их будущего востановления вместе с активностью
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // если активность приостанавливается, остановить отсчет времени
    @Override
    protected void onPause() {
        super.onPause();
        // сохраняем информацию о том, работал ли секундомер на момент вызова метода onStop()
        wasRunning = running;
        running = false;
    }

    // если активность возобновляет работу, снова запустить отсчет времени, если он происходил до этого
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
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
