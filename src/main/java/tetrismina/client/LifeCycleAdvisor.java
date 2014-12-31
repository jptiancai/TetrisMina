package tetrismina.client;

import org.springframework.richclient.application.config.DefaultApplicationLifecycleAdvisor;

public class LifeCycleAdvisor extends DefaultApplicationLifecycleAdvisor {

	private TetrisClient tetrisClient = null;

	public void setTetrisClient(TetrisClient tetrisClient) {
		this.tetrisClient = tetrisClient;
	}

	@Override
	public void onPostStartup() {
		if (this.tetrisClient != null) {
			this.tetrisClient.connect();
		}
	}

}
