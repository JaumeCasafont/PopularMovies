package com.jcr.popularmovies.data.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VideoModel implements Parcelable {

	@SerializedName("site")
	private String site;

	@SerializedName("size")
	private int size;

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	@SerializedName("iso_639_1")
	private String iso6391;

	@SerializedName("key")
	private String key;

	protected VideoModel(Parcel in) {
		site = in.readString();
		size = in.readInt();
		iso31661 = in.readString();
		name = in.readString();
		id = in.readString();
		type = in.readString();
		iso6391 = in.readString();
		key = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(site);
		dest.writeInt(size);
		dest.writeString(iso31661);
		dest.writeString(name);
		dest.writeString(id);
		dest.writeString(type);
		dest.writeString(iso6391);
		dest.writeString(key);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
		@Override
		public VideoModel createFromParcel(Parcel in) {
			return new VideoModel(in);
		}

		@Override
		public VideoModel[] newArray(int size) {
			return new VideoModel[size];
		}
	};

	public void setSite(String site){
		this.site = site;
	}

	public String getSite(){
		return site;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setIso31661(String iso31661){
		this.iso31661 = iso31661;
	}

	public String getIso31661(){
		return iso31661;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setIso6391(String iso6391){
		this.iso6391 = iso6391;
	}

	public String getIso6391(){
		return iso6391;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	@Override
 	public String toString(){
		return 
			"VideoModel{" +
			"site = '" + site + '\'' + 
			",size = '" + size + '\'' + 
			",iso_3166_1 = '" + iso31661 + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",iso_639_1 = '" + iso6391 + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}
}