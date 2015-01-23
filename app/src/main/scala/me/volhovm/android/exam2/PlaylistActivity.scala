package me.volhovm.android.exam2

import android.app.Activity
import android.app.LoaderManager.LoaderCallbacks
import android.content.{AsyncTaskLoader, Loader}
import android.os.Bundle
import android.view.{Menu, MenuItem}
import android.widget.{ArrayAdapter, ListView, Toast}

class PlaylistActivity extends Activity with LoaderCallbacks[PlayList] {
  private var mPlaylistName: String = null
  private var mPlaylist: PlayList = null
  private var mDatabaseHelper: DatabaseHelper = null
  private var mListView: ListView = null
  private var mListViewAdapter: ArrayAdapter[String] = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    mPlaylistName = getIntent.getStringExtra("playlist_name")
    mDatabaseHelper = new DatabaseHelper(this)
    mListView = new ListView(this)
    setContentView(mListView)
    getLoaderManager.initLoader(0, null, this).forceLoad()
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.menu_auxiliary, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem) = item.getItemId match {
    case R.id.action_settings => true
    case R.id.action_refresh =>
      Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show()
      super.onOptionsItemSelected(item)
      true
  }
  override def onCreateLoader(p1: Int, p2: Bundle): Loader[PlayList] =
    new AsyncTaskLoader[PlayList](this) {
      override def loadInBackground(): PlayList = mDatabaseHelper.mWrapper.getPlaylist(mPlaylistName)
    }
  override def onLoaderReset(p1: Loader[PlayList]): Unit = ()
  override def onLoadFinished(p1: Loader[PlayList], p2: PlayList): Unit = {
    mPlaylist = p2
    mListViewAdapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1,
      mPlaylist.songs.map((s: Song) => s.getArtist + " - " + s.getName).toArray)
    mListView.setAdapter(mListViewAdapter)
    mListViewAdapter.notifyDataSetChanged()
  }
}