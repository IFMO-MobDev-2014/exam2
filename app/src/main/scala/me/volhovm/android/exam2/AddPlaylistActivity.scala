package me.volhovm.android.exam2

import android.app.Activity
import android.os.Bundle
import android.view.{Menu, MenuItem}
import android.widget.Toast

class AddPlaylistActivity extends Activity {
  val mDatabaseHelper: DatabaseHelper = null
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.menu_auxiliary, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem) = item.getItemId match {
    case R.id.action_settings => ???
    case R.id.action_refresh =>
      Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show()
      super.onOptionsItemSelected(item)
  }
}