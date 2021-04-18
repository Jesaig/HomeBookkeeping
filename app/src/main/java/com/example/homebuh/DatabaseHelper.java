package com.example.homebuh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.homebuh.entity.Account;
import com.example.homebuh.entity.Measure;
import com.example.homebuh.entity.Operation;
import com.example.homebuh.entity.OperationCategory;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    // name of the database file for your application -- change to something appropriate for your app
    public static final String DATABASE_NAME = "homeBuh.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<OperationCategory, Integer> categoryDao = null;
    private Dao<Operation, Integer> operationDao = null;
    private Dao<Measure, Integer> measureDao = null;
    private Dao<Account, Integer> accountDao = null;

    public DatabaseHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, OperationCategory.class);
            TableUtils.createTable(connectionSource, Operation.class);
            TableUtils.createTable(connectionSource, Measure.class);
            TableUtils.createTable(connectionSource, Account.class);

            insertMeasures();
            insertAccounts();
            insertCategories();
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, OperationCategory.class, true);
            TableUtils.dropTable(connectionSource, Operation.class, true);
            TableUtils.dropTable(connectionSource, Measure.class, true);
            TableUtils.dropTable(connectionSource, Account.class, true);

            onCreate(database, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVersion);
            throw new RuntimeException(e);
        }
    }

    private void insertAccounts() {
        try {
            getAccountDao().create(new Account("Кошелёк", 1570.20));
            getAccountDao().create(new Account("Зарплатная карта", 37840.85));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertCategories() {
        try {
            getCategoryDao().create(new OperationCategory("Продукты питания"));
            getCategoryDao().create(new OperationCategory("Коммунальные платежи"));
            getCategoryDao().create(new OperationCategory("Автомобиль"));
            getCategoryDao().create(new OperationCategory("Медицина"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertMeasures() {
        try {
            getMeasureDao().create(new Measure("шт.", "штуки"));
            getMeasureDao().create(new Measure("уп.", "упаковки"));
            getMeasureDao().create(new Measure("л.", "литры"));
            getMeasureDao().create(new Measure("кг.", "килограммы"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<OperationCategory, Integer> getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(OperationCategory.class);
        }
        return categoryDao;
    }

    public Dao<Operation, Integer> getOperationDao() throws SQLException {
        if (operationDao == null) {
            operationDao = getDao(Operation.class);
        }
        return operationDao;
    }

    public Dao<Measure, Integer> getMeasureDao() throws SQLException {
        if (measureDao == null) {
            measureDao = getDao(Measure.class);
        }
        return measureDao;
    }

    public Dao<Account, Integer> getAccountDao() throws SQLException {
        if (accountDao == null) {
            accountDao = getDao(Account.class);
        }
        return accountDao;
    }

    @Override
    public void close() {
        super.close();
        categoryDao = null;
    }
}