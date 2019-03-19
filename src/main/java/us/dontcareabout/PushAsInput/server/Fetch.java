package us.dontcareabout.PushAsInput.server;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.dontcareabout.PushAsInput.shared.FetchSetting;
import us.dontcareabout.PushAsInput.shared.RawPush;
import us.dontcareabout.PushAsInput.shared.RawPushImpl;

public class Fetch {
	/**
	 * @return 指定 URL 下方的推文，轉成 {@link RawPush} 的 {@link ArrayList}
	 */
	public static ArrayList<RawPush> process(String url) {
		ArrayList<RawPush> result = new ArrayList<>();
		Document doc;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}

		for (Element e : doc.select(".push")) {
			RawPushImpl rp = new RawPushImpl();
			rp.setType(e.select(".push-tag").text());
			rp.setId(e.select(".push-userid").text());
			rp.setContent(e.select(".push-content").text());
			result.add(rp);
		}

		return result;
	}

	private static final Setting setting = new Setting();
	private static final FetchSetting singapore = new FetchSetting("https://www.ptt.cc/bbs/Singapore/M.1535806897.A.57F.html", "Singapore.json");
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");

	public static void main(String[] args) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(
			new Runnable() {
				@Override
				public void run() {
					try {
						routine();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			},
			0, 1, TimeUnit.HOURS);
	}

	private static void routine() throws Exception {
		ArrayList<RawPush> result = process(singapore.getUrl());
		Gson gson = new Gson();
		String json = gson.toJson(result);
		File file = new File(setting.repo(), singapore.getFileName());

		if (json.equals(originData(file))) {
			return;
		}

		Files.write(
			json.getBytes(Charsets.UTF_8), file
		);

		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(setting.username(), setting.password());
		File local = new File(new File(setting.repo()), ".git");
		Repository repo = new FileRepository(local);
		ObjectId oid = repo.resolve("HEAD^");

		Git git = new Git(repo);

		String now = format.format(new Date());
		git.reset().setRef(oid.getName()).call();
		git.add().addFilepattern(".").call();
		git.commit().setAll(true).setMessage(now).call();
		git.push().setCredentialsProvider(cp).setForce(true).call();
		git.close();

		System.out.println("Finish @ " + now);
	}

	private static String originData(File file) {
		try {
			return Files.toString(
				new File(setting.repo(), singapore.getFileName()),
				Charsets.UTF_8
			);
		} catch (IOException e) {
			return null;
		}
	}

}
