package com.zftlive.android.library.third.ormlite.stmt;

import java.sql.SQLException;

import com.zftlive.android.library.third.ormlite.dao.Dao;
import com.zftlive.android.library.third.ormlite.dao.RawRowMapper;
import com.zftlive.android.library.third.ormlite.field.FieldType;
import com.zftlive.android.library.third.ormlite.table.TableInfo;

/**
 * Default row mapper when you are using the {@link Dao#queryRaw(String, RawRowMapper, String...)}.
 * 
 * @author graywatson
 */
public class RawRowMapperImpl<T, ID> implements RawRowMapper<T> {

	private final TableInfo<T, ID> tableInfo;

	public RawRowMapperImpl(TableInfo<T, ID> tableInfo) {
		this.tableInfo = tableInfo;
	}

	public T mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
		// create our object
		T rowObj = tableInfo.createObject();
		for (int i = 0; i < columnNames.length; i++) {
			// sanity check, prolly will never happen but let's be careful out there
			if (i >= resultColumns.length) {
				continue;
			}
			// run through and convert each field
			FieldType fieldType = tableInfo.getFieldTypeByColumnName(columnNames[i]);
			Object fieldObj = fieldType.convertStringToJavaField(resultColumns[i], i);
			// assign it to the row object
			fieldType.assignField(rowObj, fieldObj, false, null);
		}
		return rowObj;
	}
}
