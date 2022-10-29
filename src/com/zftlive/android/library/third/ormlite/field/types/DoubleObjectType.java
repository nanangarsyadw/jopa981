package com.zftlive.android.library.third.ormlite.field.types;

import java.sql.SQLException;

import com.zftlive.android.library.third.ormlite.field.FieldType;
import com.zftlive.android.library.third.ormlite.field.SqlType;
import com.zftlive.android.library.third.ormlite.support.DatabaseResults;

/**
 * Type that persists a Double object.
 * 
 * @author graywatson
 */
public class DoubleObjectType extends BaseDataType {

	private static final DoubleObjectType singleTon = new DoubleObjectType();

	public static DoubleObjectType getSingleton() {
		return singleTon;
	}

	private DoubleObjectType() {
		super(SqlType.DOUBLE, new Class<?>[] { Double.class });
	}

	protected DoubleObjectType(SqlType sqlType, Class<?>[] classes) {
		super(sqlType, classes);
	}

	@Override
	public Object parseDefaultString(FieldType fieldType, String defaultStr) {
		return Double.parseDouble(defaultStr);
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
		return (Double) results.getDouble(columnPos);
	}

	@Override
	public boolean isEscapedValue() {
		return false;
	}
}
