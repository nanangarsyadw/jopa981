package com.zftlive.android.library.third.ormlite.stmt.query;

import java.sql.SQLException;
import java.util.List;

import com.zftlive.android.library.third.ormlite.db.DatabaseType;
import com.zftlive.android.library.third.ormlite.field.FieldType;
import com.zftlive.android.library.third.ormlite.stmt.ArgumentHolder;
import com.zftlive.android.library.third.ormlite.stmt.Where;

/**
 * Internal class handling the SQL 'IS NULL' comparison query part. Used by {@link Where#isNull}.
 * 
 * @author graywatson
 */
public class IsNull extends BaseComparison {

	public IsNull(String columnName, FieldType fieldType) throws SQLException {
		super(columnName, fieldType, null, true);
	}

	@Override
	public void appendOperation(StringBuilder sb) {
		sb.append("IS NULL ");
	}

	@Override
	public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> argList) {
		// there is no value
	}
}
