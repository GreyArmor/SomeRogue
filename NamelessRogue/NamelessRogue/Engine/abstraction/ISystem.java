package abstraction;

import java.sql.Time;

import shell.Game;

public interface ISystem {
	public void Update(Time gameTime, Game game);
}
