package weslei.com.br.applojavirtual;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import weslei.com.br.applojavirtual.adapter.ViewPagerAdapter;
import weslei.com.br.applojavirtual.async.AsyncUsuario;
import weslei.com.br.applojavirtual.fragment.FragmentPerfil;
import weslei.com.br.applojavirtual.fragment.FragmentProdutos;
import weslei.com.br.applojavirtual.util.Constantes;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Drawer drawer;
    private static final long ID_ND_FOOTER = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        configurarViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        //Adicionando o item Produtos
        final PrimaryDrawerItem itemPerfil = new PrimaryDrawerItem().withIdentifier(2).withName("Perfil")
                .withIcon(GoogleMaterial.Icon.gmd_person);
        final PrimaryDrawerItem itemProdutos = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Produtos")
                .withBadge("43")
                .withIcon(FontAwesome.Icon.faw_th_list)
                .withBadgeStyle(
                        new BadgeStyle()
                                .withTextColor(Color.WHITE)
                                .withColorRes(R.color.md_orange_700));
        final PrimaryDrawerItem itemCompra = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Últimas Compras")
                .withBadge("2")
                .withIcon(GoogleMaterial.Icon.gmd_shop_two)
                .withBadgeStyle(
                        new BadgeStyle()
                                .withTextColor(Color.WHITE)
                                .withColorRes(R.color.md_orange_700));




        //Criando o Header
        AccountHeader drawerHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Weslei Ferreira").withEmail("weslei@gmail.com").
                                withIcon(R.drawable.perfil)
                )
                .build();

        //Criando a parte que vai comportar toda a informação do drawer
       drawer = new DrawerBuilder()
               .withAccountHeader(drawerHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Conta do Usuário"),
                        itemPerfil,
                        new SectionDrawerItem().withName("Ações do Sistema"),
                        itemProdutos,
                        itemCompra
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        configuraItensDrawer(position, drawerItem);
                        return true;
                    }
                })
                .build();
        //Adicionando o Footer
        drawer.addStickyFooterItem(new PrimaryDrawerItem().withName("Sobre o App")
                .withIcon(GoogleMaterial.Icon.gmd_info)
                .withIdentifier(ID_ND_FOOTER));


    }

    private void configuraItensDrawer(int position, IDrawerItem drawerItem) {
        viewPager.setCurrentItem(position-1);

        switch ((int)drawerItem.getIdentifier()){
            case((int)ID_ND_FOOTER):
                try {
                    PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                    String versao = info.versionName.toString();
                    Toast.makeText(MainActivity.this, "Versão: " + versao, Toast.LENGTH_LONG).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


                break;
        }
        drawer.closeDrawer();
    }

    private void configurarViewPager(ViewPager viewPager){

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentProdutos(), "Produtos");
        viewPagerAdapter.addFragment(new FragmentPerfil(), "Perfil");

        viewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

//Substituto do toast(Bem melhor :D)
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

