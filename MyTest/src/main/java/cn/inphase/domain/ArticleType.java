package cn.inphase.domain;

import java.util.List;

public class ArticleType extends BaseType {
	private List<Item> Articles;
	private int ArticleCount;

	public List<Item> getArticles() {
		return Articles;
	}

	public void setArticles(List<Item> articles) {
		Articles = articles;
		if (articles != null) {
			ArticleCount = articles.size();
		} else {
			ArticleCount = 0;
		}
	}

	public int getArticleCount() {
		return ArticleCount;
	}

}
