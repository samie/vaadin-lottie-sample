package org.vaadin.example.lottie;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.ServiceInitEvent;
import org.jsoup.nodes.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;

@SpringBootApplication
@Push
public class DemoApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener
	public void  configureSeoAndSocialTags(ServiceInitEvent event) {
		// Inject SEO and social tags to head html element when the index.html is requested
		event.addIndexHtmlRequestListener(response -> {
			Element head = response.getDocument().head();
			injectSeoAndSocialTags(head,
					"Vaadin Lottie Demo application",
					"Embed Lottie animations into your Vaadin Java application easily.",
					"https://lottie-vaadin-demo.fly.dev/",
					"https://lottie-vaadin-demo.fly.dev/lottie_social_preview.png",
					LocalDate.now());
		});
	}

	/**
	 * Generates and injects Microdata, JSON-LD and Open Graph metadata into the head element of the HTML document.
	 *
	 * @param head          the head element of the HTML document
	 * @param name          the name of the page
	 *                      (e.g. "MyApp")
	 * @param description   the description of the page
	 *                      (e.g. "This is my application.")
	 * @param url           the URL of the page
	 *                      (e.g. "http://localhost:8080/")
	 * @param imageUrl      the URL of the image to be used as a preview
	 *                      (e.g. "http://localhost:8080/images/socialpreview.png")
	 * @param datePublished the date when the page was published
	 *                      (e.g. LocalDate.now())
	 */
	public static void injectSeoAndSocialTags(Element head, String name, String description, String url,
											  String imageUrl, LocalDate datePublished) {
		var jsonLd = JsonLd.webSite(name, description, url, imageUrl);
		head.appendElement("script")
				.attr("type", "application/ld+json")
				.appendText(jsonLd.toJson());

		addMicrodataMetaTag(head, "name", name);
		addMicrodataMetaTag(head, "description", description);
		addMicrodataMetaTag(head, "url", url);
		addMicrodataMetaTag(head, "datePublished", datePublished.toString());
		addMicrodataMetaTag(head, "image", imageUrl);

		addOpenGraphMetaTag(head, "title", name);
		addOpenGraphMetaTag(head, "description", description);
		addOpenGraphMetaTag(head, "url", url);
		addOpenGraphMetaTag(head, "image", imageUrl);
		addOpenGraphMetaTag(head, "type", "website");
		addOpenGraphMetaTag(head, "site_name", name);
	}

	static void addMicrodataMetaTag(Element head, String property, String content) {
		head.appendElement("meta").attr("itemprop", property)
				.attr("content", content);
	}

	static void addOpenGraphMetaTag(Element head, String property, String content) {
		head.appendElement("meta").attr("property", "og:"+property)
				.attr("content", content);
	}
}
