package br.com.brunocardoso.desafionw.activity;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.brunocardoso.desafionw.R;
import br.com.brunocardoso.desafionw.adapter.PhotosAdapter;
import br.com.brunocardoso.desafionw.api.ClassesService;
import br.com.brunocardoso.desafionw.api.HeroesService;
import br.com.brunocardoso.desafionw.api.PhotosService;
import br.com.brunocardoso.desafionw.api.SpecialtieService;
import br.com.brunocardoso.desafionw.model.Classe;
import br.com.brunocardoso.desafionw.model.Hero;
import br.com.brunocardoso.desafionw.model.Specialty;
import br.com.brunocardoso.desafionw.network.RetrofitInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtHeroiNome,
            edtHeroiClasses,
            edtHeroiHabilidades;

    private TextInputEditText edtHeroiVida,
            edtHeroiDefesa,
            edtHeroiDano,
            edtHeroiVeloAtaque,
            edtHeroiVeloMovimento;

    private Button btnSalvarHeroi;

    private RecyclerView rvHeroiAvatares;
    private LinearLayoutManager mLayoutManager;
    private PhotosAdapter adapter;

    private Classe classeSelected;
    private boolean[] specialtieCheckedList;

    private List<Classe> classeList = new ArrayList<>();
    private List<Specialty> specialtyList = new ArrayList<>();
    private List<Specialty> specialtySelectedList = new ArrayList<>();
    private List<String> photoList = new ArrayList<>();

    private Retrofit retrofit;

    public static final String TAG = "NWLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        retrofit = RetrofitInstance.getInstace();

        listarClasses();

        listarSpecialties();

        initViews();
    }

    private void initViews() {

        rvHeroiAvatares = findViewById(R.id.rv_heroi_avatar);

        photoList.add("27");

        adapter = new PhotosAdapter( photoList );
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvHeroiAvatares = findViewById(R.id.rv_heroi_avatar);
        rvHeroiAvatares.setAdapter(adapter);
        rvHeroiAvatares.setLayoutManager(mLayoutManager);
        rvHeroiAvatares.setHasFixedSize(false);

        edtHeroiNome = findViewById(R.id.edt_heroi_nome);
        edtHeroiClasses = findViewById(R.id.edt_heroi_classes);
        edtHeroiHabilidades = findViewById(R.id.edt_heroi_habilidades);

        edtHeroiVida = findViewById(R.id.edt_heroi_vida);
        edtHeroiDefesa = findViewById(R.id.edt_heroi_defesa);
        edtHeroiDano = findViewById(R.id.edt_heroi_dano);
        edtHeroiVeloAtaque = findViewById(R.id.edt_heroi_velo_ataque);
        edtHeroiVeloMovimento = findViewById(R.id.edt_heroi_velo_movimento);

        btnSalvarHeroi = findViewById(R.id.btn_salvar_heroi);
        btnSalvarHeroi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Hero hero = new Hero();
                hero.setClassId( classeSelected.getId() );
                hero.setClassName( classeSelected.getName() );
                hero.setName( edtHeroiNome.getText().toString() );
                hero.setHealthPoints( Double.parseDouble(edtHeroiVida.getText().toString()) );
                hero.setDefense( Double.parseDouble(edtHeroiDefesa.getText().toString()) );
                hero.setDamage( Double.parseDouble(edtHeroiDano.getText().toString()) );
                hero.setAttackSpeed( Double.parseDouble(edtHeroiVeloAtaque.getText().toString()) );
                hero.setMovimentSpeed( Double.parseDouble(edtHeroiVeloMovimento.getText().toString()) );

                // Salva as fotos do heroi
                hero.setPhotos( photoList );

                // Salva habilidades
                hero.setSpecialties(specialtySelectedList);

                HeroesService service = retrofit.create(HeroesService.class);
                Call<Void> apiService = service.makeHeroe( hero );
                apiService.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(CadastroActivity.this, "Hero salvo com sucesso!", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(CadastroActivity.this, "Não foi possivel salvar hero, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });
    }

    public void listarClasses(){

        ClassesService service = retrofit.create(ClassesService.class);
        Call<List<Classe>> classeService = service.getClasses();
        classeService.enqueue(new Callback<List<Classe>>() {
            @Override
            public void onResponse(Call<List<Classe>> call, Response<List<Classe>> response) {
                if (response.isSuccessful()){
                    for (Classe classe : response.body()){
                        Log.wtf(TAG, String.format("Nome da Classe: %s", classe.getName()));
                        classeList.add( classe );
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Classe>> call, Throwable t) {

            }
        });
    }

    public void listarSpecialties(){

        SpecialtieService service = retrofit.create(SpecialtieService.class);
        Call<List<Specialty>> classeSpecialtie = service.getSpecialties();
        classeSpecialtie.enqueue(new Callback<List<Specialty>>() {
            @Override
            public void onResponse(Call<List<Specialty>> call, Response<List<Specialty>> response) {
                if (response.isSuccessful()){
                    for (Specialty specialty : response.body()){
                        Log.wtf(TAG, String.format("Nome da Habilidade: %s", specialty.getName()));
                        specialtyList.add(specialty);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Specialty>> call, Throwable t) {

            }
        });
    }

    public void listarPhotos(){

        PhotosService service = retrofit.create(PhotosService.class);
        Call<ResponseBody> photoService = service.getPhoto(27);
        photoService.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void openDialogClasse(View view){

        String[] classesConvertList = new String[ classeList.size() ];

        for (int i =0; i<classeList.size(); i++){
            classesConvertList[ i ] = classeList.get( i ).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione uma classe");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(classesConvertList, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                classeSelected = classeList.get(which);
            }
        });
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtHeroiClasses.setText( classeSelected.getName() );
            }
        });

        AlertDialog dialogClasse = builder.create();
        dialogClasse.show();

    }

    public void openDialogSpecialtie(View view){

        final String[] specialtieConvertList = new String[ specialtyList.size() ];

        for (int i = 0; i< specialtyList.size(); i++){
            specialtieConvertList[ i ] = specialtyList.get( i ).getName();
        }

        specialtieCheckedList = new boolean[ specialtyList.size() ];

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Selecione as habilidades");
        builder1.setCancelable(false);

        builder1.setMultiChoiceItems(specialtieConvertList, specialtieCheckedList, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                specialtieCheckedList[which] = isChecked;
                specialtySelectedList.add(specialtyList.get( which ));
            }
        });
        builder1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringBuilder habilidadesHero = new StringBuilder();
                for(int i=0; i<specialtieCheckedList.length; i++){

                    boolean checked = specialtieCheckedList[i];
                    if (checked) {
                        habilidadesHero.append( specialtieConvertList[i] + ", ");
                    }
                }

                int lastIndice = habilidadesHero.lastIndexOf(",");
                String habilidadesTexto = habilidadesHero.replace(lastIndice, lastIndice+2, "").toString();
                edtHeroiHabilidades.setText( habilidadesTexto );

            }
        });

        AlertDialog dialogClasse = builder1.create();
        dialogClasse.show();

    }

}
