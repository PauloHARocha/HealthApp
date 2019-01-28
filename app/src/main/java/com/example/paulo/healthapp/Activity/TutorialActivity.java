package com.example.paulo.healthapp.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paulo.healthapp.Activity.Splash.SplashAppActivity;
import com.example.paulo.healthapp.R;

public class    TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout pontosLayout;
    private TextView[] pontos;
    private int[] layouts;
    private Button btn_pular, btn_proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Deixa a barra de notificacao transparente
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.tutorial_activity);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pontosLayout = (LinearLayout) findViewById(R.id.layoutPontos);
        btn_pular = (Button) findViewById(R.id.btn_pular);
        btn_proximo = (Button) findViewById(R.id.btn_proximo);


        //inicializa os layouts de cada slide
        layouts = new int[]{
                R.layout.tutorial_slide_paciente,
                R.layout.tutorial_slide_responsavel,
                R.layout.tutorial_slide_tratamento,
                R.layout.tutorial_slide_medicamento};

        // adiciona os pontos que simbolizam a posicao do slide
        adicionarPontos(0);

        // deixa a barra de notificacao transparente
        changeStatusBarColor();
        //Inicializa a viewPager
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btn_pular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gera uma notificacao
                Notify();
                //volta para a activity principal
                launchHomeScreen();
            }
        });

        btn_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checa se é o último slide
                int tela_atual = getItem(+1);
                if (tela_atual < layouts.length) {
                    // move par ao proximo slide
                    viewPager.setCurrentItem(tela_atual);
                } else {
                    //volta para a activity principal
                    launchHomeScreen();
                }
            }
        });
    }

    private void adicionarPontos(int currentPage) {
        pontos = new TextView[layouts.length];//gera o mesmo numero de pontos que o de slides

        pontosLayout.removeAllViews();
        for (int i = 0; i < pontos.length; i++) {
            pontos[i] = new TextView(this);
            pontos[i].setText(Html.fromHtml("&#8226;"));//simbolo do ponto
            pontos[i].setTextSize(35);//tamanho do simbolo
            pontos[i].setTextColor(getResources().getColor(R.color.dot_desativado));//cor do simbolo
            pontosLayout.addView(pontos[i]);//adiciona os pontos ao layout da acitivty
        }

        if (pontos.length > 0)
            pontos[currentPage].setTextColor(getResources().getColor(R.color.dot_ativado));//modifica a cor do ponto do slide atual
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(TutorialActivity.this, PrincipalActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            adicionarPontos(position);

            // muda o botão de "próximo" para "começar" pois é o último slide
            if (position == layouts.length - 1) {
                // último slide
                btn_proximo.setText("começar");
                btn_pular.setVisibility(View.GONE);
            } else {
                // não é o último slide
                btn_proximo.setText("próximo");
                btn_pular.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void Notify(){
        // prepara a intent que vai ser chamada se a notification for selecionada
        Intent intent = new Intent(this, SplashAppActivity.class);
        // utiliza System.currentTimeMillis() para gerar um id unico para a pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // configura a notification
        // pode ser utilizada a funcao addAction para adicionar ações se botões forem selecionados na notification
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Tutorial")
                .setContentText("Acompanhe os passos do tutorial")
                .setSmallIcon(R.drawable.splash_logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    /**
     * Deixa a barra de notificacao transparente
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}