package tetrismina.command;

public interface TetrisCommand extends Command {
	byte[] toBytes() throws Exception;
}
