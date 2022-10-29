package com.zftlive.android.library.third.ormlite.stmt.query;

import java.sql.SQLException;
import java.util.List;

import com.zftlive.android.library.third.ormlite.db.DatabaseType;
import com.zftlive.android.library.third.ormlite.stmt.ArgumentHolder;
import com.zftlive.android.library.third.ormlite.stmt.Where;
import com.zftlive.android.library.third.ormlite.stmt.QueryBuilder.InternalQueryBuilderWrapper;

/**
 * Internal class handling the SQL 'EXISTS' query part. Used by {@link Where#exists}.
 * 
 * @author graywatson
 */
public class Exists implements Clause {

	private final InternalQueryBuilderWrapper subQueryBuilder;

	public Exists(InternalQueryBuilderWrapper subQueryBuilder) {
		this.subQueryBuilder = subQueryBuilder;
	}

	public void appendSql(DatabaseType databaseType, String tableName, StringBuilder sb, List<ArgumentHolder> argList)
			throws SQLException {
		sb.append("EXISTS (");
		subQueryBuilder.appendStatementString(sb, argList);
		sb.append(") ");
	}
}
