package com.nanobookv2.data.db;

import com.nanobookv2.di.qualifier.DatabaseInfo;

import javax.inject.Inject;

public class DataBaseHelperImp implements DataBaseHelper {
    @Inject
    public DataBaseHelperImp(@DatabaseInfo String name, @DatabaseInfo Integer version) {

    }
}
