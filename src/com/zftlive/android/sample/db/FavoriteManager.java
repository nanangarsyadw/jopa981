package com.zftlive.android.sample.db;

import java.sql.Savepoint;
import java.util.List;

import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.zftlive.android.sample.IContant;
import com.zftlive.android.tools.db.DatabaseHelper;

/**
 * 信息搜藏业务控制
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class FavoriteManager {

	private DatabaseHelper dbHelper;
	private String TAG = "FavoriteManager";
	
	/**
	 * 初始化收藏业务控制
	 * @param mContext
	 */
	public FavoriteManager(Context mContext) {
		dbHelper = DatabaseHelper.gainInstance(mContext, IContant.DB_NAME, IContant.DB_VERSION);
	}
	
	public int insert(Favorite model) {
		
		//返回受影响行数
		int rows = 0;
		
		String savePointName = "FavoriteInsert";
		Savepoint savePoint = null;
		DatabaseConnection connection = null;
		try {
			//获取数据库连接
		    connection = dbHelper.getConnection();
			connection.setAutoCommit(false);
			savePoint = connection.setSavePoint(savePointName);
			
			//插入数据
			Dao<Favorite, String> dao = dbHelper.getDao(Favorite.class);
			rows = dao.create(model);
			
			//提交事务
			connection.commit(savePoint);
			
		} catch (Exception e) {
			//回滚事务
			dbHelper.rollback(connection,savePoint);
			
			Log.e(TAG, "插入数据失败，原因："+e.getMessage());
		}finally{
			//释放数据库连接
			dbHelper.closeConnection(connection);
		}
		
		return rows;
	}

	public void insertBatch() {

	}

	public void delete(Favorite model) {

	}

	public void update(Favorite model) {

	}

	public Favorite findById(String strPK) {
		return null;
	}

	public List<Favorite> findByCondtion() {
		return null;
	}
	
	public List<Favorite> findAllPagenation(final long offset, final long limit){
		return null;
	}
}
