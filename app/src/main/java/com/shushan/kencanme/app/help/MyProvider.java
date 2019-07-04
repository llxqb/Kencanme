package com.shushan.kencanme.app.help;

import android.support.v4.content.FileProvider;

/**
 * Provider类与build中authorities冲突
 * 让主app继承FileProvider
 */
public class MyProvider extends FileProvider {
}
