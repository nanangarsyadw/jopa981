package com.zftlive.android.library.third.ormlite.field.types;

import java.lang.reflect.Field;

import com.zftlive.android.library.third.ormlite.field.FieldType;
import com.zftlive.android.library.third.ormlite.support.DatabaseResults;

/**
 * Marker class used to see if we have a customer persister defined.
 * 
 * @author graywatson
 */
public class VoidType extends BaseDataType {

	VoidType() {
		super(null, new Class<?>[] {});
	}

	@Override
	public Object parseDefaultString(FieldType fieldType, String defaultStr) {
		return null;
	}

	@Override
	public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) {
		return null;
	}

	@Override
	public boolean isValidForField(Field field) {
		return false;
	}
}
