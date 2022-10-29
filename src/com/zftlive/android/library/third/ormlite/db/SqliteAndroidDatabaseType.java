package com.zftlive.android.library.third.ormlite.db;

import java.sql.SQLException;

import com.zftlive.android.library.third.ormlite.android.DatabaseTableConfigUtil;
import com.zftlive.android.library.third.ormlite.field.DataPersister;
import com.zftlive.android.library.third.ormlite.field.FieldConverter;
import com.zftlive.android.library.third.ormlite.field.FieldType;
import com.zftlive.android.library.third.ormlite.field.types.DateStringType;
import com.zftlive.android.library.third.ormlite.support.ConnectionSource;
import com.zftlive.android.library.third.ormlite.table.DatabaseTableConfig;

/**
 * Sqlite database type information for the Android OS that makes native calls to the Android OS database APIs.
 * 
 * @author graywatson
 */
public class SqliteAndroidDatabaseType extends BaseSqliteDatabaseType {

	@Override
	public void loadDriver() {
		// noop
	}

	public boolean isDatabaseUrlThisType(String url, String dbTypePart) {
		// not used by the android code
		return true;
	}

	@Override
	protected String getDriverClassName() {
		// no driver to load in android-land
		return null;
	}

	public String getDatabaseName() {
		return "Android SQLite";
	}

	@Override
	protected void appendDateType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
		// default is to store the date as a string
		appendStringType(sb, fieldType, fieldWidth);
	}

	@Override
	protected void appendBooleanType(StringBuilder sb, FieldType fieldType, int fieldWidth) {
		// we have to convert booleans to numbers
		appendShortType(sb, fieldType, fieldWidth);
	}

	@Override
	public FieldConverter getFieldConverter(DataPersister dataPersister) {
		// we are only overriding certain types
		switch (dataPersister.getSqlType()) {
			case DATE :
				return DateStringType.getSingleton();
			default :
				return super.getFieldConverter(dataPersister);
		}
	}

	@Override
	public boolean isNestedSavePointsSupported() {
		return false;
	}

	@Override
	public boolean isBatchUseTransaction() {
		return true;
	}

	@Override
	public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> clazz)
			throws SQLException {
		return DatabaseTableConfigUtil.fromClass(connectionSource, clazz);
	}
}
