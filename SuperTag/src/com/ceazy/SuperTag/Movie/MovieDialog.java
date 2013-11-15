package com.ceazy.SuperTag.Movie;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.ceazy.SuperTag.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieDialog extends DialogFragment {
	
	public static MovieDialog newInstance(Movie movie) {
		Bundle arguments = new Bundle();
		arguments.putParcelable("movie", movie);
		MovieDialog dialog = new MovieDialog();
		dialog.setArguments(arguments);
		return dialog;
	}
	
	Movie movie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		movie = getArguments().getParcelable("movie");
		int theme = 0;
		if(android.os.Build.VERSION.SDK_INT >= 11) {
			theme = android.R.style.Theme_Holo_Light_Dialog;
		} else {
			theme = android.R.style.Theme_Dialog;
		}
		setStyle(theme, DialogFragment.STYLE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View vTitle = getActivity().getLayoutInflater().inflate(R.layout.movie_title_layout, null);
		TextView tvMovieTitle = (TextView) vTitle.findViewById(R.id.tvMovieTitle);
		ImageView ivMoviePoster = (ImageView) vTitle.findViewById(R.id.ivMoviePoster);
		RatingBar rbMovieRating = (RatingBar) vTitle.findViewById(R.id.rbMovieRating);
		rbMovieRating.setRating(movie.getCriticRating()/20);
		tvMovieTitle.setText(movie.getTitle() + " (" + movie.getYear() + ")");
		return new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), DialogFragment.STYLE_NO_TITLE))
		.setCustomTitle(vTitle)
		.create();
	}
	
	private Drawable getPoster(String poster_url) {
		getDialog();
		URL url;
		try {
			url = new URL(poster_url);
			Bitmap bMoviePoster = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			bMoviePoster = Bitmap.createScaledBitmap(bMoviePoster, 64, 64, true);
			Drawable bdPoster = new BitmapDrawable(getActivity().getResources(), bMoviePoster);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private ListView createMovieListView() {
		ListView listView = new ListView(getActivity());
		return null;
	}
}
