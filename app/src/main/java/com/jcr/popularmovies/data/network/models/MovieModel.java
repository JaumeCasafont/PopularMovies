package com.jcr.popularmovies.data.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable{

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("video")
	private boolean video;

	@SerializedName("title")
	private String title;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("vote_count")
	private int voteCount;

	private boolean favorite;

	public MovieModel(String overview, String title, String posterPath, String releaseDate, double voteAverage, double popularity, int id, int favorite) {
		this.overview = overview;
		this.title = title;
		this.posterPath = posterPath;
		this.releaseDate = releaseDate;
		this.voteAverage = voteAverage;
		this.popularity = popularity;
		this.id = id;
		this.favorite = favorite == 1;
	}

    protected MovieModel(Parcel in) {
        overview = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        id = in.readInt();
        adult = in.readByte() != 0;
        voteCount = in.readInt();
        favorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeInt(id);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeInt(voteCount);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}