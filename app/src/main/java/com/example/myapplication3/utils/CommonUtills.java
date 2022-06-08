package com.example.myapplication3.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class CommonUtills {
    /**
     * This method sets back arrow button and sets up toolbar
     * @param toolbar Toolbar
     * @param title String
     * @param ctx Activity
     */
    public static void backHomeToolbar(Toolbar toolbar, String title, Context ctx, Boolean cancel) {

        ((AppCompatActivity) ctx).setSupportActionBar(toolbar);
        (((AppCompatActivity) ctx).getSupportActionBar()).setTitle(title);
        (((AppCompatActivity) ctx).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
}
