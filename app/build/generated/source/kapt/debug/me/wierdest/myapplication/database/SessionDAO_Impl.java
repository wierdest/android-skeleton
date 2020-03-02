package me.wierdest.myapplication.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SessionDAO_Impl implements SessionDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Session> __insertionAdapterOfSession;

  private final EntityDeletionOrUpdateAdapter<Session> __updateAdapterOfSession;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  private final SharedSQLiteStatement __preparedStmtOfClearSession;

  public SessionDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSession = new EntityInsertionAdapter<Session>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `session_table` (`sessionId`,`creationTimeMilli`,`lastAccessTimeMilli`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Session value) {
        stmt.bindLong(1, value.getSessionId());
        stmt.bindLong(2, value.getCreationTimeMilli());
        stmt.bindLong(3, value.getLastAccessTimeMilli());
      }
    };
    this.__updateAdapterOfSession = new EntityDeletionOrUpdateAdapter<Session>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `session_table` SET `sessionId` = ?,`creationTimeMilli` = ?,`lastAccessTimeMilli` = ? WHERE `sessionId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Session value) {
        stmt.bindLong(1, value.getSessionId());
        stmt.bindLong(2, value.getCreationTimeMilli());
        stmt.bindLong(3, value.getLastAccessTimeMilli());
        stmt.bindLong(4, value.getSessionId());
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM session_table";
        return _query;
      }
    };
    this.__preparedStmtOfClearSession = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM session_table WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Session item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSession.insert(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Session item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSession.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clear() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClear.release(_stmt);
    }
  }

  @Override
  public void clearSession(final long key) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearSession.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, key);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearSession.release(_stmt);
    }
  }

  @Override
  public Session getById(final long key) {
    final String _sql = "SELECT * FROM session_table WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, key);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
      final int _cursorIndexOfCreationTimeMilli = CursorUtil.getColumnIndexOrThrow(_cursor, "creationTimeMilli");
      final int _cursorIndexOfLastAccessTimeMilli = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessTimeMilli");
      final Session _result;
      if(_cursor.moveToFirst()) {
        final long _tmpSessionId;
        _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
        final long _tmpCreationTimeMilli;
        _tmpCreationTimeMilli = _cursor.getLong(_cursorIndexOfCreationTimeMilli);
        final long _tmpLastAccessTimeMilli;
        _tmpLastAccessTimeMilli = _cursor.getLong(_cursorIndexOfLastAccessTimeMilli);
        _result = new Session(_tmpSessionId,_tmpCreationTimeMilli,_tmpLastAccessTimeMilli);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Session getByCreationTimeMilli(final long key) {
    final String _sql = "SELECT * FROM session_table WHERE creationTimeMilli = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, key);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
      final int _cursorIndexOfCreationTimeMilli = CursorUtil.getColumnIndexOrThrow(_cursor, "creationTimeMilli");
      final int _cursorIndexOfLastAccessTimeMilli = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessTimeMilli");
      final Session _result;
      if(_cursor.moveToFirst()) {
        final long _tmpSessionId;
        _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
        final long _tmpCreationTimeMilli;
        _tmpCreationTimeMilli = _cursor.getLong(_cursorIndexOfCreationTimeMilli);
        final long _tmpLastAccessTimeMilli;
        _tmpLastAccessTimeMilli = _cursor.getLong(_cursorIndexOfLastAccessTimeMilli);
        _result = new Session(_tmpSessionId,_tmpCreationTimeMilli,_tmpLastAccessTimeMilli);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Session> getLastSession() {
    final String _sql = "SELECT * FROM session_table ORDER BY sessionId DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"session_table"}, false, new Callable<Session>() {
      @Override
      public Session call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfCreationTimeMilli = CursorUtil.getColumnIndexOrThrow(_cursor, "creationTimeMilli");
          final int _cursorIndexOfLastAccessTimeMilli = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessTimeMilli");
          final Session _result;
          if(_cursor.moveToFirst()) {
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final long _tmpCreationTimeMilli;
            _tmpCreationTimeMilli = _cursor.getLong(_cursorIndexOfCreationTimeMilli);
            final long _tmpLastAccessTimeMilli;
            _tmpLastAccessTimeMilli = _cursor.getLong(_cursorIndexOfLastAccessTimeMilli);
            _result = new Session(_tmpSessionId,_tmpCreationTimeMilli,_tmpLastAccessTimeMilli);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
