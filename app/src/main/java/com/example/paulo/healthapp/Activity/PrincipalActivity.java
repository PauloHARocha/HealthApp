package com.example.paulo.healthapp.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulo.healthapp.Activity.Paciente.PacienteActivity;
import com.example.paulo.healthapp.Activity.Paciente.PacienteEditarActivity;
import com.example.paulo.healthapp.Activity.Paciente.PacienteNovoActivity;
import com.example.paulo.healthapp.Activity.Responsavel.ResponsavelListActivity;
import com.example.paulo.healthapp.Activity.Tratamento.TratamentoListActivity;
import com.example.paulo.healthapp.AlertReceiver;
import com.example.paulo.healthapp.Fragment.LoginDialogFragment;
import com.example.paulo.healthapp.Fragment.PrincipalFragment;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelLogin;
import com.example.paulo.healthapp.Model.ModelPaciente;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceLogin;
import com.example.paulo.healthapp.Service.ServicePaciente;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Adapter adapter;
    private ViewPager viewPager;
    private ModelLogin modelLogin;
    private MenuItem itemLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);

        // Adiciona a toolbar a tela
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configura a ViewPager para cada tab
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Configura as tabs de acordo a ViewPager
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //Configura e inicaliza o menu drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        inicializaNomePaciente();
        inicializaDatadoDia();

        inicializaAlarmes();

        itemLogin = navigationView.getMenu().getItem(5);

        if(ServiceLogin.getLoginStatus(1).getStatus() == 0)
            itemLogin.setVisible(false);
        else
            itemLogin.setVisible(true);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        inicializaNomePaciente();
        inicializaAlarmes();
        //Atualiza o recyclerView da viewPager se novos itens foram adicionados
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);

        if(ServiceLogin.getLoginStatus(1).getStatus() == 0)
            itemLogin.setVisible(false);
        else
            itemLogin.setVisible(true);
        super.onResume();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_paciente) {
            ModelPaciente modelPaciente = ServicePaciente.getById(1);
            if(modelPaciente.getIdPaciente() == 1) {
                startActivity(new Intent(getApplicationContext(),PacienteActivity.class));
            }else{
                if(ServiceLogin.getLoginStatus(1).getStatus() == 0){
                    acessoLogin("NovoPaciente");
                }else{
                    startActivity(new Intent(getApplicationContext(), PacienteNovoActivity.class));
                }
            }
        } else if (id == R.id.nav_responsavel) {
            startActivity(new Intent(this,ResponsavelListActivity.class));
        } else if (id == R.id.nav_tratamento) {
            startActivity(new Intent(this,TratamentoListActivity.class));
        } else if (id == R.id.nav_sobre) {
            startActivity(new Intent(getApplicationContext(), SobreActivity.class));
        }else if (id == R.id.nav_historico) {
            if(ServiceTratamento.getList().size() <= 0)
                Toast.makeText(getApplicationContext(),"Nenhum tratamento cadastrado",Toast.LENGTH_SHORT).show();
            else
                startActivity(new Intent(getApplicationContext(), DetalhamentoActivity.class));
        }/*else if (id == R.id.nav_tutorial) {
            startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
        }*/
        else if (id == R.id.nav_logout) {
            modelLogin = ServiceLogin.getLoginStatus(1);
            modelLogin.setStatus(0);
            ServiceLogin.update(modelLogin);
            Toast.makeText(getApplicationContext(),"Logout realizado",Toast.LENGTH_SHORT).show();
            item.setVisible(false);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        String manha = "Manhã";
        String tarde = "Tarde";
        String noite = "Noite";
        //Inicializa cada fragment de acordo com a etapa do dia de cada um
        Bundle args1 = new Bundle();
        args1.putString("etapa", manha);
        PrincipalFragment manhaFragment = new PrincipalFragment();
        manhaFragment.setArguments(args1);

        Bundle args2 = new Bundle();
        args2.putString("etapa", tarde);
        PrincipalFragment tardeFragment = new PrincipalFragment();
        tardeFragment.setArguments(args2);

        Bundle args3 = new Bundle();
        args3.putString("etapa", noite);
        PrincipalFragment noiteFragment = new PrincipalFragment();
        noiteFragment.setArguments(args3);

        //Adiciona os fragments ao adapter da viewPager
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(manhaFragment, manha);
        adapter.addFragment(tardeFragment, tarde);
        adapter.addFragment(noiteFragment, noite);
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);//lista de itens
            mFragmentTitleList.add(title);//titulo da tab
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void acessoLogin(String activity){
        ArrayList<String> valores = new ArrayList<>();
        valores.add(activity);
        Bundle args = new Bundle();
        args.putStringArrayList("activity", valores);

        android.app.FragmentManager fm = getFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setArguments(args);
        loginDialogFragment.show(fm, null);
    }

    private void inicializaNomePaciente(){
        //inicializa o nome do paciente dentro do menu drawer
        ModelPaciente modelPaciente;

        View header= navigationView.getHeaderView(0);

        TextView nomePaciente = (TextView)header.findViewById(R.id.drawer_nome_paciente);

        if (ServicePaciente.getById(1) !=null && ServicePaciente.getById(1).getNomePaciente() != null){
            modelPaciente = ServicePaciente.getById(1);
            nomePaciente.setText(modelPaciente.getNomePaciente());
        }else
            nomePaciente.setText("Paciente");
    }

    private void inicializaDatadoDia(){
        //inicializa a data do dia dentro da appbar
        TextView dataHoje = (TextView)findViewById(R.id.principal_txv_data);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy");
        dataHoje.setText(simpleFormat.format(new Date()));
    }

    private void inicializaAlarmes(){
        List<ModelHistoricoMedicamento> listHistoricoMedicamento = ServiceHistoricoMedicamento.getListByDate(new Date(), new Date());
        if(listHistoricoMedicamento != null)
            for(int i = 0; i < listHistoricoMedicamento.size();i++){
                ModelHistoricoMedicamento historicoMedicamento = listHistoricoMedicamento.get(i);
                if(historicoMedicamento.getQtdTomados() < historicoMedicamento.getQtdMedicamento())
                    setAlarm(historicoMedicamento.getMedicamento().getHoraMedicamento(),
                            historicoMedicamento.getQtdTomados(),
                            historicoMedicamento.getMedicamento().getQtdMedicamento(),
                            historicoMedicamento.getIdHistoricoMedicamento());
            }
    }
    public void setAlarm(Date horario,int qtdTomados ,int qtdMedicamento, int id){

        Calendar lembrete = Calendar.getInstance();
        lembrete.setTime(horario);

        Calendar alertTime = Calendar.getInstance();
        alertTime.setTime(new Date());
        alertTime.set(Calendar.HOUR_OF_DAY, lembrete.get(Calendar.HOUR_OF_DAY));
        alertTime.set(Calendar.MINUTE, lembrete.get(Calendar.MINUTE));
        alertTime.set(Calendar.SECOND,0);
        alertTime.set(Calendar.MILLISECOND, 0);

        boolean alarmAtivo = (PendingIntent.getBroadcast(this, id, new Intent(this, AlertReceiver.class), PendingIntent.FLAG_NO_CREATE) == null);

        if (alarmAtivo){
            Intent alertIntent = new Intent(this, AlertReceiver.class);

            SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

            alertIntent.putExtra("horario",formato.format(horario));
            alertIntent.putExtra("qtdTomados",qtdTomados);
            alertIntent.putExtra("qtdMedicamento",qtdMedicamento);
            alertIntent.putExtra("id",id);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime.getTime().getTime(),
                    PendingIntent.getBroadcast(this,id, alertIntent, 0));
        }

    }


}
