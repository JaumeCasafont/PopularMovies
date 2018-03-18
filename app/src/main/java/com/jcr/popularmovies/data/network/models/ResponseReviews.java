package com.jcr.popularmovies.data.network.models;

import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

public class ResponseReviews{

	@SerializedName("id")
	private int id;

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private ReviewModel[] results;

	@SerializedName("total_results")
	private int totalResults;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(ReviewModel[] results){
		this.results = results;
	}

	public ReviewModel[] getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
	public String toString() {
		return "ResponseReviews{" +
				"id=" + id +
				", page=" + page +
				", totalPages=" + totalPages +
				", results=" + Arrays.toString(results) +
				", totalResults=" + totalResults +
				'}';
	}
}