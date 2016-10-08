package com.myownmotivator.model.gaming;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "playingturns")
public class PlayingTurns {

	private Integer playerOne;
	
	private Integer playerTwo;
	
	private Integer selectedTurn;
	
	private Integer bundleId;
	
	private Integer id;

	public Integer getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Integer playerOne) {
		this.playerOne = playerOne;
	}

	public Integer getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Integer playerTwo) {
		this.playerTwo = playerTwo;
	}

	public Integer getSelectedTurn() {
		return selectedTurn;
	}

	public void setSelectedTurn(Integer selectedTurn) {
		this.selectedTurn = selectedTurn;
	}

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}

	@Id
	@GeneratedValue
	@Column(name = "TURN_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
