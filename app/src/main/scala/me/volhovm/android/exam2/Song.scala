package me.volhovm.android.exam2

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


object Song {
  def fromCursor(cursor: Cursor): Song =
    new Song(
      cursor.getString(1),
      cursor.getString(2),
      cursor.getString(3),
      cursor.getInt(4),
      cursor.getInt(5),
      cursor.getString(6).split(":").toList,
      cursor.getInt(7),
      cursor.getInt(0))
}

class Song(val name: String,
            val artist: String,
            val url: String,
            val duration: Int,
            val popularity: Int,
            val genres: List[String],
            val year: Int,
            val id: Int
            ) {
  def getName = name
  def getId = id
  def getArtist = artist
  def getValues = {
    import DatabaseHelper._
    val values = new ContentValues
    values.put(SONG_NAME, name)
    values.put(SONG_ARTIST, artist)
    values.put(SONG_URL, url)
    values.put(SONG_DURATION, Int.box(duration))
    values.put(SONG_POPULARITY, Int.box(popularity))
    values.put(SONG_GENRES, genres.mkString(":"))
    values.put(SONG_YEAR, Int.box(year))
    values.put(BaseColumns._ID, Int.box(id))
    values
  }
}

object PlayList {
  def fromCursor(cursor: Cursor, allSongs: List[Song]) =
    new PlayList(
      cursor.getString(1),
      cursor.getString(2).split(":")
        .map(Integer.parseInt).toList
        .map((i: Int) => allSongs.find((s: Song) => s.getId == i) match {
        case None => new Song("NONAME", "ERROR", "", -1, -1, Nil, -1, i)
        case Some(song) => song
      }).toList)

  val mainPlayList = "main_playlist"
}

class PlayList(val name: String, val songs: List[Song]) {
  def getValues = {
    import DatabaseHelper._
    val values = new ContentValues
    values.put(PLAYLISTS_NAME, name)
    values.put(PLAYLISTS_TABLE_NAME, songs.map(_.getId).mkString(":"))
    values
  }
}

