package ru.ifmo.md.exam2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ru.ifmo.md.exam2.util.StreamUtil;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class TracksParser {
    public static List<Track> parse(InputStream is) throws IOException, JSONException {
        JSONArray root = new JSONArray(StreamUtil.inputStreamToString(is));
        is.close();

        List<Track> result = new ArrayList<>();

        for (int i = 0; i < root.length(); i++) {
            JSONObject trackJSON = root.getJSONObject(i);
            Track track = new Track();
            String authorAndName = trackJSON.getString("name");
            int pos = authorAndName.indexOf('–');
            track.setAuthor(authorAndName.substring(0, pos - 1));
            track.setName(authorAndName.substring(pos + 1));
            track.setYear(trackJSON.getInt("year"));
            JSONArray genresJSON = trackJSON.getJSONArray("genres");
            for (int j = 0; j < genresJSON.length(); j++) {
                track.addGenre(GenresMask.Genre.fromString(genresJSON.getString(j)));
            }
            result.add(track);
        }

        return result;
    }
}
