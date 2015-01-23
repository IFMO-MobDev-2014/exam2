package me.volhovm.android.exam2

import android.app.Activity
import android.app.LoaderManager.LoaderCallbacks
import android.content.{AsyncTaskLoader, Intent, Loader}
import android.os.Bundle
import android.util.Log
import android.view.{Menu, MenuItem, View}
import android.widget.{AdapterView, ArrayAdapter, ListView, Toast}

class MainActivity extends Activity with LoaderCallbacks[List[PlayList]] {
  private var mPlaylists: List[PlayList] = null
  private var mDatabaseHelper: DatabaseHelper = null
  private var mListView: ListView = null
  private var mListViewAdapter: ArrayAdapter[String] = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    Log.d(this.toString, "In main activity")
    mDatabaseHelper = new DatabaseHelper(this)
    mListView = new ListView(this)
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      override def onItemClick(p1: AdapterView[_], p2: View, pos: Int, p4: Long): Unit = {
        val intent = new Intent(MainActivity.this, classOf[PlaylistActivity])
        intent.putExtra("playlist_name", mPlaylists(pos).name)
        startActivity(intent)
      }
    })
    setContentView(mListView)
    getLoaderManager.initLoader(0, null, this).forceLoad()
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.menu_main, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem) = item.getItemId match {
    case R.id.action_settings => true
    case R.id.action_refresh =>
      Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show()
      super.onOptionsItemSelected(item)
      true
  }

  override def onCreateLoader(p1: Int, p2: Bundle): Loader[List[PlayList]] =
    new AsyncTaskLoader[List[PlayList]](this) {
      override def loadInBackground(): List[PlayList] = mDatabaseHelper.mWrapper.getAllPlayLists
    }
  override def onLoaderReset(p1: Loader[List[PlayList]]): Unit = ()
  override def onLoadFinished(p1: Loader[List[PlayList]], p2: List[PlayList]): Unit = {
    if (p2.isEmpty) {
      Thread.sleep(2000)
      getLoaderManager.restartLoader(0, null, this).forceLoad()
    }
    mPlaylists = p2
    mListViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mPlaylists.map(_.name).toArray)
    mListView.setAdapter(mListViewAdapter)
    mListViewAdapter.notifyDataSetChanged()
  }
}