package com.zftlive.android.library.third.ormlite.stmt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zftlive.android.library.third.ormlite.dao.CloseableIterator;
import com.zftlive.android.library.third.ormlite.dao.GenericRawResults;
import com.zftlive.android.library.third.ormlite.dao.ObjectCache;
import com.zftlive.android.library.third.ormlite.support.CompiledStatement;
import com.zftlive.android.library.third.ormlite.support.ConnectionSource;
import com.zftlive.android.library.third.ormlite.support.DatabaseConnection;

/**
 * Handler for our raw results objects which does the conversion for various different results: String[], Object[], and
 * user defined <T>.
 * 
 * @author graywatson
 */
public class RawResultsImpl<T> implements GenericRawResults<T> {

	private SelectIterator<T, Void> iterator;
	private final String[] columnNames;

	public RawResultsImpl(ConnectionSource connectionSource, DatabaseConnection connection, String query,
			Class<?> clazz, CompiledStatement compiledStmt, GenericRowMapper<T> rowMapper, ObjectCache objectCache)
			throws SQLException {
		iterator =
				new SelectIterator<T, Void>(clazz, null, rowMapper, connectionSource, connection, compiledStmt, query,
						objectCache);
		/*
		 * NOTE: we _have_ to get these here before the results object is closed if there are no results
		 */
		this.columnNames = iterator.getRawResults().getColumnNames();
	}

	public int getNumberColumns() {
		return columnNames.length;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public List<T> getResults() throws SQLException {
		List<T> results = new ArrayList<T>();
		try {
			while (iterator.hasNext()) {
				results.add(iterator.next());
			}
			return results;
		} finally {
			iterator.close();
		}
	}

	public T getFirstResult() throws SQLException {
		try {
			if (iterator.hasNextThrow()) {
				return iterator.nextThrow();
			} else {
				return null;
			}
		} finally {
			close();
		}
	}

	public CloseableIterator<T> iterator() {
		return iterator;
	}

	public CloseableIterator<T> closeableIterator() {
		return iterator;
	}

	public void close() throws SQLException {
		if (iterator != null) {
			iterator.close();
			iterator = null;
		}
	}
}
