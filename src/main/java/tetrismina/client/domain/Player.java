package tetrismina.client.domain;

public class Player implements Comparable<Player> {
	private String nickname;

	private String status;

	public Player(String nickname, String status) {
		super();
		this.nickname = nickname;
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getStatus() {
		return status;
	}

	public int compareTo(Player player) {
		if (player == null) {
			return 1;
		}
		if (this.nickname == null) {
			return -1;
		}
		return this.nickname.compareTo(player.getNickname());
	}

}
