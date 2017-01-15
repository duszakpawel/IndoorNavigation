package com.wut.indoornavigation.view.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.sdk.SystemRequirementsChecker;
import com.wut.indoornavigation.IndoorNavigationApp;
import com.wut.indoornavigation.R;
import com.wut.indoornavigation.view.base.BaseActivity;
import com.wut.indoornavigation.view.map.fragment.MapFragment;
import com.wut.indoornavigation.view.map.legend.MapLegendDialogFragment;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Activity which contains view with map
 */
public class MapActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindColor(R.color.white)
    int colorWhite;

    /**
     * Starts {@link MapActivity} instance
     * @param context current context
     */
    public static void startActivity(Context context) {
        final Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initializeToolbar();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        if (savedInstanceState == null) {
            replaceFragment(R.id.activity_map_container, MapFragment.newInstance(),
                    MapFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_legend:
                MapLegendDialogFragment.newInstance().show(getSupportFragmentManager(),
                        MapLegendDialogFragment.TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void injectDependencies() {
        IndoorNavigationApp.getDependencies(this).getMapActivityComponent().inject(this);
    }

    @Override
    protected void resetDependencies() {
        IndoorNavigationApp.getDependencies(this).releaseMapActivityComponent();
    }

    private void initializeToolbar() {
        toolbar.setTitleTextColor(colorWhite);
        setSupportActionBar(toolbar);
    }
}
