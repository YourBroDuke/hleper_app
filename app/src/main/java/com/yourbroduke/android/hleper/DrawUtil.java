package com.yourbroduke.android.hleper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class DrawUtil {
    public static Drawer result;
    public static AccountHeader headerResult;
    public static Activity mActivity;

    public static void getDrawer(final Activity activity, Toolbar toolbar, Bundle outState) {

        mActivity = activity;

        final IProfile profile = new ProfileDrawerItem()
                .withName("Sign in/Sign up")
                .withEmail("3160100000@zju.edu.cn")
                .withIcon(R.drawable.family_older_brother);


        headerResult = new AccountHeaderBuilder()
                .withDividerBelowHeader(false)
                .withTextColor(activity.getResources().getColor(R.color.md_black_1000))
                .withHeightDp(160)
                .withActivity(activity)
                .withHeaderBackground(R.color.primary_dark)
                .withCompactStyle(true)
                .addProfiles(profile)
                .build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        Drawable accountDrawable = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_account_circle)
                .color(Color.BLACK)
                .sizeDp(24);
        Drawable walletDrawable = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_account_balance_wallet)
                .color(Color.BLACK)
                .sizeDp(24);

        PrimaryDrawerItem drawerItemManagePlayers = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.manage_player).withIcon(accountDrawable);
        PrimaryDrawerItem drawerItemManagePlayersTournaments = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.wallet).withIcon(walletDrawable);


        Drawable settingDrawable = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_settings)
                .color(Color.DKGRAY)
                .sizeDp(24);
        Drawable aboutDrawable = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_attachment)
                .color(Color.DKGRAY)
                .sizeDp(24);
        Drawable helpDrawable = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_help_outline)
                .color(Color.DKGRAY)
                .sizeDp(24);
        Drawable donateDrawable = new IconicsDrawable(activity)
                .icon(GoogleMaterial.Icon.gmd_attach_money)
                .color(Color.GRAY)
                .sizeDp(24);

        SecondaryDrawerItem drawerItemSettings = new SecondaryDrawerItem().withIdentifier(3)
                .withName(R.string.settings).withIcon(settingDrawable);
        SecondaryDrawerItem drawerItemAbout = new SecondaryDrawerItem().withIdentifier(4)
                .withName(R.string.about).withIcon(aboutDrawable);
        SecondaryDrawerItem drawerItemHelp = new SecondaryDrawerItem().withIdentifier(5)
                .withName(R.string.help).withIcon(helpDrawable);
        SecondaryDrawerItem drawerItemDonate = new SecondaryDrawerItem().withIdentifier(6)
                .withName(R.string.donate).withIcon(donateDrawable);


        //create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        drawerItemManagePlayers,
                        drawerItemManagePlayersTournaments,
                        new DividerDrawerItem(),
                        drawerItemSettings,
                        drawerItemAbout,
                        drawerItemHelp,
                        drawerItemDonate
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 2 && !(activity instanceof MainActivity)) {
                            // load tournament screen
                            Intent intent = new Intent(activity, MainActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();
    }
}
