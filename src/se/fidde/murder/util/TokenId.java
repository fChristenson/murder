package se.fidde.murder.util;

/**
 * TokenId class stores each fxCss id for tokens
 * 
 * @author fidde
 * 
 */
public enum TokenId {
	PLAYER1("player1Token"), PLAYER2("player2Token"), PLAYER3("player3Token"), PLAYER4(
			"player4Token"), PLAYER5("player5Token"), PLAYER6("player6Token");

	private final String NAME;

	private TokenId(String name) {
		this.NAME = name;
	}

	@Override
	public String toString() {
		return NAME;
	}
}
