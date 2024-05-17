import { Injectable } from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Article} from "./Article";
import {SerializedUrlObject} from "../utils/SerializedUrlObject";

@Injectable({
  providedIn: 'root'
})
export class ArticleService extends GenericService<Article> {
  protected override className = "Article"
  protected override url = "http://localhost:8080/articles"

  getAllArticles() {
    return this.http.get<Article[]>(this.url);
  }

  getArticleById(id: Number) {
    return this.http.get<Article>(this.url + "/" + id);
  }

  updateArticle(article: Article) {
    return this.http.put(this.url + "/" + article.id, article);
  }

  deleteArticle(article: Article) {
    return this.http.delete(this.url + "/" + article.id);
  }

  createArticle(article: Article) {
    const requestBody = {
      id: 0,
      titre: article.titre,
      description: article.description};
    return this.http.post(this.url, requestBody);
  }
}
