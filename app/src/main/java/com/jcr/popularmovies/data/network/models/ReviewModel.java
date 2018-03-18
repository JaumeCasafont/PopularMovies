package com.jcr.popularmovies.data.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReviewModel implements Parcelable{

	@SerializedName("author")
	private String author;

	@SerializedName("id")
	private String id;

	@SerializedName("content")
	private String content;

	@SerializedName("url")
	private String url;

	protected ReviewModel(Parcel in) {
		author = in.readString();
		id = in.readString();
		content = in.readString();
		url = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(author);
		dest.writeString(id);
		dest.writeString(content);
		dest.writeString(url);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
		@Override
		public ReviewModel createFromParcel(Parcel in) {
			return new ReviewModel(in);
		}

		@Override
		public ReviewModel[] newArray(int size) {
			return new ReviewModel[size];
		}
	};

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ReviewModel{" +
			"author = '" + author + '\'' + 
			",id = '" + id + '\'' + 
			",content = '" + content + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}