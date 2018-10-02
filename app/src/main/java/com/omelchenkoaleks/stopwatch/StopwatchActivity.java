package com.omelchenkoaleks.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class StopwatchActivity extends Activity {

    // хранится текущее количество секунд
    private int seconds = 0;
    // хранит значение работает ли секундомер в настоящий момент = Флаг работы
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
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
}
