package model;

public class NewsContent {

	private String news_id;
	private String news_url;
	private String img_src;
	private String img_alt;
	private String date_updated;
	private String news_title;
	private String news_content;
	private boolean parsed;
	
	
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	public String getNews_url() {
		return news_url;
	}
	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}
	public String getImg_src() {
		return img_src;
	}
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	public String getImg_alt() {
		return img_alt;
	}
	public void setImg_alt(String img_alt) {
		this.img_alt = img_alt;
	}
	public String getDate_updated() {
		return date_updated;
	}
	public void setDate_updated(String date_updated) {
		this.date_updated = date_updated;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public boolean isParsed() {
		return parsed;
	}
	public void setParsed(boolean parsed) {
		this.parsed = parsed;
	}
	@Override
	public String toString() {
		return "NewsContent [news_id=" + news_id + ", news_url=" + news_url
				+ ", img_src=" + img_src + ", img_alt=" + img_alt
				+ ", date_updated=" + date_updated + ", news_title="
				+ news_title + ", news_content=" + news_content + ", parsed="
				+ parsed + "]";
	}
	
	
//	{"news_id":1547,"news_url":"http://www.rbc.ua/ukr/news/sport/-dlya-dnepra-printsipialno-zanyat-pervoe-mesto---huande-12122013115700",
//		"img_src":"http://www.rbc.ua/static/img/s/7/s7fp7hp4tgg_250x200.jpg",
//		"img_alt":"",
//		"date_updated":"12-12-2013 10:00:44",
//		"news_title":"\"Для \"Дніпра\" принципово зайняти перше місце\",",
//		"news_content":"Наставник \"Дніпра\" Хуанде Рамос перед грою Ліги Європи проти \"Фіорентини\" сказав, що \"Шахтар\" може виграти цей турнір і зазначив, що для \"Дніпра\" важливо посісти перше місце в групі.\n\"Для команди принципово зайняти перше місце. Це важливо для нас. Сам матч, думаю, теж буде принциповим. Команда налаштовується тільки на перемогу. Ніяких думок про відпустку не може бути в принципі, тому що ми всі професіонали і тим більше, нам належить єврокубковий матч. В першу чергу ми повинні показати хорошу гру і добитися результату, щоб зайняти перше місце в групі.\nТреба поважати кожну думку, і це в тому числі. У свою чергу, можу сказати, що, наприклад, \"Шахтар\" теж за своїм потенціалом є претендентом на титул, нітрохи не поступаючись в класі \"Ювентусу\". Але й цими двома командами не обмежується турнір. За титул боротимуться багато команд. Ми з \"Фіорентиною\" теж знаходимося в обоймі турніру.\nІталійські команди непрості, з ними завжди складно грати. Італійський футбол мені подобається своєю тактичною складовою. В минулому році ми спочатку обіграли \"Наполі\", а потім поступилися йому. Зараз, коли два місяці тому грали з \"Фіорентиною\", поже виграти будь-суперник\".\nДодамо, що онлайн трансляція матчу \"Фіорентина\" - \"Дніпро\" розпочнеться на нашому сайті о 19:30.",
//		"parsed":true}

}
