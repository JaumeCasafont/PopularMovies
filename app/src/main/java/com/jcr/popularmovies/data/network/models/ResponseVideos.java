package com.jcr.popularmovies.data.network.models;

import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

public class ResponseVideos{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private VideoModel[] results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(VideoModel[] results){
		this.results = results;
	}

	public VideoModel[] getResults(){
		return results;
	}

	@Override
	public String toString() {
		return "ResponseVideos{" +
				"id=" + id +
				", results=" + Arrays.toString(results) +
				'}';
	}
}